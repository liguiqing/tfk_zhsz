package com.zhezhu.access.application.wechat;

import com.zhezhu.access.domain.model.wechat.WeChat;
import com.zhezhu.access.domain.model.wechat.WeChatCategory;
import com.zhezhu.access.domain.model.wechat.WeChatRepository;
import com.zhezhu.commons.util.CollectionsUtilWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class WeChatQueryService {

    @Autowired
    private WeChatRepository weChatRepository;

    @Autowired
    private FollowerTransferHelper followerTransferHelper;

    public List<WeChatData> getWeChats(String weChatOpenId){
        log.debug("Get Wechats by OpenId {}",weChatOpenId);

        return this.weChatRepository.findAllByWeChatOpenId(weChatOpenId)
                .stream().map(weChat -> WeChatData.builder()
                        .personId(weChat.getPersonId().id())
                        .name(weChat.getName())
                        .openId(weChat.getWeChatOpenId())
                        .phone(weChat.getPhone())
                        .role(weChat.getCategory().name())
                        .build())
                .collect(Collectors.toList());
    }

    public List<FollowerData> getFollowers(String weChatOpenId,WeChatCategory category){
        log.debug("Get Followers by OpenId {}",weChatOpenId);

        WeChat weChat = this.weChatRepository.findByWeChatOpenIdAndCategoryEquals(weChatOpenId,category);
        if(weChat == null || CollectionsUtilWrapper.isNullOrEmpty(weChat.getFollowers()))
            return new ArrayList<>();

        return weChat.getFollowers().stream().filter(follower -> follower.isAudited()).map(follower ->
            followerTransferHelper.transTo(follower,category)
        ).collect(Collectors.toList());
    }
}