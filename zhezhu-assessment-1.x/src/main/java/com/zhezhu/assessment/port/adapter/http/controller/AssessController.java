package com.zhezhu.assessment.port.adapter.http.controller;

import com.zhezhu.assessment.application.assess.*;
import com.zhezhu.assessment.application.collaborator.CollaboratorData;
import com.zhezhu.assessment.application.collaborator.CollaboratorQueryService;
import com.zhezhu.assessment.application.index.IndexData;
import com.zhezhu.assessment.application.index.IndexQueryService;
import com.zhezhu.assessment.domain.model.assesse.RankCategory;
import com.zhezhu.assessment.domain.model.assesse.RankCategoryService;
import com.zhezhu.assessment.domain.model.assesse.RankScope;
import com.zhezhu.assessment.domain.model.collaborator.CollaboratorRole;
import com.zhezhu.commons.port.adaptor.http.controller.AbstractHttpController;
import com.zhezhu.commons.util.CollectionsUtilWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * 评价http适配器
 *
 * @author Liguiqing
 * @since V3.0
 */
@Controller
@RequestMapping("/assess")
public class AssessController extends AbstractHttpController {

    private AssessApplicationService assessApplicationService;

    private AssessQueryService assessQueryService;

    private CollaboratorQueryService collaboratorQueryService;

    private IndexQueryService indexQueryService;

    private RankCategoryService rankCategoryService;

    @Autowired
    public AssessController(Optional<AssessApplicationService> assessApplicationService,
                            Optional<AssessQueryService> assessQueryService,
                            Optional<CollaboratorQueryService> collaboratorQueryService,
                            Optional<IndexQueryService> indexQueryService,
                            Optional<RankCategoryService> rankCategoryService) {
        assessApplicationService.ifPresent(service -> this.assessApplicationService = service);
        assessQueryService.ifPresent(service->this.assessQueryService = service);
        collaboratorQueryService.ifPresent(service->this.collaboratorQueryService = service);
        indexQueryService.ifPresent(service->this.indexQueryService = service);
        rankCategoryService.ifPresent(service->this.rankCategoryService = service);
    }

    /**
     * 生成学校评价组
     *
     * @param schoolId {@link com.zhezhu.share.domain.id.school.SchoolId}
     * @return {@link ModelAndView}
     */
    @RequestMapping(value="/team/gen/{schoolId}",method = RequestMethod.POST)
    public ModelAndView onGenAssessTeamOf(@PathVariable String schoolId){
        logger.debug("URL /assess/team/gen/{} method=POST",schoolId);

        assessApplicationService.genAsseeTeamsOf(schoolId);
        return newModelAndViewBuilder("/assess/newAssessTeamSuccess").creat();
    }

    /**
     * 进行一次评价
     *
     * @param command {@link NewAssessCommand}
     * @return {@link ModelAndView}
     */
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView onAssess(@RequestBody NewAssessCommand command){
        logger.debug("URL /assess method=POST {}",command);

        assessApplicationService.assess(command);
        return newModelAndViewBuilder("/assess/newAssessSuccess").creat();
    }

    /**
     * 进行一组评价
     *
     * @param commands @NewAssessCommand
     * @return {@link ModelAndView}
     */
    @RequestMapping(value = "/more",method = RequestMethod.POST)
    public ModelAndView onAssesses(@RequestBody NewAssessCommand[] commands){
        logger.debug("URL /assess/more method=POST {}", Arrays.toString(commands));

        assessApplicationService.assesses(commands);
        return newModelAndViewBuilder("/assess/newAssessSuccess").creat();
    }

    /**
     * 获取老师评价学生的指标
     *
     * @param teacherPersonId {@link com.zhezhu.share.domain.id.PersonId}
     * @param studentPersonId {@link com.zhezhu.share.domain.id.PersonId}
     * @param schoolId {@link com.zhezhu.share.domain.id.school.SchoolId}
     *
     * @return {@link ModelAndView}
     */
    @RequestMapping(value="/teacher/to/student",method = RequestMethod.GET)
    public ModelAndView onTeacherAssessingToStudent(@RequestParam String teacherPersonId,
                                                    @RequestParam String studentPersonId,
                                                    @RequestParam String schoolId){
        logger.debug("URL /assess/teacher/to/student?teacherId={}&studentId={}&schoolId={} method=GET",teacherPersonId,studentPersonId,schoolId);

        CollaboratorData teacher = collaboratorQueryService.getAssessorBy(schoolId, teacherPersonId, CollaboratorRole.Teacher);
        CollaboratorData student = collaboratorQueryService.getAssesseeBy(schoolId, studentPersonId, CollaboratorRole.Student);
        int grade = student.getStudent().getGradeLevel();
        List<IndexData> indexes = indexQueryService.getOwnerIndexes(schoolId,grade+"",false);
        return newModelAndViewBuilder("/assess/newAssessList")
                .withData("assessor",teacher)
                .withData("assessee",student)
                .withData("indexes",indexes)
                .creat();
    }

    /**
     * 老师评价学生
     *
     * @param command {@link NewTeacherAssessStudentCommand}
     * @return {@link ModelAndView}
     *
     */
    @RequestMapping(value="/teacher/to/student",method = RequestMethod.POST)
    public ModelAndView onTeacherAssessToStudent(@RequestBody NewTeacherAssessStudentCommand command){
        logger.debug("URL /assess/teacher/to/student method=POST {}",command);

        String[] assessIds = assessApplicationService.teacherAssessStudent(command);
        return newModelAndViewBuilder("/assess/newAssessSuccess").withData("assessIds",assessIds).creat();
    }

    /**
     * 查询人员某个时段的全部评价
     *
     * @param schoolId {@link com.zhezhu.share.domain.id.school.SchoolId}
     * @param role value is one of Teacher;Student;Parent;
     * @param personId {@link com.zhezhu.share.domain.id.PersonId}
     * @param from 为null时为本周第一天
     * @param to   为null时为本周最后一天
     * @return  {@link ModelAndView}
     */
    @RequestMapping(value="/list/all/{schoolId}/{role}/{personId}",method = RequestMethod.GET)
    public ModelAndView onGetAssess(@PathVariable String schoolId,
                                    @PathVariable String role,
                                    @PathVariable String personId,
                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date to){
        logger.debug("URL /assess/list/all/{}/{}/{} method=GET ",schoolId,role,personId);

        CollaboratorRole role1 = CollaboratorRole.valueOf(role);
        CollaboratorData assessee = collaboratorQueryService.getAssesseeBy(schoolId,personId,role1,false);

        List<AssessData> data = assessQueryService.getAssessBetween(assessee.getAssesseeId(),from,to);
        return newModelAndViewBuilder("/assess/assessList").withData("assesses",data).creat();
    }

    /**
     * 查询人员某个时段某category全部评价<br>
     * 时段通过category进行自动计算
     *
     * @param schoolId {@link com.zhezhu.share.domain.id.school.SchoolId}
     * @param personId {@link com.zhezhu.share.domain.id.PersonId}
     * @param category {@link RankCategory}
     * @param role   {@link CollaboratorRole}
     * @return  {@link ModelAndView}
     */
    @RequestMapping(value="/list/category/{schoolId}/{personId}",method = RequestMethod.GET)
    public ModelAndView onGetAssessOfCategory(@PathVariable String schoolId,
                                          @PathVariable String personId,
                                          @RequestParam(required = false,defaultValue = "Day") String category,
                                          @RequestParam(required = false,defaultValue = "Student") String role){
        logger.debug("URL /assess/list/category/{}/{}?category={}&role={} method=GET ",schoolId,personId,category,role);

        Date from = rankCategoryService.from(RankCategory.valueOf(category));
        Date to = rankCategoryService.to(RankCategory.valueOf(category));
        CollaboratorData assessee = collaboratorQueryService.getAssesseeBy(schoolId,personId,CollaboratorRole.valueOf(role),false);
        List<AssessData> data = assessQueryService.getAssessBetween(assessee.getAssesseeId(),from,to);
        return newModelAndViewBuilder("/assess/assessList").withData("assesses",data).creat();
    }

    /**
     * 进行排名计算
     *
     * @param teamId Value of {@link com.zhezhu.share.domain.id.school.SchoolId}
     *               or {@link com.zhezhu.share.domain.id.school.ClazzId}
     * @return {@link ModelAndView}
     */
    @RequestMapping(value="/rank/{teamId}",method = RequestMethod.POST)
    public ModelAndView onAssessRankOf(@PathVariable String teamId){
        logger.debug("URL /assess/rank/{} method=POST ",teamId);

        assessApplicationService.rank(teamId);
        return newModelAndViewBuilder("/assess/assessRankList").creat();
    }

    /**
     * 查询个人的班内评价排名
     *
     * @param clazzId {@link com.zhezhu.share.domain.id.school.SchoolId}
     * @param personId {@link com.zhezhu.share.domain.id.PersonId}
     * @param category {@link RankCategory}
     * @return {@link ModelAndView}
     */
    @RequestMapping(value="/rank/personal/clazz/{clazzId}/{personId}",method = RequestMethod.GET)
    public ModelAndView onGetPersonalAssessRankInClazz(@PathVariable String clazzId,
                                                       @PathVariable String personId,
                                                       @RequestParam(required = false,defaultValue = "Day") String category){
        logger.debug("URL /assess/rank/personal/clazz/{}/{}?category={} method=GET ",clazzId,personId,category);

        return onGetPersonalAssessRankOf(clazzId,personId,RankCategory.valueOf(category),RankScope.Clazz);
    }

    /**
     * 查询个人的年级评价排名
     *
     * @param schoolId {@link com.zhezhu.share.domain.id.school.SchoolId}
     * @param personId {@link com.zhezhu.share.domain.id.PersonId}
     * @param category {@link RankCategory}
     * @return {@link ModelAndView}
     */
    @RequestMapping(value="/rank/personal/grade/{schoolId}/{personId}",method = RequestMethod.GET)
    public ModelAndView onGetPersonalAssessRankInSchool(@PathVariable String schoolId,
                                                @PathVariable String personId,
                                                @RequestParam(required = false,defaultValue = "Day") String category){
        logger.debug("URL /assess/rank/personal/grade/{}/{}?category={} method=GET ",schoolId,personId,category);

        return onGetPersonalAssessRankOf(schoolId,personId,RankCategory.valueOf(category),RankScope.School);
    }

    private ModelAndView onGetPersonalAssessRankOf(String teamId,String personId, RankCategory category,RankScope scope){

        Date from = rankCategoryService.from(category);
        Date to = rankCategoryService.to(category);
        List<SchoolAssessRankData> data = assessQueryService.getPersonalRanks(teamId,personId,category,scope,from,to);
        if(data == null){
            data = new ArrayList<>();
            data.add(assessQueryService.getPersonalLastRanksThisYear(personId));
        }
        return newModelAndViewBuilder("/assess/assessRankList").withData("ranks",data).creat();
    }

    /**
     * 查询当天班级所有学生评价排名
     *
     * @param clazzId {@link com.zhezhu.share.domain.id.school.ClazzId}
     * @param showLastIfNone if true return last ranks
     * @return {@link ModelAndView}
     */
    @RequestMapping(value="/rank/clazz/day/{clazzId}",method = RequestMethod.GET)
    public ModelAndView onGetAssessDayRankOfClazz(@PathVariable String clazzId,
                                                  @RequestParam(required = false) boolean showLastIfNone){
        logger.debug("URL /assess/rank/clazz/day/{}?showLastIfNode={} method=GET ",clazzId,showLastIfNone);

        return getTeamRanks(clazzId,showLastIfNone,RankCategory.Day,RankScope.Clazz);
    }

    /**
     * 查询本周班级所有学生评价排名
     *
     * @param clazzId {@link com.zhezhu.share.domain.id.school.ClazzId}
     * @param showLastIfNone if true return last ranks
     * @return {@link ModelAndView}
     */
    @RequestMapping(value="/rank/clazz/weekend/{clazzId}",method = RequestMethod.GET)
    public ModelAndView onGetAssessWeekendRankOfClazz(@PathVariable String clazzId,
                                               @RequestParam(required = false) boolean showLastIfNone){
        logger.debug("URL /assess/rank/clazz/weekend/{}?showLastIfNode={} method=GET ",clazzId,showLastIfNone);

        return getTeamRanks(clazzId,showLastIfNone,RankCategory.Weekend,RankScope.Clazz);
    }

    /**
     * 查询本月班级所有学生评价排名
     *
     * @param clazzId {@link com.zhezhu.share.domain.id.school.ClazzId}
     * @param showLastIfNone if true return last ranks
     * @return {@link ModelAndView}
     */
    @RequestMapping(value="/rank/clazz/month/{clazzId}",method = RequestMethod.GET)
    public ModelAndView onGetAssessMonthRankOfClazz(@PathVariable String clazzId,
                                                      @RequestParam(required = false) boolean showLastIfNone){
        logger.debug("URL /assess/rank/clazz/month/{}?showLastIfNode={} method=GET ",clazzId,showLastIfNone);

        return getTeamRanks(clazzId,showLastIfNone,RankCategory.Month,RankScope.Clazz);
    }

    /**
     * 查询本学期班级所有学生评价排名
     *
     * @param clazzId {@link com.zhezhu.share.domain.id.school.ClazzId}
     * @param showLastIfNone if true return last ranks
     * @return {@link ModelAndView}
     */
    @RequestMapping(value="/rank/clazz/term/{clazzId}",method = RequestMethod.GET)
    public ModelAndView onGetAssessTermRankOfClazz(@PathVariable String clazzId,
                                                    @RequestParam(required = false) boolean showLastIfNone){
        logger.debug("URL /assess/rank/clazz/term/{}?showLastIfNode={} method=GET ",clazzId,showLastIfNone);

        return getTeamRanks(clazzId,showLastIfNone,RankCategory.Term,RankScope.Clazz);
    }

    /**
     * 查询学年班级所有学生评价排名
     *
     * @param clazzId {@link com.zhezhu.share.domain.id.school.ClazzId}
     * @param showLastIfNone if true return last ranks
     * @return {@link ModelAndView}
     */
    @RequestMapping(value="/rank/clazz/year/{clazzId}",method = RequestMethod.GET)
    public ModelAndView onGetAssessYearRankOfClazz(@PathVariable String clazzId,
                                                   @RequestParam(required = false) boolean showLastIfNone){
        logger.debug("URL /assess/rank/clazz/year/{}?showLastIfNode={} method=GET ",clazzId,showLastIfNone);

        return getTeamRanks(clazzId,showLastIfNone,RankCategory.Year,RankScope.Clazz);
    }

    /**
     * 查询本周年级所有学生评价排名
     *
     * @param schoolId {@link com.zhezhu.share.domain.id.school.SchoolId}
     * @param showLastIfNone if true return last ranks
     * @return {@link ModelAndView}
     */
    @RequestMapping(value="/rank/grade/day/{schoolId}",method = RequestMethod.GET)
    public ModelAndView onGetAssessDayRankOfGrade(@PathVariable String schoolId,
                                               @RequestParam(required = false) boolean showLastIfNone){
        logger.debug("URL /assess/rank/grade/day/{}?showLastIfNode={} method=GET ",schoolId,showLastIfNone);

        return getTeamRanks(schoolId,showLastIfNone,RankCategory.Day,RankScope.School);
    }

    /**
     * 查询当天年级所有学生评价排名
     *
     * @param schoolId {@link com.zhezhu.share.domain.id.school.SchoolId}
     * @param showLastIfNone if true return last ranks
     * @return {@link ModelAndView}
     */
    @RequestMapping(value="/rank/grade/weekend/{schoolId}",method = RequestMethod.GET)
    public ModelAndView onGetAssessWeekendRankOfGrade(@PathVariable String schoolId,
                                               @RequestParam(required = false) boolean showLastIfNone){
        logger.debug("URL /assess/rank/grade/weekend/{}?showLastIfNode={} method=GET ",schoolId,showLastIfNone);

        return getTeamRanks(schoolId,showLastIfNone,RankCategory.Weekend,RankScope.School);
    }

    /**
     * 查询本月年级所有学生评价排名
     *
     * @param schoolId {@link com.zhezhu.share.domain.id.school.SchoolId}
     * @param showLastIfNone if true return last ranks
     * @return {@link ModelAndView}
     */
    @RequestMapping(value="/rank/grade/month/{schoolId}",method = RequestMethod.GET)
    public ModelAndView onGetAssessMonthRankOfGrade(@PathVariable String schoolId,
                                                      @RequestParam(required = false) boolean showLastIfNone){
        logger.debug("URL /assess/rank/grade/month/{}?showLastIfNode={} method=GET ",schoolId,showLastIfNone);

        return getTeamRanks(schoolId,showLastIfNone,RankCategory.Month,RankScope.School);
    }

    /**
     * 查询本学期年级所有学生评价排名
     *
     * @param schoolId {@link com.zhezhu.share.domain.id.school.SchoolId}
     * @param showLastIfNone if true return last ranks
     * @return {@link ModelAndView}
     */
    @RequestMapping(value="/rank/grade/term/{schoolId}",method = RequestMethod.GET)
    public ModelAndView onGetAssessTermRankOfGrade(@PathVariable String schoolId,
                                                    @RequestParam(required = false) boolean showLastIfNone){
        logger.debug("URL /assess/rank/grade/term/{}?showLastIfNode={} method=GET ",schoolId,showLastIfNone);

        return getTeamRanks(schoolId,showLastIfNone,RankCategory.Term,RankScope.School);
    }

    /**
     * 查询本学年级所有学生评价排名
     *
     * @param schoolId {@link com.zhezhu.share.domain.id.school.SchoolId}
     * @param showLastIfNone if true return last ranks
     * @return {@link ModelAndView}
     */
    @RequestMapping(value="/rank/grade/year/{schoolId}",method = RequestMethod.GET)
    public ModelAndView onGetAssessYearRankOfGrade(@PathVariable String schoolId,
                                                   @RequestParam(required = false) boolean showLastIfNone){
        logger.debug("URL /assess/rank/grade/year/{}?showLastIfNode={} method=GET ",schoolId,showLastIfNone);

        return getTeamRanks(schoolId,showLastIfNone,RankCategory.Year,RankScope.School);
    }

    private ModelAndView getTeamRanks(String teamId,boolean showLastIfNone,RankCategory category,RankScope scope){
        Date from = rankCategoryService.from(category);
        Date to = rankCategoryService.to(category);
        String node = rankCategoryService.node(category);
        List<SchoolAssessRankData> data = assessQueryService.getTeamRanks(teamId,category,scope,node,from,to);
        if(CollectionsUtilWrapper.isNullOrEmpty(data) && showLastIfNone){
            data = assessQueryService.getTeamLastRanks(teamId,category,scope,node);
        }
        return newModelAndViewBuilder("/assess/assessRankList").withData("ranks",data).creat();
    }

}