package com.zhezhu.assessment.application.medal;

import com.zhezhu.assessment.domain.model.index.Index;
import com.zhezhu.assessment.domain.model.index.IndexRepository;
import com.zhezhu.assessment.domain.model.medal.Medal;
import com.zhezhu.assessment.domain.model.medal.MedalLevel;
import com.zhezhu.assessment.domain.model.medal.MedalRepository;
import com.zhezhu.assessment.infrastructure.school.SchoolService;
import com.zhezhu.commons.domain.Identities;
import com.zhezhu.share.domain.id.index.IndexId;
import com.zhezhu.share.domain.id.medal.MedalId;
import com.zhezhu.share.domain.id.school.SchoolId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Liguiqing
 * @since V3.0
 */

@Service
@Slf4j
public class MedalApplicationService {

    @Autowired
    private MedalRepository medalRepository;

    @Autowired
    private IndexRepository indexRepository;

    @Autowired
    private SchoolService schoolService;

    @Transactional(rollbackFor = Exception.class)
    public String newMedal(NewMedalCommand command){
        log.debug("New Medal {} to School {}",command.getName(),command.getSchoolId());

        SchoolId schoolId = new SchoolId(command.getSchoolId());
        if(!schoolService.hasSchool(schoolId))
            return null;

        Medal high = null;
        if(Identities.isQualified(command.getHighMedalId())){
            high = medalRepository.loadOf(new MedalId(command.getHighMedalId()));
        }
        MedalId medalId = medalRepository.nextIdentity();
        Medal medal = Medal.builder()
                .medalId(medalId)
                .schoolId(schoolId)
                .name(command.getName())
                .level(new MedalLevel(command.getLevel(),command.getCategory()))
                .upLeast(command.getUpLeast())
                .high(high)
                .build();
        updateIndexes(medal,command.getIndexIds());
        medalRepository.save(medal);
        return medalId.id();
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateMedal(UpdateMedalCommand command){
        log.debug("Update Medal {}",command.toString());

        Medal medal = medalRepository.loadOf(new MedalId(command.getMedalId()));
        medal.setName(command.getName());
        medal.setUpLeast(command.getUpLeast());
        updateIndexes(medal,command.getIndexIds());
        medalRepository.save(medal);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteMedal(String medalId){
        log.debug("Delete Medal {}",medalId);

        medalRepository.delete(new MedalId(medalId));
    }

    private void updateIndexes(Medal medal,String[] indexIds){
        medal.clearIndexes();
        if(indexIds != null && indexIds.length > 0){
            for(String indexId:indexIds){
                Index index = indexRepository.loadOf(new IndexId(indexId));
                medal.addIndex(index);
            }
        }
    }
}