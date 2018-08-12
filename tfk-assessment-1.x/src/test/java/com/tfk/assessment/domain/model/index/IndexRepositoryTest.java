package com.tfk.assessment.domain.model.index;

import com.tfk.share.domain.id.index.IndexId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/
@ContextConfiguration(locations = {
        "classpath:META-INF/spring/applicationContext-assessment-app.xml",
        "classpath:applicationContext-test-cache.xml",
        "classpath:applicationContext-test-jndi.xml",
        "classpath:applicationContext-assessment-test-data.xml"}
)
@Transactional
@Rollback

public class IndexRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    IndexRepository indexRepository;

    @Test
    public void save() {
        assertNotNull(indexRepository);
        IndexId id1 = indexRepository.nextIdentity();
        IndexId id2 = indexRepository.nextIdentity();
        IndexId id3 = indexRepository.nextIdentity();
        IndexId id4 = indexRepository.nextIdentity();
        IndexId id5 = indexRepository.nextIdentity();
        Index dIndex = MoralsIndex.builder().indexId(id1)
                .name("M Index").score(3.0d).weight(0.2).customized(false).build();
        Index lIndex = LaboursIndex.builder().indexId(id2)
                .name("M Index").score(3.0d).weight(0.2).customized(false).build();
        Index mIndex = EstheticsIndex.builder().indexId(id3)
                .name("M Index").score(3.0d).weight(0.2).customized(false).build();
        Index zIndex = IntelligenceIndex.builder().indexId(id4)
                .name("M Index").score(3.0d).weight(0.2).customized(false).build();
        Index tIndex = SportsIndex.builder().indexId(id5)
                .name("M Index").score(3.0d).weight(0.2).customized(false).build();
        indexRepository.save(lIndex);
        indexRepository.save(dIndex);
        indexRepository.save(zIndex);
        indexRepository.save(tIndex);
        indexRepository.save(mIndex);

        Index dIndex_ = indexRepository.loadOf(id1);
    }
}