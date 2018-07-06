/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.port.adapter.persistence.hibernate;

import com.tfk.commons.util.DateUtilWrapper;
import com.tfk.ts.domain.model.school.*;
import com.tfk.ts.domain.model.school.clazz.*;
import com.tfk.ts.domain.model.school.common.*;
import com.tfk.ts.domain.model.school.staff.*;
import com.tfk.ts.domain.model.school.student.Student;
import com.tfk.ts.domain.model.school.student.StudentId;
import com.tfk.ts.domain.model.school.student.StudentIdentity;
import com.tfk.ts.domain.model.school.student.Study;
import com.tfk.ts.domain.model.school.term.Term;
import com.tfk.ts.domain.model.school.term.TermId;
import com.tfk.ts.domain.model.school.term.TermOrder;
import com.tfk.ts.domain.model.school.term.TimeSpan;
import org.hibernate.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Liguiqing
 * @since V3.0
 */
@ContextConfiguration(locations = {"classpath:applicationContext-ts-test-ds.xml",
        "classpath:applicationContext-ts.xml","classpath:applicationContext-ts-app.xml",
        "classpath:applicationContext-ts-hb.xml"})
@Transactional
@Rollback
public class ConfiguationTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private DataSource ds;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("ds")
    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Test
    public void test() throws Exception {
        SchoolId schoolId = new SchoolId("SchoolId-12345678");
        Grade g7 = new Grade("七年级",GradeLevel.Seven,StudyYear.yearOfNow());

        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        Query query = null;
        List l1 = null;

        assertNotNull(ds);
        assertNotNull(jdbcTemplate);
        List<String> xs = jdbcTemplate.query("SELECT 'x'", new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("x");
            }
        });
        assertNotNull(xs);
        assertTrue(xs.size() == 1);
        assertEquals(xs.get(0), "x");

        School s1 = new School(new TenantId("TenantId-12345678"), schoolId, "School1",
                "s1", SchoolType.High);

        transaction.begin();
        session.save(s1);
        query = session.createQuery("from School where schoolId='SchoolId-12345678'");
        l1 = query.list();
        School s1_ = (School) l1.get(0);
        assertNotNull(s1_);
        assertEquals(s1, s1_);
        assertEquals(s1.schoolId(), s1_.schoolId());
        assertEquals(s1.tenantId(), s1_.tenantId());
        session.delete(s1_);

        Term t1 = new Term(new TermId("TermId-12345678"), "2017-2018上学期", StudyYear.yearOfNow(),
                TermOrder.First, new TimeSpan(DateUtilWrapper.today(), DateUtilWrapper.tomorrow()),
                schoolId);
        session.save(t1);
        query = session.createQuery("from Term where termId='TermId-12345678'");
        l1 = query.list();
        Term t1_ = (Term) l1.get(0);
        assertNotNull(t1_);
        assertEquals(t1, t1_);
        assertEquals(t1_.termYear().name(), StudyYear.yearOfNow().name());
        session.delete(t1_);

        Clazz clazz1 = new AdminClazz(schoolId,new ClazzId("ClazzId-123456781"),"Clazz1","171",DateUtilWrapper.today(),g7,t1_);
        Clazz clazz2 = new TeachClazz(schoolId,new ClazzId("ClazzId-123456782"),"Clazz2","17hx1",DateUtilWrapper.today(),g7,t1_);
        Clazz clazz3 = new MixtureClazz(schoolId,new ClazzId("ClazzId-123456783"),"Clazz3","173",DateUtilWrapper.today(),g7,t1_);

        clazz3.addCatagory("实验班","sy");

        session.save(clazz1);
        session.save(clazz2);
        session.save(clazz3);

        query = session.createQuery("from Clazz where clazzId='ClazzId-123456783'");
        l1 = query.list();
        Clazz clazz3_ = (Clazz)l1.get(0);
        assertNotNull(clazz3_);
        assertEquals(clazz3,clazz3_);
        assertTrue(clazz3_.histories().size() == 1);
        assertTrue(clazz3_.catagories().size() == 1);


        Staff staff1 = new Staff(schoolId, new StaffId("StaffId-12345678"), "Staff1",
                Gender.Female, new Period(DateUtilWrapper.today(), DateUtilWrapper.tomorrow()));
        staff1.addIdentity(new StaffIdentity(new StaffId("StaffId-12345678"), IdentityType.JobNo, "12345678995456"));
        staff1.addIdentity(new StaffIdentity(new StaffId("StaffId-12345678"), IdentityType.QQ, "3215647899"));
        Period period = new Period(DateUtilWrapper.today(), DateUtilWrapper.tomorrow());
        staff1.actTo(new ActHeadMaster(),period);
        staff1.actTo(new ActTeacher(new Course("语文","Easytnt_SBID_001")),period);
        staff1.actTo(new ActClazzMaster(clazz1),period);
        staff1.actTo(new ActClazzMaster(clazz3),period);
        staff1.actTo(new ActClazzTeacher(clazz2,new ActTeacher(new Course("语文","Easytnt_SBID_001")).actTo(staff1,period),g7),period);
        staff1.actTo(new ActClazzTeacher(clazz3,new ActTeacher(new Course("语文","Easytnt_SBID_001")).actTo(staff1,period),g7),period);
        staff1.actTo(new ActGradeMaster(g7),period);
        staff1.actTo(new ActSubjectMaster("语文","Easytnt_SBID_001"),period);
        staff1.actTo(new ActTeachingMaster("教导主任"),period);

        session.save(staff1);

        query = session.createQuery("from Staff where staffId=?");
        query.setParameter(0, new StaffId("StaffId-12345678"));
        l1 = query.list();
        Staff staff1_ = (Staff) l1.get(0);
        assertNotNull(staff1_);
        assertEquals(staff1_.gender(), Gender.Female);
        assertTrue(staff1_.identity().size() > 0);
        assertTrue(staff1_.positions().size() > 0);
        assertTrue(staff1_.identity().contains(new StaffIdentity(new StaffId("StaffId-12345678"), IdentityType.JobNo, "12345678995456")));
        //assertTrue(staff1_.positions().contains(new ActTeacher(new Course("语文", "Easytnt_SBID_001")).actTo(staff1,period)));
        //Iterator it = staff1_.identity().iterator();
        //assertEquals(it.next(), new StaffIdentity(new StaffId("StaffId-12345678"), IdentityType.JobNo, "12345678995456"));
        //assertEquals(it.next(), new StaffIdentity(new StaffId("StaffId-12345678"), IdentityType.QQ, "3215647899"));
        Iterator it = staff1_.positions().iterator();
        int i = 0;
        while(it.hasNext()){
            it.next();
            i++;
        }
        assertEquals(i,9);
        SQLQuery sqlQuery = session.createSQLQuery("select schoolId, staffId, name, gender, periodStarts, periodEnds from ts_staff where staffId='StaffId-12345678'");
        List ll = sqlQuery.list();
        assertNotNull(ll);
        Object[] rs = (Object[]) ll.get(0);
        assertEquals(rs[3], "女");

        StudentId studentId = new StudentId("StudentId-123456781");
        Student student1 = new Student(schoolId,studentId,"Student1");
        student1.addId(new StudentIdentity(studentId,IdentityType.StudyNumber,"456789899554556652"));
        student1.changeManagedClazz(clazz1,DateUtilWrapper.today(),DateUtilWrapper.tomorrow());
        student1.addStudy(clazz2,g7,new Course("语文","Easytnt_SBID_001"),DateUtilWrapper.today(),DateUtilWrapper.tomorrow());

        session.save(student1);

        query = session.createQuery("from Student where studentId=?");
        query.setParameter(0, studentId);
        l1 = query.list();

        Student student1_ = (Student) l1.get(0);
        assertNotNull(student1_);
        assertEquals(student1, student1_);
        assertTrue(student1_.belongTos().size() == 1);
        assertTrue(student1_.studies().size() == 1);
        Study study = student1_.getStudyOnLine(g7, new Course("语文", "Easytnt_SBID_001"));
        assertNotNull(study);

        SchoolConfig sc = new SchoolConfig(schoolId,new Configuation("c1","c1","c1"));
        session.save(sc);
        transaction.rollback();


    }

}