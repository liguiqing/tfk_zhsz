package com.zhezhu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.zhezhu.access.application.wechat.WeChatData;
import com.zhezhu.access.domain.model.wechat.WeChatCategory;
import com.zhezhu.assessment.application.assess.IndexAssess;
import com.zhezhu.assessment.application.assess.NewTeacherAssessStudentCommand;
import com.zhezhu.assessment.application.index.IndexData;
import com.zhezhu.commons.domain.Identities;
import com.zhezhu.commons.port.adaptor.http.controller.HttpAdaptorResponse;
import com.zhezhu.share.domain.id.IdPrefixes;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.index.IndexId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.sm.application.data.StudentData;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
public class JsonString {

    @Test
    public void test()throws Exception{
        ArrayList<IndexAssess> data = Lists.newArrayList();
        data.add(new IndexAssess(new IndexId().id(),5));
        data.add(new IndexAssess(new IndexId().id(),-5));
        data.add(new IndexAssess(""));


        NewTeacherAssessStudentCommand command = NewTeacherAssessStudentCommand.builder()
                .schoolId(new SchoolId().id())
                .teacherPersonId(new PersonId().id())
                .studentPersonId(new PersonId().id())
                .assesses(data)
                .build();
        log.debug(toJsonString(command));

    }

    protected String toJsonString(Object o)throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(o);
        return content;
    }
}