package com.zhezhu.share.infrastructure.person;

import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.person.Gender;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
@Component("localPersonService")
public class PersonServiceImpl implements PersonService {

    @Autowired(required = false)
    @Qualifier("remotePersonService")
    @Setter
    private PersonService remoteProxy; //远程服务代理

    @Autowired
    private JdbcTemplate jdbc;

    private  boolean hasProxy(){
        return this.remoteProxy != null;
    }

    public PersonId getPersonId(String weChatOpenId){
        if(hasProxy())
            return this.remoteProxy.getPersonId(weChatOpenId);

        log.debug("Find PersonId with weChatOpenId:{}",weChatOpenId);
        String sql = "select personId from ac_WeChat where wechatOpenId=?";
        return findPersonId(sql,new Object[]{weChatOpenId});
    }

    public PersonId getPersonId(String schoolId, String clazzId, String name, Gender gender,QueryTarget target){
        if(hasProxy())
            return this.remoteProxy.getPersonId(schoolId, clazzId, name, gender, target);

        log.debug("Find PersonId with:{} {} {} {} ",schoolId,clazzId,name,gender);
        String sql = getPersonIdSql(target);
        return findPersonId(sql,new Object[]{schoolId,clazzId,name,gender.name()});
    }

    @Override
    public String getName(String personId, QueryTarget target) {
        if(hasProxy())
            return this.remoteProxy.getName(personId, target);
        String sql = getNameSql(target);
        String name = jdbc.queryForObject(sql,(rs,r) -> rs.getString("name"),personId);;
        return name;
    }

    private PersonId findPersonId(String sql,Object[] args){
        String personId = jdbc.queryForObject(sql,(rs,r) -> rs.getString("personId"),args);
        if(personId != null)
            return new PersonId(personId);
        return new PersonId();
    }

    private String getNameSql(QueryTarget target){
        switch (target){
            case Teacher:
                return "select a.name from sm_teacher a  where a.personId=?";//TODO
            case Parent:
                return "";//TODO
            default:
                return "select a.name from sm_student a where a.personId=?;";
        }
    }

    private String getPersonIdSql(QueryTarget target){
        switch (target){
            case Teacher:
                return "";//TODO
            case Parent:
                return "";//TODO
            default:
                return "select a.personId from sm_student a inner join sm_student_managed b on b.studentId=a.studentId " +
                        "where  a.schoolId=? and b.clazzId=? and a.name=? and a.gender=?  and b.dateEnds is not null;";
        }
    }

}