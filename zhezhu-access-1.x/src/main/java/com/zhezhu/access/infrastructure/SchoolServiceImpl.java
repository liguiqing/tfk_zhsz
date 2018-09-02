package com.zhezhu.access.infrastructure;

import com.zhezhu.share.domain.school.StudyYear;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Component("localSchoolService")
public class SchoolServiceImpl implements SchoolService {


    @Autowired(required = false)
    @Qualifier("remoteSchoolService")
    private SchoolService proxy;

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public String getSchoolName(String schoolId) {
        if(this.proxy == null)
            return this.proxy.getSchoolName(schoolId);

        return jdbc.queryForObject("select `name` from sm_school where schoolId=?",
                (rs,r) -> rs.getString("schoolName"),schoolId);
    }

    @Override
    public String getClazzName(String clazzId) {
        if(this.proxy != null)
            return this.proxy.getClazzName(clazzId);

        StudyYear year = StudyYear.now();
        return jdbc.queryForObject("select a.clazzName from sm_clazz_history a " +
                        "where a.yearStarts=? and a.yearEnds=? and a.clazzId=? and a.removed = 0;",
                (rs,r) -> rs.getString("schoolName"),new Object[]{year.getYearStarts(),year.getYearEnds(),clazzId});
    }

    @Override
    public String getSchoolIdBy(String clazzId) {
        if(this.proxy != null)
            return this.proxy.getClazzName(clazzId);

        StudyYear year = StudyYear.now();
        return jdbc.queryForObject("select distinct(b.schoolId) " +
                        "from sm_clazz_history a inner join sm_school b on b.schoolId=a.schoolId  " +
                        "where a.yearStarts=? and a.yearEnds=? and a.clazzId=? and a.removed = 0",
                (rs,r) -> rs.getString("schoolName"),new Object[]{year.getYearStarts(),year.getYearEnds(),clazzId});
    }

}