package com.zhezhu.assessment.port.adapter.http.controller;

import com.zhezhu.assessment.application.assess.*;
import com.zhezhu.assessment.application.collaborator.CollaboratorData;
import com.zhezhu.assessment.application.collaborator.CollaboratorQueryService;
import com.zhezhu.assessment.application.index.IndexData;
import com.zhezhu.assessment.application.index.IndexQueryService;
import com.zhezhu.assessment.domain.model.assesse.RankCategory;
import com.zhezhu.assessment.domain.model.collaborator.CollaboratorRole;
import com.zhezhu.assessment.infrastructure.rank.RankCategoryService;
import com.zhezhu.commons.port.adaptor.http.controller.AbstractHttpController;
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
    private RankCategoryService rankCategoryService;

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
        CollaboratorData student = collaboratorQueryService.getAssesseeBy(schoolId, studentId, CollaboratorRole.Student);
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

    /**
     * 查询人员某个时段的全部评价
     *
     * @param schoolId
     * @param role
     * @param personId
     * @param from 为null时为本周第一天
     * @param to   为null时为本周最后一天
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/list/{schoolId}/{role}/{personId}",method = RequestMethod.GET)
    public ModelAndView onGetAssess(@PathVariable String schoolId,
                                    @PathVariable String role,
                                    @PathVariable String personId,
                                    @RequestParam(required = false) Date from,
                                    @RequestParam(required = false) Date to){
        logger.debug("URL /assess/list/{}/{}/{} method=GET ",schoolId,role,personId);

        CollaboratorData assessee = collaboratorQueryService.getAssesseeBy(schoolId,personId,CollaboratorRole.valueOf(role),false);
        List<AssessData> data = assessQueryService.getAssessOf(assessee.getAssesseeId(),from,to);
        return newModelAndViewBuilder("/assess/assessList").withData("assesses",data).creat();
    }

    /**
     * 查询人员某个时段的全部评价
     *
     * @param schoolId
     * @param schoolId
     * @param role
     * @param personId
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/list/node/{node}/{schoolId}/{role}/{personId}",method = RequestMethod.GET)
    public ModelAndView onGetAssessOfNode(@PathVariable String node,
                                          @PathVariable String schoolId,
                                    @PathVariable String role,
                                    @PathVariable String personId){
        logger.debug("URL /assess/list/{}/{}/{} method=GET ",schoolId,role,personId);

        Date from = rankCategoryService.from(RankCategory.valueOf(node));
        Date to = rankCategoryService.to(RankCategory.valueOf(node));
        CollaboratorData assessee = collaboratorQueryService.getAssesseeBy(schoolId,personId,CollaboratorRole.valueOf(role),false);
        List<AssessData> data = assessQueryService.getAssessOf(assessee.getAssesseeId(),from,to);
        return newModelAndViewBuilder("/assess/assessList").withData("assesses",data).creat();
    }
}