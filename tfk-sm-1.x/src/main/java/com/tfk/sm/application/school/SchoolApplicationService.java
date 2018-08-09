package com.tfk.sm.application.school;

import com.tfk.share.domain.id.school.SchoolId;
import com.tfk.share.domain.school.SchoolScope;
import com.tfk.sm.domain.model.school.School;
import com.tfk.sm.domain.model.school.SchoolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Service
public class SchoolApplicationService {
    private static Logger logger = LoggerFactory.getLogger(SchoolApplicationService.class);

    @Autowired
    private SchoolRepository schoolRepository;

    public SchoolApplicationService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public void newSchool(NewSchoolCommand command){
        logger.debug("New School {} ",command);
        School old = this.schoolRepository.findSchoolByNameEquals(command.getName());
        if(old != null)
            return;

        SchoolId schoolId = schoolRepository.nextIdentity();
        SchoolScope scope = SchoolScope.get(command.getScop());
        School school = new School(schoolId,command.getName(),command.getAlias(),scope);
        this.schoolRepository.save(school);
    }

}