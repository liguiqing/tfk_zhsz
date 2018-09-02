package com.zhezhu.assessment.domain.model.medal;

import com.zhezhu.assessment.domain.model.index.Index;
import com.zhezhu.commons.AssertionConcerns;
import com.zhezhu.commons.domain.IdentifiedValueObject;
import com.zhezhu.share.domain.id.medal.MedalId;
import com.zhezhu.share.domain.id.school.SchoolId;
import lombok.*;

/**
 * 勋章
 *
 * @author Liguiqing
 * @since V3.0
 */
@Getter
@EqualsAndHashCode(of={"medalId","schoolId"},callSuper = false)
@ToString(of={"medalId","schoolId","name","level","upLeast","high"})
public class Medal extends IdentifiedValueObject {
    private MedalId medalId;

    private SchoolId schoolId;

    @Setter
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
            AssertionConcerns.assertArgumentTrue(high.gt(this),"as-03-002");
            this.high = high;
        }
    }

    public void addIndex(Index index){
        if(index == null)
            return;

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

    /**
     * 离升级的差
     * @param amount
     * @return
     */
    public int differenceToHigh(int amount){
        if(amount<0)
            return this.upLeast;
        if(amount > this.upLeast)
            return 0;
        return this.upLeast - amount;
    }

    public void clearIndexes() {
        this.indexIds = "";
    }

    public boolean gt(Medal other){
        return this.level.gt(other.level);
    }
}