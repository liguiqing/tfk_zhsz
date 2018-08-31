package com.tfk.assessment.application.medal;

import com.tfk.assessment.domain.model.index.IndexRepository;
import com.tfk.assessment.domain.model.medal.Medal;
import com.tfk.assessment.domain.model.medal.MedalRepository;
import com.tfk.assessment.infrastructure.school.SchoolService;
import com.tfk.share.domain.id.index.IndexId;
import com.tfk.share.domain.id.medal.MedalId;
import com.tfk.share.domain.id.school.SchoolId;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Copyright (c) 2016,$today.year, Liguiqing
 **/
public class MedalApplicationServiceTest {

    @Mock
    private MedalRepository medalRepository;

    @Mock
    private IndexRepository indexRepository;

    @Mock
    private SchoolService schoolService;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void newMedal() throws Exception{
        MedalApplicationService service = getService();
        assertNotNull(service);
        SchoolId schoolId = new SchoolId();
        MedalId medalId = new MedalId();
        NewMedalCommand command = NewMedalCommand.builder()
                .name("阳光少年")
                .schoolId(schoolId.id())
                .category("B")
                .indexIds(new String[]{new IndexId().id(),new IndexId().id(),new IndexId().id()})
                .level(1).build();

        Medal high = mock(Medal.class);
        when(high.gt(any(Medal.class))).thenReturn(true);
        when(schoolService.hasSchool(any(SchoolId.class))).thenReturn(true).thenReturn(true).thenReturn(false);
        when(medalRepository.nextIdentity()).thenReturn(medalId);
        when(medalRepository.loadOf(any(MedalId.class))).thenReturn(high).thenReturn(null);
        when(indexRepository.loadOf(any(IndexId.class))).thenReturn(null).thenReturn(null).thenReturn(null);
        doNothing().when(medalRepository).save(any(Medal.class));

        service.newMedal(command);
        command.setHighMedalId(new MedalId().id());
        service.newMedal(command);
        service.newMedal(command);

        verify(schoolService,times(3)).hasSchool(any(SchoolId.class));
        verify(medalRepository,times(1)).loadOf(any(MedalId.class));
        verify(medalRepository,times(2)).save(any(Medal.class));
    }

    @Test
    public void updateMedal() throws  Exception{
        MedalApplicationService service = getService();
        assertNotNull(service);
        MedalId medalId = new MedalId();
        UpdateMedalCommand command = UpdateMedalCommand.builder()
                .medalId(medalId.id())
                .name("月光少年")
                .indexIds(new String[]{new IndexId().id(),new IndexId().id(),new IndexId().id()})
                .build();
        Medal medal = mock(Medal.class);

        when(medalRepository.loadOf(any(MedalId.class))).thenReturn(medal).thenReturn(null);
        service.updateMedal(command);

        verify(medalRepository,times(1)).loadOf(any(MedalId.class));
        verify(medalRepository,times(1)).save(any(Medal.class));
        verify(indexRepository, times(3)).loadOf(any(IndexId.class));
    }

    @Test
    public void deleteMedal() throws Exception{
        MedalApplicationService service = getService();
        assertNotNull(service);
        MedalId medalId = new MedalId();
        doNothing().when(medalRepository).delete(any(MedalId.class));

        service.deleteMedal(medalId.id());
        verify(medalRepository,times(1)).delete(any(MedalId.class));
    }

    private MedalApplicationService getService()throws Exception{
        MedalApplicationService service = new MedalApplicationService();
        FieldUtils.writeField(service,"medalRepository",medalRepository,true);
        FieldUtils.writeField(service,"indexRepository",indexRepository,true);
        FieldUtils.writeField(service,"schoolService",schoolService,true);
        return spy(service);
    }
}