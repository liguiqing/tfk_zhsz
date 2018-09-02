package com.zhezhu.sm.application.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 学生按姓名分组数据
 *
 * @author Liguiqing
 * @since V3.0
 */

@NoArgsConstructor
@Getter
@ToString(of={"letter"})
@EqualsAndHashCode(of={"letter"})
public class StudentNameGroupData {

    private String letter;

    private List<StudentData> students;

    public StudentNameGroupData(String letter,StudentData data){
        this.letter = letter;
        this.students = new ArrayList<>();
        this.students.add(data);
    }

    public boolean add(String letter,StudentData data){
        if(letter !=null && letter.equalsIgnoreCase(this.letter)){
            this.students.add(data);
            return true;
        }
        return false;
    }

}