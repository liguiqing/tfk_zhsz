package com.tfk.assessment.domain.model.medal;

import com.tfk.assessment.domain.model.index.Index;
import com.tfk.commons.AssertionConcerns;
import com.tfk.commons.domain.IdentifiedValueObject;
import com.tfk.share.domain.id.index.IndexId;
import com.tfk.share.domain.id.medal.MedalId;
import com.tfk.share.domain.id.school.SchoolId;
import lombok.*;

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

    private MedalLevel level;

    @Setter
    private int upLeast; //晋级数量

    private String indexIds="";//对应指标ID,多指标时以;隔离

    private Medal high;

    @Builder
    private Medal(MedalId medalId, SchoolId schoolId, String name,int upLeast,MedalLevel level,Medal high) {
        this.medalId = medalId;
        this.schoolId = schoolId;
        this.name = name;
        this.level = level;
        this.upLeast = upLeast;
        setHigh(high);
    }

    public Medal() {}

    public void setHigh(Medal high){
        if(high != null){
            AssertionConcerns.assertArgumentTrue(high.getLevel().gt(level),"as-03-002");
            this.high = high;
        }
    }

    public void addIndex(Index index){
        String indexId = index.getIndexId().id();
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