package com.zhezhu.share.infrastructure.school;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhezhu.commons.util.DateUtilWrapper;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ClazzData {
    private String schoolId;

    private String clazzId;

    private String clazzName;

    private String gradeName;

    private int gradeLevel;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date openedTime;

    private String type;

    public int openYear(){
        return DateUtilWrapper.year(this.openedTime);
    }

}