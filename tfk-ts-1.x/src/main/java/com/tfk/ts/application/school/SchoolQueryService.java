/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.application.school;

import com.tfk.ts.application.school.data.CourseData;
import com.tfk.ts.application.school.data.GradeData;
import com.tfk.ts.application.school.data.SchoolData;
import com.tfk.ts.domain.model.school.*;
import com.tfk.ts.domain.model.school.common.Configuation;
import com.tfk.ts.domain.model.school.common.SchoolConfig;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 学校查询服务
 *
 * @author Liguiqing
 * @since V3.0
 */

public class SchoolQueryService {
    private static Logger logger = LoggerFactory.getLogger(SchoolQueryService.class);

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private JdbcTemplate query;

    @Transactional(readOnly = true)
    @Cacheable(value = "SchoolNameCache", key = "#name")
    public SchoolData findSchoolByName(String name) {
        logger.debug("Query school by  name of {}", name);
        String sql = "select name,alias,type from ts_school where name=? union " +
                " select name,alias,type from ts_school where alias=?";

        return query.queryForObject(sql, new RowMapper<SchoolData>() {

            @Override
            public SchoolData mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new SchoolData(rs.getString("name"),rs.getString("alias"),
                        rs.getString("type"));
            }
        }, new Object[]{name,name});
    }



    /**
     * 查询学校年级
     * @param schoolId
     * @return
     */
    @Transactional(readOnly = true)
    @Cacheable(value = "SchoolGradeCache", key = "#schoolId")
    public List<GradeData> schoolGrade(String schoolId){
        logger.debug("Query school Grades for {}",schoolId);

        SchoolId schoolId1 = new SchoolId(schoolId);
        GradeNameGenerateStrategy nameGenerateStrategy =
                GradeNameGenerateStrategyFactory.lookup(schoolId1);
        School school = schoolRepository.loadOfId(schoolId1);
        List<Grade> grades = school.grades();
        ArrayList<GradeData> gs = Lists.newArrayList();
        for(Grade g:grades){
            gs.add(new GradeData(g.name(), g.seq().getSeq()));
        }
        return gs;
    }

    /**
     * 查询学校课程
     *
     * @param schoolId
     * @return
     */
    @Transactional(readOnly = true)
    @Cacheable(value = "SchoolCourseCache", key = "#schoolId")
    public List<CourseData> schoolCourses(String schoolId){
        logger.debug("Query school Courses for {}",schoolId);

        String sql = " select name,subjectId,gradeName from (select name,subjectId,gradeName,gradeLevel " +
                " from ts_course where schoolId is null " +
                " union select name,subjectId,gradeName,gradeLevel " +
                " from ts_course where schoolId=?) a order by gradeLevel";
        return query.query(sql, new RowMapper<CourseData>() {
            @Override
            public CourseData mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new CourseData(rs.getString("name"),rs.getString("subjectId"),rs.getString("gradeName"));
            }
        }, new Object[]{schoolId});
    }

    /**
     * 查询学校配置
     *
     * @param schoolId
     * @return
     */
    @Transactional(readOnly = true)
    @Cacheable(value = "SchoolGradeNameCache", key = "#schoolId")
    public List<SchoolConfig> schoolConfigs(final String schoolId){
        logger.debug("Query school Configuations for {}",schoolId);

        String sql = "select name,value,description from ts_configuation where schoolId=?";

        List<SchoolConfig> l =   query.query(sql,new  RowMapper<SchoolConfig>(){

            @Override
            public SchoolConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
                Configuation c = new Configuation(rs.getString("name"),
                        rs.getString("value"),
                        rs.getString("description"));
                SchoolConfig sc = new SchoolConfig(new SchoolId(schoolId),c);
                return sc;
            }
        });
        return l;
    }

}