package com.zhezhu.share.domain.person;

/**
 * 证件号验证服务
 *
 * @author Liguiqing
 * @since V3.0
 */

public interface CredentialsValidateService {

    boolean validate(Credentials credentials);
}