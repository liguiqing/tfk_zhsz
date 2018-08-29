package com.tfk.sm.application.teacher;

import com.google.common.collect.Lists;
import com.tfk.commons.util.CollectionsUtilWrapper;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.TeacherId;
import com.tfk.share.domain.school.Grade;
import com.tfk.sm.application.clazz.ClazzApplicationService;
import com.tfk.sm.application.data.ClazzData;
import com.tfk.sm.application.data.TeacherClazzData;
import com.tfk.sm.domain.model.clazz.Clazz;
import com.tfk.sm.domain.model.clazz.ClazzService;
import com.tfk.sm.domain.model.teacher.ClazzManagement;
import com.tfk.sm.domain.model.teacher.ClazzTeaching;
import com.tfk.sm.domain.model.teacher.Teacher;
import com.tfk.sm.domain.model.teacher.TeacherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Liguiqing
 * @since V3.0
 */

@Slf4j
@Service
@Transactional(readOnly = true)
public class TeacherQueryService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ClazzApplicationService clazzApplicationService;

    public List<TeacherClazzData> findTeacherClazzesOfSchool(String schoolId, String teacherId){
        log.debug("Find Teacher {} clazzes in school {}",teacherId,schoolId);

        Teacher teacher = teacherRepository.loadOf(new TeacherId(teacherId));
        if(teacher == null)
            return new ArrayList<>();

        List<TeacherClazzData> clazzDatas = Lists.newArrayList();

        Set<ClazzTeaching> teachings =  teacher.teachings();
        if(!CollectionsUtilWrapper.isNotNullOrNotEmpty(teachings)){
            List<ClazzData> teachingClazzDatas = teachings.stream()
                    .map(ct -> toClazzData(ct.getClazz().clazzId()))
                    .collect(Collectors.toList());
            clazzDatas.add(new TeacherClazzData("Teaching", teachingClazzDatas));
        }

        Set<ClazzManagement> manages =  teacher.manages();
        if(!CollectionsUtilWrapper.isNotNullOrNotEmpty(manages)){
            List<ClazzData> managedClazzDatas = manages.stream()
                    .map(cm -> toClazzData(cm.getClazz().clazzId()))
                    .collect(Collectors.toList());
            clazzDatas.add(new TeacherClazzData("Managed", managedClazzDatas));
        }
        return clazzDatas;
    }

    private ClazzData toClazzData(ClazzId clazzId){
        Clazz clazz = clazzApplicationService.getClazz(clazzId.id());
        Grade grade = clazz.currentGrade();
        return new ClazzData(clazz.getClazzId().id(),clazz.getGradeFullName(grade),grade.name(),grade.level());
    }

}