package com.zhezhu.access.domain.model.wechat.audit;

import com.zhezhu.commons.domain.ValueObject;
import com.zhezhu.share.domain.id.wechat.WeChatId;
import lombok.*;

/**
 * 注者申请人
 *
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode(exclude = {"name"})
@ToString
public class Applier extends ValueObject {

    private WeChatId weChatId;

    private String weChatOpenId;

    private String name;
}