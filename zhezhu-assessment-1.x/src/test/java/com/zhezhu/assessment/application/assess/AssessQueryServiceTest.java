package com.zhezhu.assessment.application.assess;

import com.zhezhu.assessment.domain.model.assesse.*;
import com.zhezhu.assessment.domain.model.index.IndexRepository;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.school.SchoolId;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.spy;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/
public class AssessQueryServiceTest {

    @Mock
    private AssessService assesseService;

    @Mock
    private IndexRepository indexRepository;

    @Mock
    private AssessRepository assessRepository;

    @Mock
    private AssessRankRepository rankRepository;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }


    private AssessQueryService getService()throws Exception{
        AssessQueryService service = new AssessQueryService();
        FieldUtils.writeField(service,"assesseService",assesseService,true);
        FieldUtils.writeField(service,"indexRepository",indexRepository,true);
        FieldUtils.writeField(service,"assessRepository",assessRepository,true);
        FieldUtils.writeField(service,"rankRepository",rankRepository,true);
        return spy(service);
    }

    @Test
    public void getRanks() throws Exception{
        AssessQueryService service = getService();
        SchoolId schoolId = new SchoolId();
        PersonId personId = new PersonId();
        Date from  = DateUtilWrapper.toDate("2018-09-01","yyyy-MM-dd");
        Date to  = DateUtilWrapper.toDate("2018-09-30","yyyy-MM-dd");
        List<AssessRank> ranks = service.getRanks(schoolId.id(), personId.id(), RankCategory.Weekend,
                RankScope.Clazz, from, to);
    }

    @Test
    public void getAssessOf() {

    }


}