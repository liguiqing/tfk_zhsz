package com.zhezhu.boot.infrastructure.init;

import com.zhezhu.assessment.application.collaborator.CollaboratorApplicationService;
import com.zhezhu.share.domain.id.SubjectId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.school.StudyYear;
import com.zhezhu.sm.application.clazz.ClazzApplicationService;
import com.zhezhu.sm.application.data.Contacts;
import com.zhezhu.sm.application.data.CourseData;
import com.zhezhu.sm.application.data.CredentialsData;
import com.zhezhu.sm.application.data.StudyData;
import com.zhezhu.sm.application.student.ArrangeStudentCommand;
import com.zhezhu.sm.application.student.NewStudentCommand;
import com.zhezhu.sm.application.student.StudentApplicationService;
import com.zhezhu.sm.application.teacher.ArrangeTeacherCommand;
import com.zhezhu.sm.application.teacher.NewTeacherCommand;
import com.zhezhu.sm.application.teacher.TeacherApplicationService;
import com.zhezhu.sm.domain.model.school.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Liguiqing
 * @since V3.0
 */

public abstract class AbstractSchoolData  implements SchoolData{

    @Autowired
    protected SchoolRepository schoolRepository;

    @Autowired
    protected ClazzApplicationService applicationService;

    @Autowired
    protected TeacherApplicationService teacherApplicationService;

    @Autowired
    protected StudentApplicationService studentApplicationService;

    @Autowired
    protected CollaboratorApplicationService collaboratorService;

    @Autowired
    protected JdbcTemplate jdbc;

    protected SchoolId schoolId = new SchoolId();

    @Override
    public void create() {
        doCreate();
        collaboratorService.teacherToAssessor(this.schoolId.id());
        collaboratorService.studentToAssessee(this.schoolId.id());
    }

    protected abstract void doCreate();

    @PostConstruct
    public void initSubjects(){
        if(subjectIds == null) {
            List<SubjectId> subjectIdList = jdbc.query("select subjectId from cm_course order by id", (rs, rn) -> new SubjectId(rs.getString("subjectId")));
            subjectIds = new SubjectId[subjectIdList.size()];
            subjectIdList.toArray(subjectIds);
        }
    }

    //语数外物化生政史地
    protected static SubjectId[] subjectIds = null;

    protected CredentialsData[] toCredentials(String name,String[] values) {
       return Arrays.asList(values).stream().map(s -> new CredentialsData(name, s))
               .collect(Collectors.toList()).toArray(new CredentialsData[]{});
    }

    protected void createStudent(ClazzId clazzId, String[] names, String[] phones, CredentialsData[] credentialsData, StudyData[] studyData, StudyYear year) {
        int i=0;
        for(String name:names){
            String studentId = studentApplicationService.newStudent(NewStudentCommand.builder()
                    .name(name)
                    .contacts(new Contacts[]{new Contacts("Phone",phones[i])})
                    .credentials(new CredentialsData[]{credentialsData[i++]})
                    .joinDate(year.getDefaultDateStarts())
                    .schoolId(schoolId.id())
                    .build());
            studentApplicationService.arrangingClazz(ArrangeStudentCommand.builder()
                    .studentId(studentId)
                    .courses(studyData)
                    .dateStarts(year.getDefaultDateStarts())
                    //.dateEnds(year.getDefaultDateEnds())
                    .managedClazzId(clazzId.id())
                    .build());
        }
    }

    protected void createTeachers(String name,Contacts[] contacts,StudyYear studyYear,
                                  CourseData[] courses,
                                  ClazzId[] teachingClazzIds,ClazzId[] managementClazzIds){
        String teacherId2 = teacherApplicationService.newTeacher(NewTeacherCommand.builder()
                .name(name)
                .contacts(contacts)
                .schoolId(schoolId.id())
                .build());
        teacherApplicationService.arranging(ArrangeTeacherCommand.builder()
                .courses(courses)
                .teacherId(teacherId2)
                .dateStarts(studyYear.getDefaultDateStarts())
                //.dateEnds(studyYear.getDefaultDateEnds())
                .teachingClazzIds(Arrays.stream(teachingClazzIds).map(id->id.id()).collect(Collectors.toList()).toArray(new String[]{}))
                .managementClazzIds(Arrays.stream(managementClazzIds).map(id->id.id()).collect(Collectors.toList()).toArray(new String[]{}))
                .build());
    }
}