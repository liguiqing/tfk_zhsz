package com.zhezhu.sm.domain.model.clazz;

import com.zhezhu.commons.config.CommonsConfiguration;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.sm.SmTestConfiguration;
import com.zhezhu.sm.config.SmApplicationConfiguration;
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
@ContextConfiguration(
        classes = {
                SmTestConfiguration.class,
                CommonsConfiguration.class,
                SmApplicationConfiguration.class
        }
)
@Transactional
@Rollback
public class UnitedClazzRepositoryTest  extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    UnitedClazzRepository unitedClazzRepository;

    @Test
    public void supports() {
        assertTrue(unitedClazzRepository.supports(new UnitedClazz(new ClazzId(),new SchoolId()).getClass()));
    }
}