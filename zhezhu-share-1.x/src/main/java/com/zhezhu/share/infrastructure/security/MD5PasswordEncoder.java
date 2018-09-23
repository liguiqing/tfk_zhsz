package com.zhezhu.share.infrastructure.security;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * MD5密码生成器
 *
 * @author Liguiqing
 * @since V3.0
 */

public class MD5PasswordEncoder {

    private String algorithmName = "md5";

    private int hashIterations = 2;

    public static MD5PasswordEncoder defaultEncoder = new MD5PasswordEncoder();

    public MD5PasswordEncoder() {
    }

    public MD5PasswordEncoder(String algorithmName, int hashIterations) {
        this.algorithmName = algorithmName;
        this.hashIterations = hashIterations;
    }

    public  String encode(String salt, String password) {
        return new SimpleHash(this.algorithmName, password, ByteSource.Util.bytes(salt), this.hashIterations).toHex();
    }
}