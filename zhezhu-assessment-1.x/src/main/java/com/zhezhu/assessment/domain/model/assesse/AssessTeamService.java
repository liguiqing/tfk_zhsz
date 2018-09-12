package com.zhezhu.assessment.domain.model.assesse;

import com.google.common.collect.Lists;
import com.zhezhu.commons.util.CollectionsUtilWrapper;
import com.zhezhu.share.domain.id.assessment.AssessTeamId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.infrastructure.school.ClazzData;
import com.zhezhu.share.infrastructure.school.SchoolData;
import com.zhezhu.share.infrastructure.school.SchoolService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Liguiqing
 * @since V3.0
 */
public class AssessTeamService {

    public List<AssessTeam> teamOfSchool(SchoolId schoolId, SchoolService schoolService){
        ArrayList<AssessTeam> teams = Lists.newArrayList();
        SchoolData school = schoolService.getSchool(schoolId);

        List<ClazzData> clazzData = schoolService.getClazzs(schoolId);
        Map<String, List<ClazzData>> gradeClazzs = clazzData.stream().collect(Collectors.groupingBy(ClazzData::getGradeName));

        Set<String> grades = gradeClazzs.keySet();
        if(CollectionsUtilWrapper.isNullOrEmpty(grades))
            return teams;
        for(String grade:grades){
            List<ClazzData> clazzs = gradeClazzs.get(grade);
            int year = clazzs.get(0).openYear();
            AssessTeam gradeTeam =  AssessTeam.builder()
                    .teamId(schoolId.id())
                    .teamName(school.getName()+"-"+year+"级")
                    .assessTeamId(new AssessTeamId())
                    .build();
            teams.add(gradeTeam);
            for(ClazzData clazz:clazzs){
                AssessTeam clazzTeam =  AssessTeam.builder()
                        .teamId(clazz.getClazzId())
                        .teamName(clazz.getClazzName())
                        .assessTeamId(new AssessTeamId())
                        .parentAssessTeamId(gradeTeam.getAssessTeamId())
                        .build();
                teams.add(clazzTeam);
            }
        }

        return teams;
    }

}