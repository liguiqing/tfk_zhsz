package com.zhezhu.sm.application.clazz;

import com.zhezhu.commons.util.CollectionsUtilWrapper;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.school.Grade;
import com.zhezhu.sm.application.data.ClazzData;
import com.zhezhu.sm.domain.model.clazz.Clazz;
import com.zhezhu.sm.domain.model.clazz.ClazzRepository;
import com.zhezhu.sm.domain.model.school.School;
import com.zhezhu.sm.domain.model.school.SchoolRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class ClazzQueryService {

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private List<ClazzRepository> clazzRepositories;

    /**
     * 查询学校当前可管理学生的班级
     *
     * @param schoolId
     * @param gradeLevel scope of 1-12; if it is out of scope,use Grade 1 as default;
     * @return
     */
    public List<ClazzData> findSchoolGradeClazzesCanBeManagedOfNow(String schoolId, int gradeLevel){
        Grade grade = Grade.newWithLevel(gradeLevel);
        log.debug("Find school {} class of {}",schoolId,grade);

        School school = schoolRepository.loadOf(new SchoolId(schoolId));

        if(school == null || !school.hasGrade(grade)){
            return new ArrayList<>();
        }

        ClazzRepository clazzRepository = clazzRepositories.get(0);
        List<Clazz> clazzes = clazzRepository.findClazzCanBeManagedOf(school.getSchoolId(), grade);
        if(CollectionsUtilWrapper.isNullOrEmpty(clazzes)){
            return new ArrayList<>();
        }

        List<Clazz> existClazzes = clazzes.stream()
                .map(clazz -> clazzRepository.loadOf(clazz.getClazzId()))
                .filter(clazz -> clazz != null)
                .collect(Collectors.toList());

        if(CollectionsUtilWrapper.isNullOrEmpty(existClazzes)){
            return new ArrayList<>();
        }

        List<ClazzData> clazzDatas =  existClazzes.stream()
                .map(clazz -> new ClazzData(clazz.getClazzId().id(),
                        clazz.getGradeFullName(grade),grade.getName(),grade.getLevel()))
                .collect(Collectors.toList());
        return clazzDatas;
    }


    private ClazzRepository getClazzRepositoryOf(Class clazz){
        for(ClazzRepository clazzRepository:this.clazzRepositories){
            if(clazzRepository.supports(clazz))
                return clazzRepository;
        }
        return null;
    }


}