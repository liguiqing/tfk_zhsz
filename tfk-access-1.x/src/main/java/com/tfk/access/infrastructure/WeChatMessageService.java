package com.tfk.access.infrastructure;

import com.tfk.share.domain.id.PersonId;
import com.tfk.share.domain.id.school.ClazzId;
import com.tfk.share.domain.id.school.SchoolId;
import org.springframework.stereotype.Component;

/**
 * 微信消息服务
 *
 * @author Liguiqing
 * @since V3.0
 */
@Component
public class WeChatMessageService {


    public void notifyNewFollowerApply(SchoolId schoolId, ClazzId clazzId, PersonId followerId,String cause){

    }
}