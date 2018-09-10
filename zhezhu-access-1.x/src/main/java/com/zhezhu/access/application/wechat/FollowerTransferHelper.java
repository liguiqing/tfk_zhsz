package com.zhezhu.access.application.wechat;

import com.zhezhu.access.domain.model.wechat.Follower;
import com.zhezhu.access.domain.model.wechat.WeChatCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */

@Component
public class FollowerTransferHelper {
    @Autowired
    private List<FollowerDataTransfer> transfers;

    public FollowerData transTo(Follower follower, WeChatCategory weChatCategory){
        FollowerData data = null;
        for(FollowerDataTransfer transfer:this.transfers){
            data = transfer.trans(follower,weChatCategory);
            if(data != null)
                return data;
        }
        return null;
    }

}