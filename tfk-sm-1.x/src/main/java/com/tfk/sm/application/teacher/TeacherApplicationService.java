package com.tfk.sm.application.teacher;

import com.tfk.commons.AssertionConcerns;
import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.SubjectId;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.id.school.TeacherId;
import com.tfk.share.domain.person.Contact;
import com.tfk.share.domain.school.Course;
import com.tfk.share.domain.school.Grade;
import com.tfk.share.infrastructure.validate.contact.ContactValidations;
import com.tfk.sm.application.data.Contacts;
import com.tfk.sm.application.data.CourseData;
import com.tfk.sm.domain.model.clazz.Clazz;
import com.tfk.sm.domain.model.clazz.ClazzRepository;
import com.tfk.sm.domain.model.teacher.Teacher;
import com.tfk.sm.domain.model.teacher.TeacherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class TeacherApplicationService {
    private static Logger logger = LoggerFactory.getLogger(TeacherApplicationService.class);

    private TeacherRepository teacherRepository;

    private ClazzRepository clazzRepository;

    private ContactValidations contactValidations;


    public TeacherApplicationService(TeacherRepository teacherRepository, ContactValidations contactValidations) {
        this.teacherRepository = teacherRepository;
        this.contactValidations = contactValidations;
    }

    public void newTeacher(NewTeacherCommand command){
        logger.debug("New Teacher for School {}",command.getSchoolId());

        SchoolId schoolId = new SchoolId(command.getSchoolId());
        PersonId personId = new PersonId();
        TeacherId teacherId = new TeacherId();
        Teacher teacher = new Teacher(teacherId,personId,schoolId,command.getName());
        teacher.join(command.getJoinDate());
        if(command.getOffDate() != null){
            teacher.off(command.getOffDate());
        }
        if(command.getContacts() != null){
            for(Contacts contacts:command.getContacts()){
                Contact contact = contacts.toContact();

                if(contact == null)
                    continue;

                if(contactValidations.validate(contact)){
                    teacher.addContact(contact);
                }else{
                    logger.warn("Contact {} is illegal ",contact.toString());
                }
            }
        }

        teacherRepository.save(teacher);
    }

    public void arranging(TeacherArrangingCommand command){
        logger.debug("Teacher join {}",command.getTeacherId());
        TeacherId teacherId = new TeacherId(command.getTeacherId());
        Teacher teacher = teacherRepository.loadOf(teacherId);
        AssertionConcerns.assertArgumentNotNull(teacher,"sm-03-001");

        addTeacherCourse(command, teacher);
        addManagementClazzes(command,teacher);
        addTeachingClazzes(command, teacher);

        teacherRepository.save(teacher);
    }

    private void addTeacherCourse(TeacherArrangingCommand command,Teacher teacher){
        if(command.courses!=null){
            for(CourseData cd:command.courses){
                Course course = new Course(cd.getName(),new SubjectId(cd.getSubjectId()));
                Grade grade = Grade.newWithLevel(cd.getGradeLevel());
                teacher.addCourse(grade,course);
            }
        }
    }

    private void addManagementClazzes(TeacherArrangingCommand command,Teacher teacher){
        if(command.getManagementClazzIds() != null){
            for(String clazzId:command.getManagementClazzIds()){
                Clazz clazz = clazzRepository.loadOf(new ClazzId(clazzId));
                if(clazz.canBeManagedAt()){
                    Grade grade = clazz.currentGrade();

                }
            }
        }
    }

    private void addTeachingClazzes(TeacherArrangingCommand command,Teacher teacher){

    }

    public void offSchool(){

    }
}