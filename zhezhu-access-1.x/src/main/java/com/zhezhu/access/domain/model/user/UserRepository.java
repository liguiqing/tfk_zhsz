package com.zhezhu.access.domain.model.user;

import com.zhezhu.commons.domain.EntityRepository;
import com.zhezhu.share.domain.id.UserId;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Liguiqing
 * @since V3.0
 */

@Repository
public interface UserRepository extends EntityRepository<User, UserId> {

    default  UserId nextIdentity(){return new UserId();}

    @Cacheable(value = "accessCache",key = "#p0.id",unless = "#result == null")
    @Query("From User where userId=?1")
    User loadOf(UserId userId);

    void save(User user);

    User findByUserName(String userName);

    @CacheEvict(value = "accessCache",key="#p0.id")
    @Modifying
    @Query(value = "DELETE from User where userId=?1")
    void delete(UserId userId);
}