package com.zhezhu.assessment.application.collaborator;

import com.zhezhu.assessment.domain.model.collaborator.Assessee;
import com.zhezhu.assessment.domain.model.collaborator.Assessor;
import com.zhezhu.assessment.domain.model.collaborator.CollaboratorRole;
import com.zhezhu.share.infrastructure.school.SchoolService;
import com.zhezhu.share.infrastructure.school.StudentData;
import com.zhezhu.share.infrastructure.school.TeacherData;
import lombok.*;

/**
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CollaboratorData {
    private String schoolId;

    private String assessorId;

    private String assesseeId;

    private StudentData student;

    private TeacherData teacher;

    public CollaboratorData setAssessorDetail(CollaboratorRole role, Assessor assessor, SchoolService schoolService){
        if(role.equals(CollaboratorRole.Teacher)){
            this.teacher = schoolService.getTeacherBy(assessor.getCollaborator().getPersonId(),
                    assessor.getCollaborator().getSchoolId());

        }
        return this;
    }

    public CollaboratorData setAssesseeDetail(CollaboratorRole role, Assessee assessee, SchoolService schoolService){
        if(role.equals(CollaboratorRole.Student)){
            this.student = schoolService.getStudenBy(assessee.getCollaborator().getPersonId());
        }
        return this;
    }
}