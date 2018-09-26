package com.zhezhu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.zhezhu.access.application.wechat.BindCommand;
import com.zhezhu.access.application.wechat.FollowerData;
import com.zhezhu.access.domain.model.wechat.WeChatCategory;
import com.zhezhu.assessment.application.assess.AssessData;
import com.zhezhu.assessment.application.assess.SchoolAssessRankData;
import com.zhezhu.assessment.domain.model.assesse.RankCategory;
import com.zhezhu.assessment.domain.model.assesse.RankScope;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.school.SchoolId;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
public class JsonString {

    @Test
    public void test()throws Exception{
        PersonId studentId = new PersonId();
        SchoolId schoolId = new SchoolId();
        RankCategory dayCategory = RankCategory.Day;
        Date from = DateUtilWrapper.toDate("2018-09-01","yyyy-MM-dd");
        Date to = DateUtilWrapper.toDate("2018-09-01","yyyy-MM-dd");
        List<SchoolAssessRankData> data = Lists.newArrayList();
        data.add(SchoolAssessRankData.builder().promote(1).rank(10).rankNode("2018-09-01").rankDate(from).rankCategory(dayCategory.name()).rankScope(RankScope.Clazz.name()).personId(studentId.id()).schoolId(schoolId.id()).build());
        data.add(SchoolAssessRankData.builder().promote(1).rank(9).rankNode("2018-09-02").rankDate(from).rankCategory(dayCategory.name()).rankScope(RankScope.Clazz.name()).personId(studentId.id()).schoolId(schoolId.id()).build());
        data.add(SchoolAssessRankData.builder().promote(2).rank(7).rankNode("2018-09-03").rankDate(from).rankCategory(dayCategory.name()).rankScope(RankScope.Clazz.name()).personId(studentId.id()).schoolId(schoolId.id()).build());
        data.add(SchoolAssessRankData.builder().promote(1).rank(6).rankNode("2018-09-04").rankDate(from).rankCategory(dayCategory.name()).rankScope(RankScope.Clazz.name()).personId(studentId.id()).schoolId(schoolId.id()).build());
        data.add(SchoolAssessRankData.builder().promote(3).rank(3).rankNode("2018-09-05").rankDate(from).rankCategory(dayCategory.name()).rankScope(RankScope.Clazz.name()).personId(studentId.id()).schoolId(schoolId.id()).build());

        BindCommand command = BindCommand.builder()
                .wechatOpenId("")
                .phone("")
                .name("")
                .category(WeChatCategory.Teacher.name())

                .build().addFollower(FollowerData.builder().build());

        log.debug(toJsonString(command));
    }

    protected String toJsonString(Object o)throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(o);
        return content;
    }
}