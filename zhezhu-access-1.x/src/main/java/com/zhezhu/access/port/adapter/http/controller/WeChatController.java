package com.zhezhu.access.port.adapter.http.controller;

import com.zhezhu.access.application.wechat.BindCommand;
import com.zhezhu.access.application.wechat.WeChatData;
import com.zhezhu.access.application.wechat.WeChatApplicationService;
import com.zhezhu.access.application.wechat.WechatQueryService;
import com.zhezhu.access.domain.model.wechat.WebAccessToken;
import com.zhezhu.access.domain.model.wechat.message.XmlMessage;
import com.zhezhu.access.domain.model.wechat.xml.XStreamTransformer;
import com.zhezhu.commons.port.adaptor.http.controller.AbstractHttpController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 *
 * 微信公众号/小程序API
 *
 * @author Liguiqing
 * @since V3.0
 */
@Controller
@RequestMapping("/wechat")
public class WeChatController extends AbstractHttpController {

    @Autowired(required = false)
    private WeChatApplicationService wechatApplicationService;

    @Autowired(required = false)
    private WechatQueryService wechatQueryService;

    /**
     * 微信关注成功后欢迎信息
     * 只在关注成功的首次调用
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value ="/follow",method = RequestMethod.GET)
    public void onFollow(HttpServletRequest request,HttpServletResponse response){
        logger.debug("URL /wechat Method=GET");
        Map<String,String> params = getServletWrapper().getParameterMap(request);
        String content = wechatApplicationService.follow(params);
        output(content,response);
    }

    /**
     * 微信公众号对话信息反馈
     *
     * @param request
     * @param response
     */
    @RequestMapping(value ="/follow",method = RequestMethod.POST)
    public void onDialog(HttpServletRequest request,HttpServletResponse response)throws Exception{
        logger.debug("URL /wechat Method=GET");
        
        XmlMessage message = XStreamTransformer.fromXml(XmlMessage.class, request.getInputStream());
        String xml = wechatApplicationService.dialog(message);
        output(xml,response);
    }

    @RequestMapping(value = "/oauth2/{model}")
    public ModelAndView onOauth2(@PathVariable String model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        logger.debug(" URL /wechat/oauth2/{} Method=GET  code:{},state:{}",model, code, state);

        WebAccessToken accessToken = wechatApplicationService.getWeChatAccessToken(code);
        return newModelAndViewBuilder("/menu/model/" + model)
                .withData("openId", accessToken.getOpenId())
                .withData("token",accessToken.getAccessToken())
                .creat();
    }

    /**
     * 查询微信用户注册信息
     * 微信用户可以注册为系统中的三种角色:Teacher,Student,Parent,三种角色可以同时存在,但一个角色只能注册一个
     * @param openId
     * @return
     */
    @RequestMapping(value = "/join/{openId}")
    public ModelAndView onGetJoined(@PathVariable String openId){
        logger.debug("URL /wechat/join/{}", openId);

        List<WeChatData> data = wechatQueryService.getWeChats(openId);
        return newModelAndViewBuilder("/wechat/wechatList").withData("weChats",data).creat();
    }

    /**
     * 微信信息与系统绑定
     *
     * @param command
     * @return
     */
    @RequestMapping(value ="/bind",method = RequestMethod.POST)
    public ModelAndView onBind(@RequestBody BindCommand command){
        logger.debug("URL /wechat/bind Method=GET {}",command.getWechatOpenId());
        String weChatId = wechatApplicationService.bind(command);
        return newModelAndViewBuilder("/wechat/bindSuccess").withData("weChatId",weChatId).creat();
    }

    /**
     * 添加微信号的被关注者
     * @param command
     * @return
     */
    @RequestMapping(value ="/follower",method = RequestMethod.POST)
    public ModelAndView onFollower(@RequestBody BindCommand command){
        logger.debug("URL /wechat/bind Method=GET {}",command.getWechatOpenId());
        wechatApplicationService.bind(command);
        return newModelAndViewBuilder("/wechat/bindSuccess").creat();
    }

    /**
     * 取消微信号的被关注者
     * @param command
     * @return
     */
    @RequestMapping(value ="/follower",method = RequestMethod.PUT)
    public ModelAndView onCancelFollower(@RequestBody BindCommand command){
        logger.debug("URL /wechat/bind Method=GET {}",command.getWechatOpenId());
        wechatApplicationService.bindCancel(command);
        return newModelAndViewBuilder("/wechat/bindSuccess").creat();
    }


}