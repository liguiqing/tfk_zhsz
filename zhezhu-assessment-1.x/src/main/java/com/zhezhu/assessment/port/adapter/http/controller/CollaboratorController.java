package com.zhezhu.assessment.port.adapter.http.controller;

import com.zhezhu.assessment.application.collaborator.CollaboratorService;
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
    private CollaboratorService collaboratorService;

    /**
     * 将所有学校老师转换为评价者
     *
     * @param schoolId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/all/{schoolId}",method = RequestMethod.POST)
    public ModelAndView onToCollaborator(@PathVariable String schoolId)throws Exception{
        logger.debug("URL /collaborator/all/{} method=POST {}",schoolId);

        collaboratorService.toCollaborator(schoolId);
        return newModelAndViewBuilder("/collaborator/newCollaboratorSuccess").creat();
    }

    /**
     * 将学生转换为被评价者
     *
     * @param schoolId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/assessee/from/student/{schoolId}",method = RequestMethod.POST)
    public ModelAndView onStudentToAssessee(@PathVariable String schoolId)throws Exception{
        logger.debug("URL /collaborator/assessee/from/student/{} method=POST {}",schoolId);

        collaboratorService.studentToAssessee(schoolId);
        return newModelAndViewBuilder("/collaborator/newStudentToAssesseeSuccess").creat();
    }

    /**
     * 将某个学校的老师转换为评价者
     *
     * @param schoolId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/assessor/from/teacher/{schoolId}",method = RequestMethod.POST)
    public ModelAndView onTeacherToAssessor(@PathVariable String schoolId)throws Exception{
        logger.debug("URL /collaborator/assessor/from/teacher/{} method=POST ",schoolId);

        collaboratorService.teacherToAssessor(schoolId);
        return newModelAndViewBuilder("/collaborator/newTeacherToAssessorSuccess").creat();
    }

}