package com.tfk.sm.application;

import com.tfk.commons.AssertionConcerns;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.id.school.StudentId;
import com.tfk.share.domain.id.school.TeacherId;
import com.tfk.share.domain.person.Contact;
import com.tfk.share.domain.person.Person;
import com.tfk.share.domain.school.Grade;
import com.tfk.share.domain.school.Teadent;
import com.tfk.share.infrastructure.validate.contact.ContactValidations;
import com.tfk.sm.application.data.Contacts;
import com.tfk.sm.domain.model.clazz.Clazz;
import com.tfk.sm.domain.model.clazz.LearningClazzRepository;
import com.tfk.sm.domain.model.clazz.SuperviseClazzRepository;
import com.tfk.sm.domain.model.clazz.UnitedClazzRepository;
import com.tfk.sm.domain.model.school.School;
import com.tfk.sm.domain.model.student.Student;
import com.tfk.sm.domain.model.student.StudentRepository;
import com.tfk.sm.domain.model.teacher.Teacher;
import com.tfk.sm.domain.model.teacher.TeacherRepository;
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