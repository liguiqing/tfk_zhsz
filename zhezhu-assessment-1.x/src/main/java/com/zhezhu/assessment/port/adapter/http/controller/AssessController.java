package com.zhezhu.assessment.port.adapter.http.controller;

import com.zhezhu.assessment.application.assess.*;
import com.zhezhu.assessment.application.collaborator.CollaboratorData;
import com.zhezhu.assessment.application.collaborator.CollaboratorQueryService;
import com.zhezhu.assessment.application.index.IndexData;
import com.zhezhu.assessment.application.index.IndexQueryService;
import com.zhezhu.assessment.domain.model.collaborator.CollaboratorRole;
import com.zhezhu.commons.port.adaptor.http.controller.AbstractHttpController;
import com.zhezhu.share.infrastructure.school.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Controller
@RequestMapping("/assess")
public class AssessController extends AbstractHttpController {

    @Autowired(required = false)
    private AssessApplicationService assessApplicationService;

    @Autowired(required = false)
    private AssessQueryService assessQueryService;

    @Autowired(required = false)
    private CollaboratorQueryService collaboratorQueryService;

    @Autowired(required = false)
    private IndexQueryService indexQueryService;

    @Autowired(required = false)
    private SchoolService schoolService;

    /**
     * 进行一次评价
     *
     * @param command
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView onAssess(@RequestBody NewAssessCommand command){
        logger.debug("URL /assess method=POST {}",command);

        assessApplicationService.assess(command);
        return newModelAndViewBuilder("/assess/newAssessSuccess").creat();
    }

    /**
     * 老师评价学生
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/teacher/to/student",method = RequestMethod.GET)
    public ModelAndView onTeacherAssessingToStudent(@RequestParam String teacherId,
                                                    @RequestParam String studentId,
                                                    @RequestParam String schoolId){
        logger.debug("URL /assess/teacher/to/student?teacherId={}&studentId={} method=POST",teacherId,studentId);
        CollaboratorData teacher = collaboratorQueryService.getAssessorBy(schoolId, teacherId, CollaboratorRole.Teacher);
        CollaboratorData student = collaboratorQueryService.getAssessorBy(schoolId, studentId, CollaboratorRole.Student);
        int grade = student.getStudent().getGradeLevel();
        List<IndexData> indexes = indexQueryService.getOwnerIndexes(schoolId,grade+"",false);
        return newModelAndViewBuilder("/assess/newAssessSuccess")
                .withData("assessor",teacher)
                .withData("assessee",student)
                .withData("indexes",indexes)
                .creat();
    }

    /**
     * 老师评价学生
     *
     * @param command
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/teacher/to/student",method = RequestMethod.POST)
    public ModelAndView onTeacherAssessToStudent(@RequestBody NewTeacherAssessStudentCommand command){
        logger.debug("URL /assess/teacher/to/student method=POST {}",command);
        String assessId = assessApplicationService.teacherAssessStudent(command);
        return newModelAndViewBuilder("/assess/newAssessSuccess").withData("assessId",assessId).creat();
    }

    @RequestMapping(value="/assessee/{assesseeId}",method = RequestMethod.GET)
    public ModelAndView onGetAssess(@PathVariable String assesseeId,
                                    @RequestParam(required = false) Date from,
                                    @RequestParam(required = false) Date to)throws Exception{
        logger.debug("URL /assess/assessee/{} method=GET {}",assesseeId);

        List<AssessData> data = assessQueryService.getAssessOf(assesseeId,from,to);
        return newModelAndViewBuilder("/assess/newAssessSuccess").withData("assesses",data).creat();
    }
}