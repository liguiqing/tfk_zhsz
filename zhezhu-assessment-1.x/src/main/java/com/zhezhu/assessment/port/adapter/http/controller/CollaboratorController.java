package com.zhezhu.assessment.port.adapter.http.controller;

import com.zhezhu.assessment.application.collaborator.CollaboratorData;
import com.zhezhu.assessment.application.collaborator.CollaboratorQueryService;
import com.zhezhu.assessment.application.collaborator.CollaboratorApplicationService;
import com.zhezhu.assessment.domain.model.collaborator.CollaboratorRole;
import com.zhezhu.commons.port.adaptor.http.controller.AbstractHttpController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Controller
@RequestMapping("/collaborator")
public class CollaboratorController extends AbstractHttpController {

    @Autowired(required = false)
    private CollaboratorApplicationService collaboratorService;

    @Autowired(required = false)
    private CollaboratorQueryService collaboratorQueryService;

    /**
     * 将所有学校老师转换为评价者
     *
     * @param schoolId
     * @return
     * @
     */
    @RequestMapping(value = "/all/{schoolId}",method = RequestMethod.POST)
    public ModelAndView onToCollaborator(@PathVariable String schoolId){
        logger.debug("URL /collaborator/all/{} method=POST {}",schoolId);

        collaboratorService.toCollaborator(schoolId);
        return newModelAndViewBuilder("/collaborator/newCollaboratorSuccess").creat();
    }

    /**
     * 将学生转换为被评价者
     *
     * @param schoolId
     * @return
     * @
     */
    @RequestMapping(value = "/assessee/from/student/{schoolId}",method = RequestMethod.POST)
    public ModelAndView onStudentToAssessee(@PathVariable String schoolId){
        logger.debug("URL /collaborator/assessee/from/student/{} method=POST {}",schoolId);

        collaboratorService.studentToAssessee(schoolId);
        return newModelAndViewBuilder("/collaborator/newStudentToAssesseeSuccess").creat();
    }

    /**
     * 将某个学校的老师转换为评价者
     *
     * @param schoolId
     * @return
     * @
     */
    @RequestMapping(value = "/assessor/from/teacher/{schoolId}",method = RequestMethod.POST)
    public ModelAndView onTeacherToAssessor(@PathVariable String schoolId){
        logger.debug("URL /collaborator/assessor/from/teacher/{} method=POST ",schoolId);

        collaboratorService.teacherToAssessor(schoolId);
        return newModelAndViewBuilder("/collaborator/newTeacherToAssessorSuccess").creat();
    }


    /**
     * 取老师的评价者身份
     *
     * @param personId
     * @return
     * @
     */
    @RequestMapping(value = "/assessor/teacher/{schoolId}/{personId}",method = RequestMethod.GET)
    public ModelAndView onGetTeacherAsAssessor(@PathVariable String schoolId,@PathVariable String personId){
        logger.debug("URL /collaborator/assessor/teacher/{}/{} method=GET ",schoolId,personId);

        CollaboratorData assessor = collaboratorQueryService.getAssessorBy(schoolId,personId, CollaboratorRole.Teacher);
        return newModelAndViewBuilder("/collaborator/assessorInfo").withData("assessor",assessor).creat();
    }

    /**
     * 取学生的被评价者身份
     *
     * @param personId
     * @return
     * @
     */
    @RequestMapping(value = "/assessee/student/{schoolId}/{personId}",method = RequestMethod.GET)
    public ModelAndView onGetStudentAsAssessee(@PathVariable String schoolId,@PathVariable String personId){
        logger.debug("URL /collaborator/assessee/student/{}/{} method=GET ",schoolId,personId);

        CollaboratorData assessee = collaboratorQueryService.getAssesseeBy(schoolId,personId, CollaboratorRole.Student);
        return newModelAndViewBuilder("/collaborator/assesseeInfo").withData("assessee",assessee).creat();
    }
}