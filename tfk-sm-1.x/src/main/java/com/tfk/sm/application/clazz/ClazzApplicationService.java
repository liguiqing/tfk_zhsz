package com.tfk.sm.application.clazz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class ClazzApplicationService {
    private static Logger logger = LoggerFactory.getLogger(ClazzApplicationService.class);

    public void newClazz(NewClazzCommand command){
        logger.debug("New Clazz {}",command);

    }
}