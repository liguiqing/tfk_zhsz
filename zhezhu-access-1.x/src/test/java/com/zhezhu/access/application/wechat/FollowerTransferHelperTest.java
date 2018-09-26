package com.zhezhu.access.application.wechat;

import com.google.common.collect.Lists;
import com.zhezhu.access.domain.model.wechat.Follower;
import com.zhezhu.access.domain.model.wechat.WeChatCategory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

/**
 * Copyright (c) 2016,$today.year, 深圳市易考试乐学测评有限公司
 **/
public class FollowerTransferHelperTest {

    @Mock
    private FollowerDataTransfer transfer1;

    @Mock
    private FollowerDataTransfer transfer2;

    @Mock
    private FollowerDataTransfer transfer3;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }

    private FollowerTransferHelper getService(){
        List<FollowerDataTransfer> transfers = Lists.newArrayList();
        transfers.add(transfer1);
        transfers.add(transfer2);
        transfers.add(transfer3);

        FollowerTransferHelper helper = new FollowerTransferHelper(transfers);
        return spy(helper);
    }

    @Test
    public void transTo(){
        FollowerTransferHelper helper = getService();
        Follower follower = mock(Follower.class);
        FollowerData data = mock(FollowerData.class);
        when(transfer1.trans(any(Follower.class), eq(WeChatCategory.Teacher))).thenReturn(null);
        when(transfer2.trans(any(Follower.class), eq(WeChatCategory.Student))).thenReturn(data);
        when(transfer2.trans(any(Follower.class), eq(WeChatCategory.Parent))).thenReturn(null);

        FollowerData data_ = helper.transTo(follower,WeChatCategory.Teacher);
        assertNull(data_);

        data_ = helper.transTo(follower,WeChatCategory.Parent);
        assertNull(data_);
        data_ = helper.transTo(follower,WeChatCategory.Student);
        assertNotNull(data_);
        verify(transfer1,times(1)).trans(any(Follower.class), eq(WeChatCategory.Teacher));
        verify(transfer1,times(1)).trans(any(Follower.class), eq(WeChatCategory.Student));
        verify(transfer1,times(1)).trans(any(Follower.class), eq(WeChatCategory.Parent));
    }
}