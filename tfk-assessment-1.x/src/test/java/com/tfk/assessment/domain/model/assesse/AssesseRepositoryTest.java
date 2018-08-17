package com.tfk.assessment.domain.model.assesse;

import com.tfk.commons.util.DateUtilWrapper;
import com.tfk.share.domain.id.assessment.AssessId;
import com.tfk.share.domain.id.assessment.AssesseeId;
import com.tfk.share.domain.id.assessment.AssessorId;
import com.tfk.share.domain.id.index.IndexId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Copyright (c) 2016,2018, 深圳市易考试乐学测评有限公司
 **/
@ContextHierarchy({
    @ContextConfiguration(locations = {
        "classpath:META-INF/spring/applicationContext-assessment-app.xml",
        "classpath:applicationContext-test-cache.xml",
        "classpath:applicationContext-test-jndi.xml",
        "classpath:applicationContext-assessment-test-data.xml"}
)})
@Transactional
@Rollback
public class AssesseRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    AssessRepository assessRepository;

    @Test
    public void test() {
        assertNotNull(assessRepository);
        IndexId indexId = new IndexId();
        AssessId assessId = new AssessId();
        AssesseeId assesseeId = new AssesseeId();
        AssessorId assessorId = new AssessorId();
        Assess assess = Assess.builder()
                .indexId(indexId)
                .assessId(assessId)
                .assessorId(assessorId)
                .assesseeId(assesseeId)
                .category("D")
                .score(10d)
                .word("YAMADIE")
                .doneDate(DateUtilWrapper.now())
                .build();
        assessRepository.save(assess);

        Assess assess_ = assessRepository.loadOf(assessId);
        assertEquals(assess,assess_);

        Assess assess2 = Assess.builder()
                .indexId(indexId)
                .assessId(assessId)
                .assessorId(assessorId)
                .assesseeId(assesseeId)
                .category("D")
                .score(10d)
                .word("YAMADIE")
                .doneDate(DateUtilWrapper.now())
                .build();
        assertNotEquals(assess2,assess);
        assertNotEquals(assess2,assess_);

        assessRepository.delete(assessId);
        assess_ = assessRepository.loadOf(assessId);
        assertNull(assess_);

        testBatch(10,10000);
    }

    private void testBatch(int saves, int querys) {
        AssessId[] ids = new  AssessId[saves];

        IndexId indexId = new IndexId();
        AssesseeId assesseeId = new AssesseeId();
        AssessorId assessorId = new AssessorId();

        for(int i=0;i<saves;i++){
            ids[i] = assessRepository.nextIdentity();
            Assess assess = Assess.builder()
                    .indexId(indexId)
                    .assessId(ids[i] )
                    .assessorId(assessorId)
                    .assesseeId(assesseeId)
                    .category("D")
                    .score(10d)
                    .doneDate(DateUtilWrapper.now())
                    .build();
            assessRepository.save(assess);
        }

        for(int i=0;i<querys;i++){
            for(int j=0;j<saves;j++){
                AssessId id = ids[j];
                Assess assess = assessRepository.loadOf(id);
                assertNotNull(assess);
            }
        }

        for(int j=0;j<saves;j++){
            AssessId id = ids[j];
            assessRepository.delete(id);
            Assess assess = assessRepository.loadOf(id);
            assertNull(assess);
        }
    }

}