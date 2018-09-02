package com.zhezhu.sm.application.clazz;

import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.sm.domain.model.clazz.Clazz;
import com.zhezhu.sm.domain.model.clazz.ClazzRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */

@Service
public class ClazzApplicationService {
    private static Logger logger = LoggerFactory.getLogger(ClazzApplicationService.class);

    @Autowired
    private List<ClazzRepository> clazzRepositories;

    public ClazzApplicationService(List<ClazzRepository> clazzRepositories) {
        this.clazzRepositories = clazzRepositories;
    }

    @Transactional(rollbackFor = Exception.class)
    public void newClazz(NewClazzCommand command){
        logger.debug("New Clazz {}",command);
        Clazz clazz = command.toClazz();
        ClazzRepository clazzRepository = getClazzRepositoryOf(clazz);
        clazzRepository.save(clazz);
    }

    @Transactional(readOnly = true)
    public Clazz getClazz(String clazzId){
        logger.debug("New Clazz {}",clazzId);

        ClazzId clazzId1 = new ClazzId(clazzId);
        Clazz clazz = null;
        for(ClazzRepository clazzRepository:this.clazzRepositories){
            clazz = clazzRepository.loadOf(clazzId1);
            if(clazz != null)
                break;;
        }
        return clazz;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateOpenedTime(ClazzId clazzId,Date openedTime){
        //TODO
    }

    private ClazzRepository getClazzRepositoryOf(Clazz clazz){
        for(ClazzRepository clazzRepository:this.clazzRepositories){
            if(clazzRepository.supports(clazz))
                return clazzRepository;
        }
        return null;
    }

}