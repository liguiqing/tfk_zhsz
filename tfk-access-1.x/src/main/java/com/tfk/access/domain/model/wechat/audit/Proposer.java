package com.tfk.access.domain.model.wechat.audit;

import com.tfk.commons.domain.ValueObject;
import com.tfk.share.domain.id.wechat.WeChatId;
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
public class Proposer extends ValueObject {

    private WeChatId weChatId;

    private String weChatOpenId;

    private String name;
}