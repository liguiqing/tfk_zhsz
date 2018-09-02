package com.zhezhu.access.port.adapter.http.controller;

import com.zhezhu.access.application.school.*;
import com.zhezhu.commons.security.UserFace;
import com.zhezhu.commons.security.UserFaceService;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.access.ClazzFollowApplyId;
import com.zhezhu.share.domain.id.access.ClazzFollowAuditId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.zhezhu.controller.AbstractControllerTest;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/

@ContextHierarchy({
        @ContextConfiguration(classes= {SchoolApplyAndAuditController.class}),
        @ContextConfiguration(locations = {"classpath:servlet-context-test.xml"})
})
public class SchoolApplyAndAuditControllerTest extends AbstractControllerTest {

    @Autowired
    @InjectMocks
    private SchoolApplyAndAuditController controller;

    @Mock
    private SchoolApplyAndAuditQueryService applyAndAuditQueryService;;

    @Mock
    private SchoolApplyAndAuditApplicationService applyAndAuditApplicationService;

    @Mock
    private UserFaceService userFaceService;

    @Test
    public void onClazzFollowApply() throws Exception{
        ClazzFollowApplyCommand command = ClazzFollowApplyCommand.builder()
                .applierId(new PersonId().id())
                .applierName("Applier")
                .applierPhone("021564789")
                .applyDate(DateUtilWrapper.now())
                .applyingClazzId(new ClazzId().id())
                .applyingSchoolId(new SchoolId().id())
                .build();
        String content = toJsonString(command);
        when(applyAndAuditApplicationService.followClazzApply(any(ClazzFollowApplyCommand.class))).thenReturn("123456");
        this.mvc.perform(post("/apply/school").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(content))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.applyId", equalTo("123456")))
                .andExpect(view().name("/apply/clazzFollowApplySuccess"));
    }

    @Test
    public void onClazzFollowApplyCancel() throws Exception{
        doNothing().when(applyAndAuditApplicationService).followClazzApplyCancel(any(String.class));
        ClazzFollowApplyId applyId = new ClazzFollowApplyId();
        this.mvc.perform(delete("/apply/school/"+applyId.id()).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(view().name("/apply/clazzFollowApplyCancel"));
    }

    @Test
    public void onGetClazzFollowApplyAudited()throws Exception{
        List<ClazzFollowApplyAndAuditData> data = new ArrayList<>();
        for(int i=0;i<5;i++){
            data.add(ClazzFollowApplyAndAuditData.builder().clazzId(new ClazzId().id()).clazzName("className"+i).build());
        }

        when(applyAndAuditQueryService.getAuditedClazzs(any(String.class))).thenReturn(data);

        this.mvc.perform(get("/apply/audited/"+new  PersonId().id()).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.clazzs[0].clazzName", equalTo("className0")))
                .andExpect(jsonPath("$.clazzs[4].clazzName", equalTo("className4")))
                .andExpect(view().name("/apply/clazzFollowApplyAuditedList"));
    }

    @Test
    public void onFollowClazzAudit() throws Exception{
        ClazzFollowAuditCommand command = ClazzFollowAuditCommand.builder()
                .applyId(new ClazzFollowApplyId().id())
                .auditDate(DateUtilWrapper.now())
                .auditorId(new PersonId().id())
                .description("Description")
                .ok(true)
                .build();
        String content = toJsonString(command);
        UserFace user = mock(UserFace.class);
        when(userFaceService.getUser()).thenReturn(user);
        when(user.getUserPersonId()).thenReturn(new PersonId().id());
        when(applyAndAuditApplicationService.followClazzAudit(any(ClazzFollowAuditCommand.class))).thenReturn("123456");
        this.mvc.perform(post("/audit/school").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(content))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(jsonPath("$.auditId", equalTo("123456")))
                .andExpect(view().name("/apply/clazzFollowAuditSuccess"));
    }

    @Test
    public void onFollowClazzAuditCancel()throws Exception {
        doNothing().when(applyAndAuditApplicationService).followClazzApplyCancel(any(String.class));
        ClazzFollowAuditId auditId = new ClazzFollowAuditId();
        this.mvc.perform(delete("/audit/school/"+auditId.id()).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status.success", is(Boolean.TRUE)))
                .andExpect(view().name("/audit/clazzFollowAuditCancel"));
    }
}