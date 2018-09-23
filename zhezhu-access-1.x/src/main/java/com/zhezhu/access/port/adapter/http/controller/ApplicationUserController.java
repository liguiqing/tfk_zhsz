package com.zhezhu.access.port.adapter.http.controller;

import com.zhezhu.access.application.user.NewUserCommand;
import com.zhezhu.access.application.user.UpdatePasswordCommand;
import com.zhezhu.access.application.user.UserApplicationService;
import com.zhezhu.commons.port.adaptor.http.controller.AbstractHttpController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Liguiqing
 * @since V3.0
 */
@Controller
@RequestMapping("/user")
public class ApplicationUserController extends AbstractHttpController {

    private UserApplicationService userApplicationService;

    @Autowired
    public ApplicationUserController(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    /**
     * 创建用户
     * @param command {@link NewUserCommand}
     */
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView onNewUser(@RequestBody NewUserCommand command){
        logger.debug("URL /user Method=POST");

        String userId = this.userApplicationService.createUser(command);
        return newModelAndViewBuilder("/user/userList")
                .withData("userId", userId)
                .creat();
    }

    /**
     * 创建用户
     * @param command {@link NewUserCommand}
     */
    @RequestMapping(value="/password",method = RequestMethod.PUT)
    public ModelAndView onUpdateUserPassword(@RequestBody UpdatePasswordCommand command){
        logger.debug("URL /user/password Method=POST");

        this.userApplicationService.updatePassword(command);
        return newModelAndViewBuilder("/user/userDetail")
                .withData("userId", command.getUserId())
                .creat();
    }
}