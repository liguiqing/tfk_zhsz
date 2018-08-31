package com.zhezhu.sm.application.data;

import com.zhezhu.commons.util.StringUtilWrapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */

@NoArgsConstructor
@Getter
@ToString
public class StudentNameSortedData {

    List<StudentNameGroupData> groups;

    public int size(){
        if(this.groups == null)
            return 0;
        return this.groups.size();
    }

    public void add(StudentData data){
        String nameSpelling = StringUtilWrapper.chineseTranslateToSpelling(data.getName(), true);
        String firstLetter = StringUtils.substring(nameSpelling,0,1);

        if(this.groups == null) {
            this.groups = new ArrayList<>();
        }

        boolean added = false;
        for(StudentNameGroupData groupData: groups){
            added = groupData.add(firstLetter, data);
            if(added)
                break;
        }
        if(!added){
            groups.add(new StudentNameGroupData(firstLetter,data));
        }
    }
}