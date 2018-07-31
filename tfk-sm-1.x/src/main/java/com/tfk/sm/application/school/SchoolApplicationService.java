package com.tfk.sm.application.school;

import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.school.SchoolScope;
import com.tfk.sm.domain.model.school.School;
import com.tfk.sm.domain.model.school.SchoolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class SchoolApplicationService {
    private static Logger logger = LoggerFactory.getLogger(SchoolApplicationService.class);

    private SchoolRepository schoolRepository;


    public void newSchool(NewSchoolCommand command){
        logger.debug("New School {} ",command);
        SchoolId schoolId = schoolRepository.nextIdentity();
        SchoolScope scope = SchoolScope.get(command.getScop());
        School school = new School(schoolId,command.getName(),command.getAlias(),scope);
        this.schoolRepository.save(school);
    }

}