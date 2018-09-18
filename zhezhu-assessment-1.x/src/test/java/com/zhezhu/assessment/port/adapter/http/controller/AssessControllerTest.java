package com.zhezhu.assessment.port.adapter.http.controller;

import com.google.common.collect.Lists;
import com.zhezhu.assessment.application.assess.*;
import com.zhezhu.assessment.application.collaborator.CollaboratorData;
import com.zhezhu.assessment.application.collaborator.CollaboratorQueryService;
import com.zhezhu.assessment.application.index.IndexData;
import com.zhezhu.assessment.application.index.IndexQueryService;
import com.zhezhu.assessment.domain.model.assesse.RankCategory;
import com.zhezhu.assessment.domain.model.assesse.RankCategoryService;
import com.zhezhu.assessment.domain.model.assesse.RankScope;
import com.zhezhu.assessment.domain.model.collaborator.CollaboratorRole;
import com.zhezhu.assessment.domain.model.index.IndexCategory;
import com.zhezhu.commons.util.ClientType;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.assessment.AssessId;
import com.zhezhu.share.domain.id.assessment.AssesseeId;
import com.zhezhu.share.domain.id.assessment.AssessorId;
import com.zhezhu.share.domain.id.index.IndexId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.infrastructure.school.StudentData;
import com.zhezhu.share.infrastructure.school.TeacherData;
import com.zhezhu.zhezhu.controller.AbstractControllerTest;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Copyright (c) 2016,$today.year, Liguiqing
 **/

@ContextHierarchy({
        @ContextConfiguration(classes= {AssessController.class}),
        @ContextConfiguration(locations = {"classpath:servlet-context-test.xml"})
})
public class AssessControllerTest extends AbstractControllerTest {

    @Autowired
    @InjectMocks
    private AssessController controller;

    @Mock
    private AssessApplicationService assessApplicationService;

    @Mock
    private AssessQueryService assessQueryService;

    @Mock
    private CollaboratorQueryService collaboratorQueryService;

    @Mock
    private IndexQueryService indexQueryService;

    @Mock
    private RankCategoryService rankCategoryService;

    @Test
    public void onGenAssessTeamOf()throws Exception{
        this.mvc.perform(post("/assess/team/gen/"+new SchoolId().id()).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(view().name("/assess/newAssessTeamSuccess"));
    }

    @Test
    public void onAssess() throws Exception{
        assertNotNull(controller);
        AssesseeId assesseeId = new AssesseeId();
        AssessorId assessorId = new AssessorId();
        IndexId indexId = new IndexId();
        NewAssessCommand command  = NewAssessCommand.builder()
                .assesseeId(assesseeId.id())
                .assessorId(assessorId.id())
                .indexId(indexId.id())
                .score(10.0d)
                .word("亚马爹")
                .build();

        String content = toJsonString(command);

        doNothing().when(assessApplicationService).assess(any(NewAssessCommand.class));

        this.mvc.perform(post("/assess").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(content))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)));


        content = toJsonString(new NewAssessCommand[]{command});
        this.mvc.perform(post("/assess/more").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(content))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(view().name("/assess/newAssessSuccess"));
    }

    @Test
    public void onTeacherAssessingToStudentGet()throws Exception{
        PersonId teacherId = new PersonId();
        PersonId studentId = new PersonId();
        SchoolId schoolId = new SchoolId();
        AssessorId assessorId = new AssessorId();
        AssesseeId assesseeId = new AssesseeId();

        CollaboratorData teacher = CollaboratorData.builder()
                .schoolId(schoolId.id())
                .assessorId(assessorId.id())
                .teacher(TeacherData.builder().schoolId(schoolId.id()).name("T1").build())
                .build();
        CollaboratorData student = CollaboratorData.builder()
                .schoolId(schoolId.id())
                .assesseeId(assesseeId.id())
                .student(StudentData.builder().schoolId(schoolId.id()).name("S1").build())
                .build();

        when(collaboratorQueryService.getAssessorBy(any(String.class), any(String.class), any(CollaboratorRole.class))).thenReturn(teacher);
        when(collaboratorQueryService.getAssesseeBy(any(String.class), any(String.class), eq(CollaboratorRole.Student))).thenReturn(student);
        ArrayList<IndexData> indexData = new ArrayList<>();
        indexData.add(IndexData.builder()
                .plus(true)
                .alias("Index")
                .score(7)
                .name("Index")
                .group("1")
                .description("desc1")
                .categoryName(IndexCategory.Intelligence.name())
                .build()
                .addWebResource("icon","icon11", ClientType.WeChatApp)
                .addWebResource("icon","icon12", ClientType.PC));
        indexData.add(IndexData.builder()
                .plus(false)
                .alias("Index2")
                .score(6)
                .name("Index2")
                .group("1")
                .description("desc2")
                .categoryName(IndexCategory.Morals.name())
                .build().addWebResource("icon","icon21", ClientType.WeChatApp)
                .addWebResource("icon","icon22", ClientType.PC));

        when(indexQueryService.getOwnerIndexes(any(String.class),any(String.class),any(Boolean.class))).thenReturn(indexData);


        this.mvc.perform(get("/assess/teacher/to/student")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("teacherPersonId",teacherId.getId())
                .param("studentPersonId",studentId.getId())
                .param("schoolId",schoolId.getId()))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.assessor.schoolId", equalTo(schoolId.id())))
                .andExpect(jsonPath("$.assessee.schoolId", equalTo(schoolId.id())))
                .andExpect(jsonPath("$.assessor.assessorId", equalTo(assessorId.id())))
                .andExpect(jsonPath("$.assessor.teacher.name", equalTo("T1")))
                .andExpect(jsonPath("$.assessee.student.name", equalTo("S1")))
                .andExpect(jsonPath("$.assessee.student.schoolId", equalTo(schoolId.id())))
                .andExpect(jsonPath("$.indexes[0].alias", equalTo("Index")))
                .andExpect(jsonPath("$.indexes[0].plus", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.indexes[0].categoryName", equalTo(IndexCategory.Intelligence.name())))
                .andExpect(jsonPath("$.indexes[0].recommendIcon", equalTo("icon11")))
                .andExpect(jsonPath("$.indexes[1].alias", equalTo("Index2")))
                .andExpect(jsonPath("$.indexes[1].plus", is(Boolean.FALSE)))
                .andExpect(jsonPath("$.indexes[1].categoryName", equalTo(IndexCategory.Morals.name())))
                .andExpect(view().name("/assess/newAssessList"));
        verify(indexQueryService,times(1)).getOwnerIndexes(any(String.class),any(String.class),any(Boolean.class));
    }

    @Test
    public void onTeacherAssessingToStudentPost()throws Exception{
        PersonId teacherId = new PersonId();
        PersonId studentId = new PersonId();
        SchoolId schoolId = new SchoolId();
        String[] assessIds = {new AssessId().id(),new AssessId().id(),new AssessId().id(),new AssessId().id(),new AssessId().id()};
        ArrayList<IndexAssess> indexAssesses = Lists.newArrayList();
        indexAssesses.add(new IndexAssess(new IndexId().id(),4));
        indexAssesses.add(new IndexAssess(new IndexId().id(),5));
        indexAssesses.add(new IndexAssess(new IndexId().id(),7));
        indexAssesses.add(new IndexAssess(new IndexId().id(),9));
        indexAssesses.add(new IndexAssess("word"));
        NewTeacherAssessStudentCommand command = NewTeacherAssessStudentCommand.builder()
                .studentPersonId(studentId.id())
                .teacherPersonId(teacherId.id())
                .schoolId(schoolId.id())
                .assesses(indexAssesses)
                .build();

        String content = toJsonString(command);

        when(assessApplicationService.teacherAssessStudent(any(NewTeacherAssessStudentCommand.class))).thenReturn(assessIds);

        this.mvc.perform(post("/assess/teacher/to/student")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(content))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.assessIds[0]", equalTo(assessIds[0])))
                .andExpect(view().name("/assess/newAssessSuccess"));
        verify(assessApplicationService,times(1)).teacherAssessStudent(any(NewTeacherAssessStudentCommand.class));
    }

    @Test
    public void onGetAssess()throws Exception{
        CollaboratorRole role = CollaboratorRole.Student;
        PersonId studentId = new PersonId();
        ClazzId clazzId = new ClazzId();
        SchoolId schoolId = new SchoolId();
        AssesseeId assesseeId = new AssesseeId();
        CollaboratorData student = CollaboratorData.builder()
                .schoolId(schoolId.id())
                .assesseeId(assesseeId.id())
                .student(StudentData.builder().schoolId(schoolId.id()).name("S1").build())
                .build();
        List<AssessData> data = Lists.newArrayList();
        data.add(AssessData.builder().indexScore(2).clazzId(clazzId.id()).indexName("I1").assesseeId(new AssesseeId().id()).doneDate(new Date()).assesseeName("S1").build());
        data.add(AssessData.builder().indexScore(5).clazzId(clazzId.id()).indexName("I2").assesseeId(new AssesseeId().id()).doneDate(new Date()).assesseeName("S1").build());
        data.add(AssessData.builder().indexScore(6).clazzId(clazzId.id()).indexName("I3").assesseeId(new AssesseeId().id()).doneDate(new Date()).assesseeName("S1").build());
        data.add(AssessData.builder().indexScore(-6).clazzId(clazzId.id()).indexName("I4").assesseeId(new AssesseeId().id()).doneDate(new Date()).assesseeName("S1").build());

        when(collaboratorQueryService.getAssesseeBy(eq(schoolId.id()),eq(studentId.id()),eq(role),eq(Boolean.FALSE))).thenReturn(student);
        when(assessQueryService.getAssessBetween(any(String.class),any(Date.class),any(Date.class))).thenReturn(data);

        this.mvc.perform(get("/assess/list/all/"+schoolId.id()+"/"+role.name()+"/"+studentId.id())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("from","2018-08-01")
                .param("to","2018-10-01"))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.assesses[0].indexName", equalTo(data.get(0).getIndexName())))
                .andExpect(jsonPath("$.assesses[1].indexName", equalTo(data.get(1).getIndexName())))
                .andExpect(view().name("/assess/assessList"));
        when(assessQueryService.getAssessBetween(any(String.class),any(),any())).thenReturn(data);
        this.mvc.perform(get("/assess/list/all/"+schoolId.id()+"/"+role.name()+"/"+studentId.id())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.assesses[0].indexName", equalTo(data.get(0).getIndexName())))
                .andExpect(jsonPath("$.assesses[1].indexName", equalTo(data.get(1).getIndexName())))
                .andExpect(view().name("/assess/assessList"));
        verify(collaboratorQueryService,times(2)).getAssesseeBy(eq(schoolId.id()),eq(studentId.id()),eq(role),eq(Boolean.FALSE));
    }

    @Test
    public void onGetAssessOfCategory()throws Exception{
        CollaboratorRole role = CollaboratorRole.Student;
        PersonId studentId = new PersonId();
        ClazzId clazzId = new ClazzId();
        SchoolId schoolId = new SchoolId();
        AssesseeId assesseeId = new AssesseeId();
        CollaboratorData student = CollaboratorData.builder()
                .schoolId(schoolId.id())
                .assesseeId(assesseeId.id())
                .student(StudentData.builder().schoolId(schoolId.id()).name("S1").build())
                .build();
        List<AssessData> data = Lists.newArrayList();
        data.add(AssessData.builder().indexScore(2).clazzId(clazzId.id()).indexName("I1").assesseeId(new AssesseeId().id()).doneDate(new Date()).assesseeName("S1").build());
        data.add(AssessData.builder().indexScore(5).clazzId(clazzId.id()).indexName("I2").assesseeId(new AssesseeId().id()).doneDate(new Date()).assesseeName("S1").build());
        data.add(AssessData.builder().indexScore(6).clazzId(clazzId.id()).indexName("I3").assesseeId(new AssesseeId().id()).doneDate(new Date()).assesseeName("S1").build());
        data.add(AssessData.builder().indexScore(-6).clazzId(clazzId.id()).indexName("I4").assesseeId(new AssesseeId().id()).doneDate(new Date()).assesseeName("S1").build());

        when(collaboratorQueryService.getAssesseeBy(eq(schoolId.id()),eq(studentId.id()),eq(role),eq(Boolean.FALSE))).thenReturn(student);
        when(assessQueryService.getAssessBetween(any(String.class),any(Date.class),any(Date.class))).thenReturn(data);

        RankCategory dayCategory = RankCategory.Day;
        Date from = DateUtilWrapper.toDate("2018-09-01","yyyy-MM-dd");
        Date to = DateUtilWrapper.toDate("2018-09-01","yyyy-MM-dd");
        when(rankCategoryService.from(eq(dayCategory))).thenReturn(from);
        when(rankCategoryService.to(eq(dayCategory))).thenReturn(to);

        this.mvc.perform(get("/assess/list/category/"+schoolId.getId()+"/"+studentId.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("category",dayCategory.name())
                .param("role",CollaboratorRole.Student.name()))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.assesses[0].indexName", equalTo(data.get(0).getIndexName())))
                .andExpect(jsonPath("$.assesses[1].indexName", equalTo(data.get(1).getIndexName())))
                .andExpect(view().name("/assess/assessList"));

        this.mvc.perform(get("/assess/list/category/"+schoolId.getId()+"/"+studentId.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.assesses[0].indexName", equalTo(data.get(0).getIndexName())))
                .andExpect(jsonPath("$.assesses[1].indexName", equalTo(data.get(1).getIndexName())))
                .andExpect(view().name("/assess/assessList"));
        verify(collaboratorQueryService,times(2)).getAssesseeBy(eq(schoolId.id()),eq(studentId.id()),eq(role),eq(Boolean.FALSE));
    }

    @Test
    public void onAssessRankOf()throws Exception{
        doNothing().when(assessApplicationService).rank(any(String.class));
        this.mvc.perform(post("/assess/rank/"+new SchoolId().getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(view().name("/assess/assessRankList"));
        verify(assessApplicationService,times(1)).rank(any(String.class));
    }

    @Test
    public void onGetPersonalAssessRankInClazz()throws Exception{
        RankCategory category = RankCategory.Day;
        PersonId studentId = new PersonId();
        SchoolId schoolId = new SchoolId();
        ClazzId clazzId = new ClazzId();
        List<SchoolAssessRankData> data = mockRankData(category,RankScope.Clazz,RankScope.Clazz,studentId,clazzId,schoolId,"");

        this.mvc.perform(get("/assess/rank/personal/clazz/"+clazzId.id()+"/"+studentId.id())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("category",category.name()))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.ranks[0].rank", equalTo(data.get(0).getRank())))
                .andExpect(jsonPath("$.ranks[0].rankNode", equalTo("2018-09-01")))
                .andExpect(view().name("/assess/assessRankList"));

        this.mvc.perform(get("/assess/rank/personal/clazz/"+clazzId.id()+"/"+studentId.id())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("category",category.name()))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.ranks[0].rank", equalTo(data.get(4).getRank())))
                .andExpect(jsonPath("$.ranks[0].rankNode", equalTo("2018-09-05")))
                .andExpect(view().name("/assess/assessRankList"));

        this.mvc.perform(get("/assess/rank/personal/clazz/"+clazzId.id()+"/"+studentId.id())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.ranks[0].rank", equalTo(data.get(4).getRank())))
                .andExpect(jsonPath("$.ranks[0].rankNode", equalTo("2018-09-05")))
                .andExpect(view().name("/assess/assessRankList"));
        verify(assessQueryService,times(3)).getPersonalRanks(eq(clazzId.id()),eq(studentId.id()),eq(RankCategory.Day), eq(RankScope.Clazz),any(Date.class),any(Date.class));
        verify(assessQueryService,times(2)).getPersonalLastRanksThisYear(eq(studentId.id()));
    }

    @Test
    public void onGetPersonalAssessRankInSchool()throws Exception{
        RankCategory category = RankCategory.Day;
        PersonId studentId = new PersonId();
        SchoolId schoolId = new SchoolId();
        ClazzId clazzId = new ClazzId();
        List<SchoolAssessRankData> data = mockRankData(category,RankScope.Clazz,RankScope.School,studentId,clazzId,schoolId,"");

        this.mvc.perform(get("/assess/rank/personal/grade/"+schoolId.id()+"/"+studentId.id())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("category",category.name()))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.ranks[0].rank", equalTo(data.get(0).getRank())))
                .andExpect(jsonPath("$.ranks[0].rankNode", equalTo("2018-09-01")))
                .andExpect(view().name("/assess/assessRankList"));

        this.mvc.perform(get("/assess/rank/personal/grade/"+schoolId.id()+"/"+studentId.id())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("category",category.name()))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.ranks[0].rank", equalTo(data.get(4).getRank())))
                .andExpect(jsonPath("$.ranks[0].rankNode", equalTo("2018-09-05")))
                .andExpect(view().name("/assess/assessRankList"));

        this.mvc.perform(get("/assess/rank/personal/grade/"+schoolId.id()+"/"+studentId.id())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.ranks[0].rank", equalTo(data.get(4).getRank())))
                .andExpect(jsonPath("$.ranks[0].rankNode", equalTo("2018-09-05")))
                .andExpect(view().name("/assess/assessRankList"));
        verify(assessQueryService,times(3)).getPersonalRanks(eq(schoolId.id()),eq(studentId.id()),eq(RankCategory.Day), eq(RankScope.School),any(Date.class),any(Date.class));
        verify(assessQueryService,times(2)).getPersonalLastRanksThisYear(eq(studentId.id()));
    }

    @Test
    public void onGetAssessRankCategoryOfClazz()throws Exception{
        PersonId studentId = new PersonId();
        SchoolId schoolId = new SchoolId();
        ClazzId clazzId = new ClazzId();

        for(RankCategory category:RankCategory.values()){
            List<SchoolAssessRankData> data = mockRankData(category,RankScope.Clazz,RankScope.Clazz,studentId,clazzId,schoolId, LocalDate.now().toString());
            testClazz(clazzId.id(),"clazz",category.name().toLowerCase(),data);
        }
    }

    @Test
    public void onGetAssessRankCategoryOfGrade()throws Exception{
        PersonId studentId = new PersonId();
        SchoolId schoolId = new SchoolId();
        ClazzId clazzId = new ClazzId();

        for(RankCategory category:RankCategory.values()){
            List<SchoolAssessRankData> data = mockRankData(category,RankScope.School,RankScope.School,studentId,clazzId,schoolId, LocalDate.now().toString());
            testClazz(clazzId.id(),"grade",category.name().toLowerCase(),data);
        }
    }

    private void testClazz(String id,String scope,String node,List<SchoolAssessRankData> data) throws Exception{
        this.mvc.perform(get("/assess/rank/"+scope+"/"+node+"/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("showLastIfNone","false"))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.ranks[0].rank", equalTo(data.get(0).getRank())))
                .andExpect(jsonPath("$.ranks[0].rankNode", equalTo("2018-09-01")))
                .andExpect(view().name("/assess/assessRankList"));
        this.mvc.perform(get("/assess/rank/"+scope+"/"+node+"/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("showLastIfNone","true"))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.ranks[0].rank", equalTo(data.get(4).getRank())))
                .andExpect(jsonPath("$.ranks[0].rankNode", equalTo("2018-09-05")))
                .andExpect(view().name("/assess/assessRankList"));
    }


    private List<SchoolAssessRankData> mockRankData(RankCategory category,RankScope scope,
                                                    RankScope queryScope,PersonId studentId,
                                                    ClazzId clazzId,SchoolId schoolId,
                                                    String node){
        Date from = DateUtilWrapper.now();
        Date to = from;
        when(rankCategoryService.from(eq(category))).thenReturn(from,to);
        when(rankCategoryService.to(eq(category))).thenReturn(from,to);
        when(rankCategoryService.node(category)).thenReturn(node);

        List<SchoolAssessRankData> data = Lists.newArrayList();
        data.add(SchoolAssessRankData.builder().promote(1).rank(10).rankNode("2018-09-01").rankDate(from).rankCategory(category.name()).rankScope(scope.name()).personId(studentId.id()).schoolId(schoolId.id()).clazzId(clazzId.id()).build());
        data.add(SchoolAssessRankData.builder().promote(1).rank(9).rankNode("2018-09-02").rankDate(from).rankCategory(category.name()).rankScope(scope.name()).personId(studentId.id()).schoolId(schoolId.id()).clazzId(clazzId.id()).build());
        data.add(SchoolAssessRankData.builder().promote(2).rank(7).rankNode("2018-09-03").rankDate(from).rankCategory(category.name()).rankScope(scope.name()).personId(studentId.id()).schoolId(schoolId.id()).clazzId(clazzId.id()).build());
        data.add(SchoolAssessRankData.builder().promote(1).rank(6).rankNode("2018-09-04").rankDate(from).rankCategory(category.name()).rankScope(scope.name()).personId(studentId.id()).schoolId(schoolId.id()).clazzId(clazzId.id()).build());
        data.add(SchoolAssessRankData.builder().promote(3).rank(3).rankNode("2018-09-05").rankDate(from).rankCategory(category.name()).rankScope(scope.name()).personId(studentId.id()).schoolId(schoolId.id()).clazzId(clazzId.id()).build());

        when(assessQueryService.getPersonalRanks(any(String.class),any(String.class),eq(category), eq(queryScope),eq(from),eq(to))).thenReturn(data).thenReturn(null);
        when(assessQueryService.getPersonalLastRanksThisYear(eq(studentId.id()))).thenReturn(data.get(4));
        when(assessQueryService.getTeamRanks(any(String.class),eq(category), eq(queryScope),eq(node),eq(from),eq(to))).thenReturn(data).thenReturn(null);
        List<SchoolAssessRankData> data2 = data.stream().sorted(Comparator.comparing(SchoolAssessRankData::getRankNode).reversed()).collect(Collectors.toList());
        when(assessQueryService.getTeamLastRanks(any(String.class),eq(category), eq(queryScope),eq(node))).thenReturn(data2);

        return data;
    }

}