package com.zhezhu.share.infrastructure.validate.contact;

import com.zhezhu.share.domain.person.Contact;

/**
 * 联系方式验证器
 *
 * @author Liguiqing
 * @since V3.0
 */

public interface ContactValidator {
    boolean supports(Contact contact);

    boolean validate(Contact contact);
}