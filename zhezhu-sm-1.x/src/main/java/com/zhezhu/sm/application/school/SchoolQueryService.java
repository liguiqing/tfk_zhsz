package com.zhezhu.sm.application.school;

import com.zhezhu.sm.domain.model.school.School;
import com.zhezhu.sm.domain.model.school.SchoolRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 学校信息查询
 *
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class SchoolQueryService {

    @Autowired
    private SchoolRepository schoolRepository;

    public List<SchoolData> findAllSchool(int page, int size){
        log.debug("Query school page{} size{}",page,size);
        List<School> schools = schoolRepository.findByLimit(page, size);
        return schools.stream().map(school ->{
            SchoolData data = new SchoolData(school.getSchoolId().id(),school.getName());
            data.addGradeDatas(school.grades());
            return data;
        }).collect(Collectors.toList());
    }

}