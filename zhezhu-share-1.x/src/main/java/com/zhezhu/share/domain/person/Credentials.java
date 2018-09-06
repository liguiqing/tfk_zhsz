package com.zhezhu.share.domain.person;

import com.zhezhu.commons.domain.IdentifiedValueObject;
import com.zhezhu.share.domain.id.PersonId;
import lombok.*;

import java.util.Date;

/**
 * 人的证件
 *
 * @author Liguiqing
 * @since V3.0
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of={"personId","name","value"},callSuper = false)
@Builder
@ToString
@Getter
@Setter
public class Credentials extends IdentifiedValueObject {

    private PersonId personId;

    private String name;

    private String value;

    private Date releaseDate; //发放日期

    private Date expireDate;  //有效日期

}