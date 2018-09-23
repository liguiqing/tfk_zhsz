package com.zhezhu.access.application.user;

import com.zhezhu.access.domain.model.user.PasswordService;
import com.zhezhu.access.domain.model.user.User;
import com.zhezhu.access.domain.model.user.UserRepository;
import com.zhezhu.commons.AssertionConcerns;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.UserId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Slf4j
@Service
public class UserApplicationService {
    private PasswordService passwordService;

    private UserRepository userRepository;

    @Autowired
    public UserApplicationService(PasswordService passwordService,UserRepository userRepository) {
        this.passwordService = passwordService;
        this.userRepository = userRepository;
    }

    /**
     * 创建用户
     * @param command {@link NewUserCommand} password will be 123456 when command.password is null or empty
     * @return userId
     */
    @Transactional(rollbackFor = Exception.class)
    public String createUser(NewUserCommand command){
        log.debug("Create new user : {}",command.getUserName());

        User user_ = this.userRepository.findByUserName(command.getUserName());
        AssertionConcerns.assertArgumentNull(user_,"ac-01-009");
        UserId userId = this.userRepository.nextIdentity();
        String password = passwordService.password(command.getPassword(),userId.id());
        User user = User.builder().userName(command.getUserName()).password(password).ok(true).createTime(DateUtilWrapper.Now).build();
        this.userRepository.save(user);
        return userId.id();
    }

    /**
     * 修改用户密码
     *
     * @param command {@link UpdatePasswordCommand}
     */
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(UpdatePasswordCommand command){
        log.debug("Update user password : {}",command.getUserId());
        UserId userId = new UserId(command.getUserId());

        User user = this.userRepository.loadOf(userId);
        String password = passwordService.password(command.getOldPassword(),userId.id());
        String password_ = passwordService.password(command.getNewPassword(),userId.id());
        AssertionConcerns.assertArgumentEquals(user.getPassword(),password,"ac-01-011");
        AssertionConcerns.assertArgumentNotEquals(password_,password,"ac-01-012");
        user.updatePassword(password_);
        this.userRepository.save(user);
    }
}