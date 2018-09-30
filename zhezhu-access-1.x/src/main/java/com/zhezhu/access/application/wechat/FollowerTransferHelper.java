package com.zhezhu.access.application.wechat;

import com.zhezhu.access.domain.model.wechat.Follower;
import com.zhezhu.access.domain.model.wechat.WeChatCategory;
import com.zhezhu.access.domain.model.wechat.audit.FollowApply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */

@Component
public class FollowerTransferHelper {

    private List<FollowerDataTransfer> transfers;

    @Autowired
    public FollowerTransferHelper(List<FollowerDataTransfer> transfers) {
        this.transfers = transfers;
    }

    public FollowerData transTo(Follower follower, WeChatCategory weChatCategory){
        for(FollowerDataTransfer transfer:this.transfers){
            FollowerData data = transfer.trans(follower,weChatCategory);
            if(data != null)
                return data;
        }
        return null;
    }

    public FollowerData transTo(FollowApply apply, WeChatCategory weChatCategory){
        for(FollowerDataTransfer transfer:this.transfers){
            FollowerData data = transfer.trans(apply,weChatCategory);
            if(data != null)
                return data;
        }
        return null;
    }

}