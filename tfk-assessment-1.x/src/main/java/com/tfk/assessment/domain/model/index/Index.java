package com.tfk.assessment.domain.model.index;

import com.google.common.collect.Sets;
import com.tfk.commons.AssertionConcerns;
import com.tfk.commons.domain.IdentifiedValueObject;
import com.tfk.share.domain.id.index.IndexId;
import lombok.*;

import java.util.Set;

/**
 * 评价指标
 *
 * @author Liguiqing
 * @since V3.0
 */
@ToString(of = {"indexId","name","score","weight","description"})
@EqualsAndHashCode(of = {"indexId"},callSuper = false)
@Getter
public abstract class Index extends IdentifiedValueObject {

    private IndexId indexId;

    private String name;

    private double score;

    private double weight;

    private String description;

    private boolean customized; //是否定制指标

    private Index parent; //上级指标

    private Index mapped; //映射到标准指标

    private Set<Index> children;

    public Index(IndexId indexId, String name, double score, double weight, boolean customized) {
        this.indexId = indexId;
        this.name = name;
        this.score = score;
        this.weight = weight;
        this.customized = customized;
    }

    public Index(IndexId indexId, String name, double score, double weight, boolean customized,String description) {
        this.indexId = indexId;
        this.name = name;
        this.score = score;
        this.weight = weight;
        this.customized = customized;
        this.description = description;
    }

    public void description(String description) {
        this.description = description;
    }

    public void customized(boolean customized) {
        this.customized = customized;
    }

    public void parent(Index parent) {
        this.parent = parent;
    }

    public void mappedTo(Index stIndex){
        this.mapped = stIndex;
    }

    public void addChild(Index child){
        if(child != null && child.typeOf(this))

        if(this.children == null)
            this.children = Sets.newHashSet();

        this.children.add(child);
    }

    public boolean isTop(){
        return this.parent == null;
    }

    public boolean typeOf(Index other){
        return other.getClass().isAssignableFrom(getClass());
    }

    public boolean hasChildren(){
        return this.size() > 0;
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
        if(parent != null)
            AssertionConcerns.assertArgumentRange(score,0,parent.getScore(),"as-01-001");
        else
            AssertionConcerns.assertArgumentRange(score,0,this.getScore(),"as-01-001");

        return this.weight * score;
    }

    public abstract  String getCategory();

    public Index(){}

}