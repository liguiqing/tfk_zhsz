package com.zhezhu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
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

        log.debug(toJsonString(data));


        Map<Integer,List<Integer>> m1 = new HashMap<>();
        m1.put(1, Lists.newArrayList(1, 2, 3));
        m1.put(2, Lists.newArrayList(1, 2, 3));
        m1.put(3, Lists.newArrayList(1, 2, 3));

        Map<Integer,List<Integer>> m2 = new HashMap<>();
        m2.put(1, Lists.newArrayList(1, 2, 3));
        m2.put(2, Lists.newArrayList(2, 3, 4));
        m2.put(4, Lists.newArrayList(1, 2, 3));
        //List ll = m2.keySet().stream().map(i->m1.computeIfPresent(i,(k1,k2)->m2.get(k1))).collect(Collectors.toList());
        m2.entrySet().stream().map(v->{
            m1.merge(v.getKey(),v.getValue(),(x,y)-> {
                x.addAll(y);
                return x;
            });
            return v;
        }).count();
        //m1.entrySet().stream().collect(Integer::new,x->x,y->y,(a,b)->a);
        System.out.println();
    }

    protected String toJsonString(Object o)throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(o);
        return content;
    }
}