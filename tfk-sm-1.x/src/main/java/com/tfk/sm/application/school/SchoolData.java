package com.tfk.sm.application.school;

import com.google.common.collect.Lists;
import com.tfk.share.domain.school.Grade;
import com.tfk.sm.application.data.GradeData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Liguiqing
 * @since V3.0
 */

@Getter
@ToString(of ={"schoolId","name"})
public class SchoolData {

    private String schoolId;

    private String name;

    private List<GradeData> grads;

    public SchoolData() {
    }

    public SchoolData(String schoolId, String name) {
        this.schoolId = schoolId;
        this.name = name;
    }

    public void addGradeDatas(Grade[] grades){
        grads = Arrays.stream(grades).map(g->new GradeData(g.name(),g.level())).collect(Collectors.toList());
    }

}