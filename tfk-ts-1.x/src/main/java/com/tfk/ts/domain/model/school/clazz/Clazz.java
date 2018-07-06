/*
 * Copyright (c) 2016,2017, tfk All Rights Reserved. 深圳市天定康科技有限公司 版权所有.
 */

package com.tfk.ts.domain.model.school.clazz;

import com.tfk.commons.AssertionConcerns;
import com.tfk.commons.domain.Entity;
import com.tfk.commons.util.DateUtilWrapper;
import com.tfk.ts.domain.model.school.*;
import com.tfk.ts.domain.model.school.common.Period;
import com.tfk.ts.domain.model.school.common.WLType;
import com.tfk.ts.domain.model.school.term.Term;
import com.tfk.ts.domain.model.school.term.TermOrder;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import java.util.Date;
import java.util.Set;

/**
 * 班级
 *
 * @author Liguiqing
 * @since V3.0
 */

public abstract class Clazz extends Entity {
    private SchoolId schoolId;

    private ClazzId clazzId;

    private String name;

    private String clazzNo;//班号，学校自编，是班级连续的标识

    private String createDate; //建班日期，使用时格式为YYYY-mm

    private boolean finish = Boolean.FALSE;

    private Set<ClazzCatagory> catagories;

    private Set<ClazzHistory> histories;

    public Clazz(SchoolId schoolId, ClazzId clazzId, String name, String clazzNo, Date createDate,
                 Grade grade,Term term) {
        this(schoolId,clazzId,name,clazzNo,createDate,grade,WLType.None,term);
    }


    public Clazz(SchoolId schoolId, ClazzId clazzId, String name, String clazzNo, Date createDate,
                 Grade grade, WLType wl, Term term) {
        this(schoolId,clazzId,name,clazzNo,DateUtilWrapper.toString(createDate,"yyyyy-MM"),
                grade,wl,term);
    }

    public Clazz(SchoolId schoolId, ClazzId clazzId, String name, String clazzNo, String createDate,
                 Grade grade,  WLType wl, Term term) {
        AssertionConcerns.assertArgumentNotNull(schoolId,"请提供学校唯一标识");
        AssertionConcerns.assertArgumentNotNull(clazzId,"请提供班级唯一标识");
        AssertionConcerns.assertArgumentNotNull(name,"请提供班级名称");
        AssertionConcerns.assertArgumentNotNull(createDate,"请提供班级创建日期");
        AssertionConcerns.assertArgumentNotNull(grade,"请提供班级所属年级");

        this.schoolId = schoolId;
        this.clazzId = clazzId;
        this.name = name;
        this.clazzNo = clazzNo;
        this.createDate = createDate;
        this.histories = Sets.newTreeSet();

        if(term != null){
            this.addHistory(term,grade,wl);
        }
    }

    public void graduate(){
        this.finish = Boolean.TRUE;
    }

    /**
     * 增加班级的学期史
     *
     * @param term
     * @param grade
     * @param wl　Null时为 WLType.None
     */
    public void addHistory(Term term,Grade grade,WLType wl){
        if(this.finish)
            return;

        if(wl == null)
            wl = WLType.None;

        ClazzHistory aHistory = new ClazzHistory(this.clazzId,term,grade,wl);
        this.histories.add(aHistory);
    }

    /**
     * 取得班级某学期的年级
     *
     * @param aTerm
     * @return
     */
    public Grade termGrade(Term aTerm){
        for(ClazzHistory history:this.histories){
            if(history.termId().equals(aTerm.termId())){
                return history.grade();
            }
        }
        throw new ClazzNotInTermException(aTerm.toString());
    }

    /**
     * 取得班级某学期的文理分类
     *
     * @param term
     * @return
     */
    public WLType termWL(Term term){
        for(ClazzHistory history:this.histories){
            if(history.termId().equals(term.termId())){
                return history.wl();
            }
        }
        return WLType.None;
    }

    /**
     * 取得班级某个时间范围的年级
     *
     * @param period
     * @return 如果找不到对应的年级，则返回为Null，客户端请自行处理 null值
     */
    public Grade periodGrade(Period period){
        for(ClazzHistory history:this.histories){
            if(history.isInPeriod(period)){
                return history.grade();
            }
        }
        return null;
    }

    /**
     * 班级升一个年级
     * 能升班的规则是，最近的班级史必须是下学期;新学期学年必须是最近班级史的下一学年
     *
     * @param term
     */
    public void upGrade(Term term){
        if(this.histories == null || this.histories.size() == 0)
            return;
        
        if(this.finish)
            return;

        ClazzHistory top = this.histories.iterator().next();
        AssertionConcerns.assertArgumentTrue(top.termOrder() == TermOrder.Second,"上学期不能升班");
        StudyYear topYearNext = term.termYear().nextYear();
        AssertionConcerns.assertArgumentTrue(topYearNext.equals(term.termYear()),"升班后的学期必须是新的学期");

        GradeNameGenerateStrategy nameGenerateStrategy = GradeNameGenerateStrategyFactory.lookup(this.schoolId);
        Grade nextGrade = top.grade().next(nameGenerateStrategy);
        if(nextGrade.equals(top.grade())){
            this.graduate();
        }else{
            this.addHistory(term,nextGrade,top.wl());
        }

    }

    /**
     * 增加班分类
     *
     * @param catagoryName
     * @param catagoryCode
     */
    public void addCatagory(String catagoryName,String catagoryCode){
        if(this.catagories == null)
            this.catagories = Sets.newHashSet();
        this.catagories.add(new ClazzCatagory(this.clazzId,catagoryName,catagoryCode));
    }


    public abstract boolean canBeStudyAndTeachIn();

    public abstract boolean canBeManaged();

    public abstract boolean canBeStudied();

    protected boolean classOf(String clazzName) {
        return this.getClass().getSimpleName().equals(clazzName);
    }

    public SchoolId schoolId() {
        return schoolId;
    }

    public ClazzId clazzId() {
        return clazzId;
    }

    public String name() {
        return name;
    }

    public String clazzNo() {
        return clazzNo;
    }

    public String createDate() {
        return createDate;
    }

    public boolean finish(){
        return this.finish;
    }

    public Set<ClazzCatagory> catagories() {
        return ImmutableSet.copyOf(catagories);
    }

    public Set<ClazzHistory> histories() {
        return ImmutableSet.copyOf(histories);
    }

    protected Clazz(){

    }

}