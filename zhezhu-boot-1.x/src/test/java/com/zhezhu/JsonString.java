package com.zhezhu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.zhezhu.access.application.wechat.WeChatData;
import com.zhezhu.access.domain.model.wechat.WeChatCategory;
import com.zhezhu.assessment.application.index.IndexData;
import com.zhezhu.commons.domain.Identities;
import com.zhezhu.commons.port.adaptor.http.controller.HttpAdaptorResponse;
import com.zhezhu.share.domain.id.IdPrefixes;
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
        log.debug(toJsonString(new HttpAdaptorResponse.Builder().code("").success().create()));
        ArrayList<IndexData> data = Lists.newArrayList();
        data.add(IndexData.builder().name("Name").build());
        data.add(IndexData.builder().group("Group").build());
        log.debug(toJsonString(data));
    }

    protected String toJsonString(Object o)throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(o);
        return content;
    }
}