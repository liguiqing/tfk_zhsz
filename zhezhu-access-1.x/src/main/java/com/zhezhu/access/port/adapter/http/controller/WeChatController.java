package com.zhezhu.access.port.adapter.http.controller;

import com.zhezhu.access.application.wechat.*;
import com.zhezhu.access.domain.model.wechat.WeChatCategory;
import com.zhezhu.access.domain.model.wechat.WebAccessToken;
import com.zhezhu.access.domain.model.wechat.message.XmlMessage;
import com.zhezhu.access.domain.model.wechat.xml.XStreamTransformer;
import com.zhezhu.commons.port.adaptor.http.controller.AbstractHttpController;
import com.zhezhu.share.domain.person.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    private WeChatQueryService weChatQueryService;

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

        List<WeChatData> data = weChatQueryService.getWeChats(openId);
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
        logger.debug("URL /wechat/bind Method=POST {}",command.getWechatOpenId());

        String weChatId = wechatApplicationService.bind(command);
        return newModelAndViewBuilder("/wechat/bindSuccess").withData("weChatId",weChatId).creat();
    }

    /**
     * 添加微信号的被关注者申请
     *
     * @param command
     * @return
     */
    @RequestMapping(value ="/apply/follower",method = RequestMethod.POST)
    public ModelAndView onFollower(@RequestBody BindCommand command){
        logger.debug("URL /wechat/apply/follower Method=POST {}",command.getWechatOpenId());

        wechatApplicationService.applyFollowers(command);
        return newModelAndViewBuilder("/wechat/applyFollowersSuccess").creat();
    }

    /**
     * 取消微信号的被关注者
     *
     * @param applyId
     * @return
     */
    @RequestMapping(value ="/apply/follower/{applyId}",method = RequestMethod.DELETE)
    public ModelAndView onCancelFollower(@PathVariable String applyId){
        logger.debug("URL /wechat/apply/follower/{} Method=DELETE ",applyId);

        wechatApplicationService.cancelApply(applyId);
        return newModelAndViewBuilder("/wechat/cancelFollowersSuccess").creat();
    }

    /**
     * 审核关注申请
     *
     * @param command
     * @return
     */
    @RequestMapping(value ="/audit/follower",method = RequestMethod.POST)
    public ModelAndView onAuditFollower(@RequestBody ApplyAuditCommand command){
        logger.debug("URL /wechat/audit/follower Method=POST {}",command);

        String auditId = wechatApplicationService.applyAudit(command);
        return newModelAndViewBuilder("/wechat/auditFollowersSuccess").withData("auditId",auditId).creat();
    }

    /**
     * 取消关注审核
     *
     * @param command
     * @return
     */
    @RequestMapping(value ="/audit/follower",method = RequestMethod.DELETE)
    public ModelAndView onCancelAuditFollower(@RequestBody ApplyAuditCommand command){
        logger.debug("URL /wechat/audit/follower Method=DELETE {}",command);

        wechatApplicationService.cancelApplyAudit(command);
        return newModelAndViewBuilder("/wechat/cancelAuditFollowersSuccess").creat();
    }

    /**
     * 查询学生关注者
     *
     * @param weChatOpenId
     * @return
     */
    @RequestMapping(value ="/followers/student/{weChatOpenId}",method = RequestMethod.GET)
    public ModelAndView onGetFollowerOfStudent(@PathVariable String weChatOpenId){
        logger.debug("URL /wechat/followers/student/{} Method=GET ",weChatOpenId);

        List<FollowerData> followers = weChatQueryService.getFollowers(weChatOpenId, WeChatCategory.Student);
        return newModelAndViewBuilder("/wechat/followerList").withData("followers",followers).creat();
    }

    /**
     *
     * @param name
     * @param clazzId
     * @param credentialsName
     * @param credentialsValue
     * @param gender
     * @return
     */
    @RequestMapping(value ="/followers/student/query",method = RequestMethod.GET)
    public ModelAndView onQueryFollowerOfStudent(@RequestParam String name,
                                                 @RequestParam String clazzId,
                                                 @RequestParam(required = false) String credentialsName,
                                                 @RequestParam(required = false) String credentialsValue,
                                                 @RequestParam(required = false) String gender
                                                    ){
        logger.debug("URL /wechat/followers/student/query?name={}&clazzId={} Method=GET ",name,clazzId);

        long c = weChatQueryService.findFollowerBy(name, clazzId, credentialsName, credentialsValue, Gender.valueOf(gender));
        return newModelAndViewBuilder("/wechat/followerList").withData("c",c).creat();
    }

}