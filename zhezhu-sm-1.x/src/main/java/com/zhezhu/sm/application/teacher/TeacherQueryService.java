package com.zhezhu.sm.application.teacher;

import com.google.common.collect.Lists;
import com.zhezhu.commons.util.CollectionsUtilWrapper;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.TeacherId;
import com.zhezhu.share.domain.school.Grade;
import com.zhezhu.sm.application.clazz.ClazzApplicationService;
import com.zhezhu.sm.application.data.ClazzData;
import com.zhezhu.sm.application.data.TeacherClazzData;
import com.zhezhu.sm.domain.model.clazz.Clazz;
import com.zhezhu.sm.domain.model.teacher.ClazzManagement;
import com.zhezhu.sm.domain.model.teacher.ClazzTeaching;
import com.zhezhu.sm.domain.model.teacher.Teacher;
import com.zhezhu.sm.domain.model.teacher.TeacherRepository;
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
        if(!CollectionsUtilWrapper.isNotNullAndNotEmpty(teachings)){
            List<ClazzData> teachingClazzDatas = teachings.stream()
                    .map(ct -> toClazzData(ct.getClazz().getClazzId()))
                    .collect(Collectors.toList());
            clazzDatas.add(new TeacherClazzData("Teaching", teachingClazzDatas));
        }

        Set<ClazzManagement> manages =  teacher.manages();
        if(!CollectionsUtilWrapper.isNotNullAndNotEmpty(manages)){
            List<ClazzData> managedClazzDatas = manages.stream()
                    .map(cm -> toClazzData(cm.getClazz().getClazzId()))
                    .collect(Collectors.toList());
            clazzDatas.add(new TeacherClazzData("Managed", managedClazzDatas));
        }
        return clazzDatas;
    }

    private ClazzData toClazzData(ClazzId clazzId){
        Clazz clazz = clazzApplicationService.getClazz(clazzId.id());
        Grade grade = clazz.currentGrade();
        return new ClazzData(clazz.getClazzId().id(),clazz.getGradeFullName(grade),grade.getName(),grade.getLevel());
    }

}