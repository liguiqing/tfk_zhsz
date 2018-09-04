package com.zhezhu.sm.application.school;

import com.zhezhu.share.domain.school.Grade;
import com.zhezhu.sm.application.data.GradeData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@Getter
@ToString(of ={"schoolId","name"})
public class SchoolData {

    private String schoolId;

    private String name;

    private List<GradeData> grades;

    public SchoolData(String schoolId, String name) {
        this.schoolId = schoolId;
        this.name = name;
    }

    public void addGradeDatas(Grade[] grades){
        this.grades = Arrays.stream(grades).map(g->new GradeData(g.getName(),g.getLevel())).collect(Collectors.toList());
    }

}