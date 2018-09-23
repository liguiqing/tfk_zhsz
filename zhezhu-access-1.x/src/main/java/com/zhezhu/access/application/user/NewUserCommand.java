package com.zhezhu.access.application.user;

import lombok.*;

/**
 * @author Liguiqing
 * @since V3.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class NewUserCommand {
    private String userName;

    private String password;

}