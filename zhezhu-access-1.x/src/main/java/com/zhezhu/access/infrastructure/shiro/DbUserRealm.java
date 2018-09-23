package com.zhezhu.access.infrastructure.shiro;

import com.zhezhu.commons.lang.Throwables;
import com.zhezhu.commons.security.InnerUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 来自数据库的用户数据
 *
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
public class DbUserRealm extends AuthorizingRealm {

    private String sql = "select 'xxoxx' as userId, 'xx' as userName,'oxoxooxxoox' as pwd,'xx' as userId,'oo' as realName,'xxoo' as personId from dual";

    private JdbcTemplate jdbc;

    public DbUserRealm(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public DbUserRealm(String sql, JdbcTemplate jdbc) {
        if(sql != null && sql.length() >10){
            this.sql = sql;
        }
        this.jdbc = jdbc;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return new SimpleAuthorizationInfo();
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        String userName = userToken.getUsername();
        InnerUser user = getUserFromDb(userName);
        if (user == null) {
            throw new UnknownAccountException();
        }
        log.debug(user.toString());

        SimplePrincipalCollection principalCollection = new SimplePrincipalCollection(user, getName());
        //principalCollection.add(user,userName);
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo();
        simpleAuthenticationInfo.setCredentials(user.pwd());
        simpleAuthenticationInfo.setPrincipals(principalCollection);
        return simpleAuthenticationInfo;
    }

    private InnerUser getUserFromDb(String userName){
        try {
            return jdbc.queryForObject(sql,(rs,rn)->InnerUser.builder()
                    .userId(rs.getString("userId"))
                    .userName(rs.getString("userName"))
                    .userRealName(rs.getString("realName"))
                    .userPersonId(rs.getString("personId"))
                    .password(rs.getString("pwd"))
                    .build(),userName);
        }catch (Exception e){
            log.error(Throwables.toString(e));
            throw  new UnknownAccountException();
        }
    }

}