package com.zhezhu.access.application.user;

import lombok.*;

/**
 * @author Liguiqing
 * @since V3.0
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(of={"userId"})
@ToString(of={"userId"})
public class UpdatePasswordCommand {
    private String userId;

    private String oldPassword;

    private String newPassword;

}