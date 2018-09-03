package com.zhezhu.access.domain.model.wechat;

import com.zhezhu.commons.domain.EntityRepository;
import com.zhezhu.share.domain.id.wechat.WeChatId;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Repository
public interface WeChatRepository extends EntityRepository<WeChat,WeChatId> {

    @Override
    default WeChatId nextIdentity(){
        return new WeChatId();
    }

    @Cacheable(value = "accessCache",key = "#p0.id",unless = "#result == null")
    @Query("From WeChat where weChatId=?1 and removed=0")
    WeChat loadOf(WeChatId weChatId);

    @Override
    @CacheEvict(value = "accessCache", key="#p0.weChatId.id")
    void save(WeChat weChat);


    @CacheEvict(value = "accessCache",key="#p0.id")
    @Modifying
    @Query(value = "update WeChat set removed = 1 where weChatId=?1")
    void delete(WeChatId weChatId);

    @Query("From WeChat where weChatOpenId=?1 and category=?2 and removed=0")
    WeChat findByWeChatOpenIdAndCategoryEquals(String weChatOpenId,WeChatCategory category);


    @Query("From WeChat where weChatOpenId=?1 and removed=0")
    List<WeChat> findAllByWeChatOpenId(String weChatOpenId);
}