package com.zhezhu.sm.application;

import com.zhezhu.commons.AssertionConcerns;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.id.school.StudentId;
import com.zhezhu.share.domain.id.school.TeacherId;
import com.zhezhu.share.domain.person.Contact;
import com.zhezhu.share.domain.person.Person;
import com.zhezhu.share.domain.school.Teadent;
import com.zhezhu.share.infrastructure.validate.contact.ContactValidations;
import com.zhezhu.sm.application.data.Contacts;
import com.zhezhu.sm.application.data.CredentialsData;
import com.zhezhu.sm.domain.model.clazz.Clazz;
import com.zhezhu.sm.domain.model.clazz.LearningClazzRepository;
import com.zhezhu.sm.domain.model.clazz.SuperviseClazzRepository;
import com.zhezhu.sm.domain.model.clazz.UnitedClazzRepository;
import com.zhezhu.sm.domain.model.student.Student;
import com.zhezhu.sm.domain.model.student.StudentRepository;
import com.zhezhu.sm.domain.model.teacher.Teacher;
import com.zhezhu.sm.domain.model.teacher.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 抽象师生应用服务
 *
 * @author Liguiqing
 * @since V3.0
 */

public abstract class TeadentApplicationService {

    @Autowired
    protected TeacherRepository teacherRepository;

    @Autowired
    protected StudentRepository studentRepository;

    @Autowired
    protected LearningClazzRepository learningClazzRepository;

    @Autowired
    protected SuperviseClazzRepository superviseClazzRepository;

    @Autowired
    protected UnitedClazzRepository unitedClazzRepository;

    @Autowired
    protected ContactValidations contactValidations;


    protected void addContacts(Contacts[] contacts, Person person){
        if(contacts != null){
            for(Contacts con:contacts){
                Contact contact = con.toContact();
                person.addContact(this.contactValidations,contact);
            }
        }
    }

    protected Teacher toTeacher(NewTeadentCommand command){
        SchoolId schoolId = new SchoolId(command.getSchoolId());
        PersonId personId = new PersonId();
        TeacherId teacherId = teacherRepository.nextIdentity();
        Teacher teacher = new Teacher(teacherId,personId,schoolId,command.getName());
        setOtherFieldsValue(command,teacher);
        return teacher;
    }

    protected Student toStudent(NewTeadentCommand command){
        StudentId studentId = studentRepository.nextIdentity();
        SchoolId schoolId = new SchoolId(command.getSchoolId());
        PersonId personId = new PersonId();
        Student student = new Student(studentId,schoolId,personId,command.getName(),command.getBirthday(),command.getGender());
        setOtherFieldsValue(command,student);
        return student;
    }

    protected void setOtherFieldsValue(NewTeadentCommand command,Teadent teadent){
        teadent.join(command.getJoinDate());
        teadent.off(command.getOffDate());
        addContacts(command.getContacts(),teadent);
        addCredentials(command.getCredentials(),teadent);
    }

    private void addCredentials(CredentialsData[] credentials, Teadent teadent) {
        if(credentials != null){
            for(CredentialsData data:credentials){
                teadent.addCredentials(data.getName(),data.getValue());
            }
        }
    }

    protected Clazz getStudyClazz(ClazzId clazzId){
        Clazz clazz = learningClazzRepository.loadOf(clazzId);
        if(clazz == null)
            clazz = unitedClazzRepository.loadOf(clazzId);
        AssertionConcerns.assertArgumentNotNull(clazz,"sm-02-001");
        return clazz;
    }

    protected Clazz getManagedClazz(ClazzId clazzId){
        Clazz clazz = superviseClazzRepository.loadOf(clazzId);
        if(clazz == null)
            clazz = unitedClazzRepository.loadOf(clazzId);
        AssertionConcerns.assertArgumentNotNull(clazz,"sm-02-001");
        return clazz;
    }
}