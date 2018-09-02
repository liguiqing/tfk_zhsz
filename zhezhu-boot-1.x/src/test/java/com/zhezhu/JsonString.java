package com.zhezhu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhezhu.access.application.school.ClazzFollowApplyAndAuditData;
import com.zhezhu.access.application.school.ClazzFollowApplyCommand;
import com.zhezhu.access.application.wechat.WechatData;
import com.zhezhu.access.domain.model.wechat.WebAccessToken;
import com.zhezhu.commons.port.adaptor.http.controller.HttpAdaptorResponse;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.school.Grade;
import com.zhezhu.sm.application.data.ClazzData;
import com.zhezhu.sm.application.school.SchoolData;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
public class JsonString {

    @Test
    public void test()throws Exception{
        log.debug(toJsonString(new HttpAdaptorResponse.Builder().code("").success().create()));
        List<ClazzFollowApplyAndAuditData> data = new ArrayList<>();
        for(int i=0;i<1;i++){
            data.add(ClazzFollowApplyAndAuditData.builder().clazzId(new ClazzId().id()).clazzName("className"+i).build());
        }
        log.debug(toJsonString(data));
    }

    protected String toJsonString(Object o)throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(o);
        return content;
    }
}