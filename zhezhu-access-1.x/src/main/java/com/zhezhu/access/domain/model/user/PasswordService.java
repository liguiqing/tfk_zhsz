package com.zhezhu.access.domain.model.user;

import com.zhezhu.commons.AssertionConcerns;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.UserId;
import com.zhezhu.share.infrastructure.security.MD5PasswordEncoder;

/**
 * @author Liguiqing
 * @since V3.0
 */

public class PasswordService {

    private MD5PasswordEncoder passwordEncoder;

    private String defaultPassword;

    private int minLength;

    private int maxLength;

    public PasswordService(MD5PasswordEncoder passwordEncoder,int minLength,int maxLength, String defaultPassword) {
        this.passwordEncoder = passwordEncoder;
        this.defaultPassword = defaultPassword;
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    public String password(String password,String salt){
        if(password == null){
            return passwordEncoder.encode(salt, this.defaultPassword);
        }
        int length = password.length();
        AssertionConcerns.assertArgumentRange(length,this.minLength,this.maxLength,"ac-01-010");
        return passwordEncoder.encode(salt, password);
    }

    public static void main(String[] args)throws Exception{
        UserId userId = new UserId();
        PersonId personId = new PersonId();
        PasswordService passwordService = new PasswordService(MD5PasswordEncoder.defaultEncoder,6,8,"oxoxoox");
        System.out.print("userId: "+userId.id() +" password: "+passwordService.password(null,userId.id()) +" personId: "+personId.id());
    }

}