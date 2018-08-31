package com.zhezhu.access.infrastructure;

import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
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