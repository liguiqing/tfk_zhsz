package com.zhezhu.assessment.domain.model.index;

import com.google.common.collect.Sets;
import com.zhezhu.commons.AssertionConcerns;
import com.zhezhu.commons.domain.Entity;
import com.zhezhu.share.domain.id.identityaccess.TenantId;
import com.zhezhu.share.domain.id.index.IndexId;
import lombok.*;

import java.util.Set;

/**
 * 评价指标
 *
 * @author Liguiqing
 * @since V3.0
 */
@ToString(of = {"indexId","name","score","description"})
@EqualsAndHashCode(of = {"indexId"},callSuper = false)
@Getter
public class Index extends Entity {

    private IndexId indexId;

    private Index parent; //上级指标

    @Setter
    private IndexCategory category;

    private TenantId owner;

    @Setter
    private String name;

    @Setter
    private IndexScore score;

    @Setter
    private String description;

    @Setter
    private String group;

    private Set<Index> children;

    private Set<IndexMapping> mappings; //映射到标准指标

    @Builder
    private Index(IndexId indexId, Index parent, IndexCategory category,
                 TenantId owner, String name, double score, double weight, String description,String group) {
        this.indexId = indexId;
        this.category = category;
        this.owner = owner;
        this.name = name;
        this.score = new IndexScore(score,weight);
        this.description = description;
        this.group = group;
        if(parent != null){
            parent.addChild(this);
        }
    }

    public boolean isCustomized(){
        return this.owner != null;
    }

    public Index mappedTo(Index mapped,double score,double weight){
        if(mapped.isCustomized())
            return this;

        if(this.mappings == null)
            this.mappings = Sets.newHashSet();

        this.mappings.add(new IndexMapping(this,mapped,new IndexScore(score,weight)));
        return this;
    }

    public Index addChild(Index child){
        if(child == null || !child.typeOf(this))
            return this;

        if(this.children == null)
            this.children = Sets.newHashSet();
        child.parent = this;
        this.children.add(child);
        return this;
    }

    public Index removeChild(Index child){
        if(this.children == null)
            return this;
        this.children.remove(child);
        return this;
    }

    public boolean isTop(){
        return this.parent == null;
    }

    public boolean typeOf(Index other){
        return this.category.equals(other.category);
    }

    public boolean hasChildren(){
        return this.size() > 0;
    }

    public int mappedSize(){
        if(this.mappings == null)
            return 0;
        return this.mappings.size();
    }

    public int size(){
        if(this.children == null)
            return 0;
        return this.children.size();
    }

    public int allSize(){
        int size = this.size();
        if(this.hasChildren()){
            Set<Index> children = this.children;
            for(Index child:children){
                size += child.allSize();
            }
        }
        return size;
    }

    public double calRealScore(double score) {
        double convertScore = this.score.convert(score);
        if(parent != null)
            convertScore = parent.score.convert(score);

        AssertionConcerns.assertArgumentRange(convertScore,0,this.getMaxScore(),"as-01-001");

        return convertScore;
    }

    public double getMaxScore(){
        if(this.parent != null)
            return parent.getMaxScore();
        return this.getScore().getScore();
    }

    public String getCategory(){
        return this.category.name();
    }

    protected Index(){}

}