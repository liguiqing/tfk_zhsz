package com.zhezhu.sm.application.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class CredentialsData {

    private String name;

    private String value;

    private Date releaseDate;

    private Date expireDate;

    public CredentialsData(String name,String value){
        this.value = value;
        this.name = name;
    }

}