package com.tfk.assessment.domain.model.medal;

import com.tfk.commons.domain.IdentifiedValueObject;
import com.tfk.share.domain.id.index.IndexId;
import com.tfk.share.domain.id.medal.MedalId;
import com.tfk.share.domain.id.school.SchoolId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

/**
 * 勋章
 *
 * @author Liguiqing
 * @since V3.0
 */
@Getter
@EqualsAndHashCode(of={"medalId","schoolId"},callSuper = false)
@ToString(of={"medalId","schoolId","name","category","upLeast","high","low"})
public class Medal extends IdentifiedValueObject {
    private MedalId medalId;

    private SchoolId schoolId;

    private String name;

    private String category;

    private int upLeast; //晋级数量

    private Medal high; //上一级勋章

    private Medal low;  //下一级勋章

    private String indexIds="";//对应指标ID,多指标时以;隔离

    @Builder
    private Medal(MedalId medalId, SchoolId schoolId, String name, String category,
                 int upLeast, Medal high, Medal low) {
        this.medalId = medalId;
        this.schoolId = schoolId;
        this.name = name;
        this.category = category;
        this.upLeast = upLeast;
        this.high = high;
        this.low = low;
    }

    public Medal() {}

    public void addIndex(String indexId){
        if(this.indexIds.length() >0){
            this.indexIds += ";"+indexId;
        }else{
            this.indexIds = indexId;
        }
    }

    public boolean canUp(){
        return this.high != null;
    }

    public Medal promotion(int amount){
        if(amount >= this.upLeast){
            return this.high;
        }
        return null;
    }

}