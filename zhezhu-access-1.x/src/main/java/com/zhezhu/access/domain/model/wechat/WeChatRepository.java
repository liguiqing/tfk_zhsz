package com.zhezhu.access.domain.model.wechat;

import com.zhezhu.commons.domain.EntityRepository;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.wechat.WeChatId;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    WeChat findByPersonIdAndCategoryEquals(PersonId personId,WeChatCategory category);

    @Query("From WeChat where weChatOpenId=?1 and removed=0")
    List<WeChat> findAllByWeChatOpenId(String weChatOpenId);

    @Query(value = "select a.* From ac_WeChat a join ac_wechatfollower b on b.weChatId=a.weChatId where " +
            "b.auditorId is null LIMIT :page,:size",nativeQuery = true)
    List<WeChat> findAllByFollowersIsNotAudited(@Param("page")int page, @Param("size")int size);

    @Query(value = "select a.* From ac_WeChat a join ac_wechatfollower b on b.weChatId=a.weChatId where " +
            "b.auditorId is not null LIMIT :page,:size",nativeQuery = true)
    List<WeChat> findAllByFollowersIsAudited(@Param("page")int page, @Param("size")int size);
}