package com.tfk.sm.application.student;

import com.tfk.commons.util.CollectionsUtilWrapper;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.school.Grade;
import com.tfk.sm.application.clazz.ClazzApplicationService;
import com.tfk.sm.application.data.StudentData;
import com.tfk.sm.application.data.StudentNameSortedData;
import com.tfk.sm.domain.model.clazz.Clazz;
import com.tfk.sm.domain.model.student.Student;
import com.tfk.sm.domain.model.student.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class StudentQueryService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClazzApplicationService clazzApplicationService;


    public StudentNameSortedData findStudentInOfNowAndSortByName(String schoolId, String clazzId){
        log.debug("Find student of school {} in clazz {} and sorted ",schoolId,clazzId);
        List<StudentData> studentData = findStudentInOfNow(schoolId, clazzId);
        //按中文字姓氏排序
        studentData.sort((s1,s2)->(Collator.getInstance(java.util.Locale.CHINA).compare(s1.getName(),s2.getName())));
        StudentNameSortedData sortedData = new StudentNameSortedData();
        studentData.forEach(data-> sortedData.add(data));
        return sortedData;
    }

    public List<StudentData> findStudentInOfNow(String schoolId, String clazzId){
        log.debug("Find student of school {} in {}",schoolId,clazzId);

        Clazz clazz = clazzApplicationService.getClazz(clazzId);
        Grade grade = clazz.currentGrade();
        SchoolId schoolId1 = new SchoolId(schoolId);
        ClazzId clazzId1 = new ClazzId(clazzId);
        if(clazz.canBeManagedAt()){
            List<Student> students = studentRepository.findByManageds(schoolId1,clazzId1,grade);
            return toStudentData(students,clazz);
        }else{
            List<Student> students = studentRepository.findByStudies(schoolId1,clazzId1,grade);
            return toStudentData(students,clazz);
        }
    }

    private List<StudentData> toStudentData(List<Student> students, Clazz clazz){
        if(CollectionsUtilWrapper.isNullOrEmpty(students)){
            return new ArrayList<>();
        }

        return students.stream().map(student -> toStudentData(student,clazz)).collect(Collectors.toList());
    }

    private StudentData toStudentData(Student student,Clazz clazz){
        ClazzId clazzId = clazz.canBeManagedAt()?student.currentManagedClazz():student.currentStudyClazz();
        Grade grade = clazz.currentGrade();
        return StudentData.builder()
                .clazzId(clazzId.id())
                .clazzName(clazz.getGradeFullName(grade))
                .gradeName(grade.getName())
                .gradeLevel(grade.getLevel())
                .schoolId(student.getSchoolId().id())
                .personId(student.getPersonId().id())
                .studentId(student.getStudentId().id())
                .build();
    }
}