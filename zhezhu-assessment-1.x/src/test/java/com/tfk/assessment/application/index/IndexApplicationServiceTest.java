package com.tfk.assessment.application.index;

import com.tfk.assessment.domain.model.index.Index;
import com.tfk.assessment.domain.model.index.IndexRepository;
import com.tfk.share.domain.id.identityaccess.TenantId;
import com.tfk.share.domain.id.index.IndexId;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

/**
 * Copyright (c) 2016,$today.year, Liguiqing
 **/
public class IndexApplicationServiceTest {

    @Mock
    private IndexRepository indexRepository;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void newStIndex() throws Exception{
        IndexApplicationService indexApplicationService = getIndexApplicationService();
        NewIndexCommand command = NewIndexCommand.builder()
                .categoryName("Morals")
                .description("Desc")
                .name("name")
                .score(10.0d)
                .weight(0.5d)
                .build();
        IndexId stIndexId1 = new IndexId();
        when(indexRepository.nextIdentity()).thenReturn(stIndexId1);
        doNothing().when(indexRepository).save(any(Index.class));
        indexApplicationService.newStIndex(command);

        verify(indexRepository,times(1)).nextIdentity();
        verify(indexRepository,times(1)).save(any(Index.class));

    }

    @Test
    public void newTenantIndex()throws Exception {

        IndexApplicationService indexApplicationService = getIndexApplicationService();
        IndexId pndexId1 = new IndexId();
        NewIndexCommand command = NewIndexCommand.builder()
                .categoryName("Morals")
                .description("Desc")
                .name("name")
                .score(10.0d)
                .weight(0.5d)
                .parentIndexId(pndexId1.id())
                .ownerId(new TenantId().id())
                .build();
        Index pIndex = mock(Index.class);
        when(indexRepository.loadOf(any(IndexId.class))).thenReturn(pIndex);
        when(pIndex.addChild(any(Index.class))).thenReturn(pIndex);

        IndexId stIndexId1 = new IndexId();
        when(indexRepository.nextIdentity()).thenReturn(stIndexId1);
        doNothing().when(indexRepository).save(any(Index.class));
        indexApplicationService.newTenantIndex(command);

        verify(indexRepository,times(1)).nextIdentity();
        verify(indexRepository,times(1)).save(any(Index.class));
        verify(pIndex,times(1)).addChild(any(Index.class));
    }

    @Test
    public void updateIndex()throws Exception {
        IndexApplicationService indexApplicationService = getIndexApplicationService();
        NewIndexCommand command = NewIndexCommand.builder()
                .categoryName("Morals1")
                .description("Desc1")
                .name("name1")
                .score(11.0d)
                .weight(0.6d)
                .build();
        Index index = mock(Index.class);
        IndexId stIndexId1 = new IndexId();
        when(indexRepository.loadOf(any(IndexId.class))).thenReturn(index);
        indexApplicationService.updateIndex(new UpdateIndexCommand(stIndexId1.id()).build(command));

        verify(indexRepository,times(1)).loadOf(any(IndexId.class));
    }

    private IndexApplicationService getIndexApplicationService()throws Exception{
        IndexApplicationService indexApplicationService = new IndexApplicationService();
        FieldUtils.writeField(indexApplicationService,"indexRepository",indexRepository,true);
        return spy(indexApplicationService);
    }
}