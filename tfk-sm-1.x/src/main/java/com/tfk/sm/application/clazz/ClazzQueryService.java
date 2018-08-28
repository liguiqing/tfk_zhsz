package com.tfk.sm.application.clazz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */

@Slf4j
@Service
@Transactional(readOnly = true)
public class ClazzQueryService {

    public List<ClazzData> findSchoolGradeClazzesOfNow(String schoolId, int gradeLevel){

        return null;
    }

}