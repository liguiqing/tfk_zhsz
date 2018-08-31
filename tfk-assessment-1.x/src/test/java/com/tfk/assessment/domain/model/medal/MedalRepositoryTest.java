package com.tfk.assessment.domain.model.medal;

import com.tfk.assessment.AssessmentTestConfiguration;
import com.tfk.assessment.config.AssessmentApplicationConfiguration;
import com.tfk.commons.config.CommonsConfiguration;
import com.tfk.share.domain.id.medal.MedalId;
import com.tfk.share.domain.id.school.SchoolId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Copyright (c) 2016,$today.year, Liguiqing
 **/

@ContextConfiguration(
        classes = {
                AssessmentTestConfiguration.class,
                CommonsConfiguration.class,
                AssessmentApplicationConfiguration.class
        }
)
@Transactional
@Rollback
public class MedalRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private MedalRepository medalRepository;

    @Test
    public  void test(){
        assertNotNull(medalRepository);
        MedalLevel l1 = new MedalLevel(1, "B");
        MedalLevel l11 = new MedalLevel(1, "R");
        MedalLevel l2 = new MedalLevel(2, "G");
        MedalLevel l3 = new MedalLevel(3, "G");
        MedalLevel l4 = new MedalLevel(4, "G");

        MedalId medalId1 = medalRepository.nextIdentity();
        MedalId medalId2 = medalRepository.nextIdentity();
        MedalId medalId3 = medalRepository.nextIdentity();
        MedalId medalId4 = medalRepository.nextIdentity();
        MedalId medalId5 = medalRepository.nextIdentity();

        SchoolId schoolId = new SchoolId();
        Medal medal = Medal.builder().medalId(medalId1).schoolId(schoolId).name("阳光少年").upLeast(8).level(l1).build();
        Medal medal11 = Medal.builder().medalId(medalId2).schoolId(schoolId).name("阳光少年").upLeast(12).level(l11).build();
        Medal medal2 = Medal.builder().medalId(medalId3).schoolId(schoolId).name("阳光少年").upLeast(5).level(l2).build();
        Medal medal3 = Medal.builder().medalId(medalId4).schoolId(schoolId).name("阳光少年").upLeast(5).level(l3).build();
        Medal medal4 = Medal.builder().medalId(medalId5).schoolId(schoolId).name("阳光少年").upLeast(5).level(l4).build();

        medalRepository.save(medal2);
        medalRepository.save(medal3);
        medalRepository.save(medal4);

        medal2 = medalRepository.loadOf(medal2.getMedalId());
        medal3 = medalRepository.loadOf(medal3.getMedalId());
        medal4 = medalRepository.loadOf(medal4.getMedalId());

        medal.setHigh(medal2);
        medal11.setHigh(medal2);
        medal2.setHigh(medal3);
        medal3.setHigh(medal4);

        medalRepository.save(medal);
        medal = medalRepository.loadOf(medal.getMedalId());
        assertEquals("阳光少年",medal.getName());
        medal.setName("阳光少年2");
        medalRepository.save(medal);
        assertEquals("阳光少年2",medal.getName());

        Medal medal_ = medalRepository.loadOf(medalId1);
        assertNotNull(medal_);
        assertEquals(medal,medal_);
        assertEquals(medal2,medal_.getHigh());
        assertEquals(medal3,medal_.getHigh().getHigh());
        assertEquals(medal4,medal_.getHigh().getHigh().getHigh());
        assertEquals(medalId1,medal_.getMedalId());
        assertEquals(medalId3,medal_.getHigh().getMedalId());
        assertEquals(medalId4,medal_.getHigh().getHigh().getMedalId());
        assertEquals(medalId5,medal_.getHigh().getHigh().getHigh().getMedalId());

        for(int i = 0;i<100000;i++){
            medalRepository.loadOf(medalId1);
        }
    }

    @Test
    public void find(){
        SchoolId schoolId1 = new SchoolId();
        SchoolId schoolId2 = new SchoolId();
        List<Medal> medals = createMedalsForSchool(schoolId1, 1,new MedalLevel(2,"G"),null);
        Medal hight = medalRepository.loadOf(medals.get(0).getMedalId());
        createMedalsForSchool(schoolId1, 5,null,hight);
        createMedalsForSchool(schoolId2,8,null,null);

        List<Medal> medalList = medalRepository.findMedalsBySchoolId(new SchoolId());
        assertEquals(0,medalList.size());
        List<Medal> medalList1 = medalRepository.findMedalsBySchoolId(schoolId1);
        List<Medal> medalList2 = medalRepository.findMedalsBySchoolId(schoolId2);
        assertEquals(6,medalList1.size());
        assertEquals(8,medalList2.size());

        List<Medal> medalList3 = medalRepository.findMedalsBySchoolIdAndHigh(schoolId1,hight);
        assertEquals(5,medalList3.size());
    }

    List<Medal> createMedalsForSchool(SchoolId schoolId,int size,MedalLevel level,Medal hight){
        ArrayList<Medal> medals = new ArrayList(size);
        if(level == null)
            level = new MedalLevel(1,"B");

        for(int i =0;i<size;i++){
            MedalId medalId = medalRepository.nextIdentity();
            Medal medal = Medal.builder().medalId(medalId)
                    .schoolId(schoolId).name("阳光少年").upLeast(8).level(level).high(hight).build();
            medalRepository.save(medal);
            medals.add(medal);
        }
        return medals;
    }
}