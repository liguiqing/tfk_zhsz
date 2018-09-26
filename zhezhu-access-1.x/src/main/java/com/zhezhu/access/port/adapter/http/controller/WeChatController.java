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

    private WeChatApplicationService wechatApplicationService;

    private WeChatQueryService weChatQueryService;

    @Autowired
    public WeChatController(WeChatApplicationService wechatApplicationService,
                            WeChatQueryService weChatQueryService) {
        this.wechatApplicationService = wechatApplicationService;
        this.weChatQueryService = weChatQueryService;
    }

    /**
     * 微信关注成功后欢迎信息
     * 只在关注成功的首次调用
     *
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
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
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     *
     */
    @RequestMapping(value ="/follow",method = RequestMethod.POST)
    public void onDialog(HttpServletRequest request,HttpServletResponse response)throws Exception{
        logger.debug("URL /wechat Method=GET");
        
        XmlMessage message = XStreamTransformer.fromXml(XmlMessage.class, request.getInputStream());
        String xml = wechatApplicationService.dialog(message);
        output(xml,response);
    }

    /**
     * 查询微信授权信息
     *
     * @param code 微信授权码
     * @param state 微信授权状态
     *
     * @return {@link ModelAndView}
     */
    @RequestMapping(value = "/info")
    public ModelAndView onOauth2(@RequestParam String code,
                                 @RequestParam(required = false,defaultValue = "000000") String state){
        logger.debug(" URL /wechat/info Method=GET  code:{},state:{}", code, state);

        WebAccessToken accessToken = wechatApplicationService.getWeChatAccessToken(code);
        List<WeChatData> data = weChatQueryService.getWeChats(accessToken.getOpenId());
        return newModelAndViewBuilder("/wechat/wechatList")
                .withData("openId", accessToken.getOpenId())
                .withData("token",accessToken.getAccessToken())
                .withData("weChats",data)
                .creat();
    }

    /**
     * 查询微信用户注册信息,微信用户可以注册为系统中的三种角色:<br>
     *     {@link WeChatCategory#Teacher};<br>
     *     {@link WeChatCategory#Student};<br>
     *     {@link WeChatCategory#Parent};<br>
     * 三种角色可以同时存在,但一个角色只能注册一个
     * @param openId 微信openId
     * @return {@link ModelAndView}
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
     * @param command {@link BindCommand}
     * @return {@link ModelAndView}
     */
    @RequestMapping(value ="/bind",method = RequestMethod.POST)
    public ModelAndView onBind(@RequestBody BindCommand command){
        logger.debug("URL /wechat/bind Method=POST {}",command.getWechatOpenId());

        String weChatId = wechatApplicationService.bind(command);
        List<WeChatData> data = weChatQueryService.getWeChats(command.getWechatOpenId());
        return newModelAndViewBuilder("/wechat/bindSuccess").withData("weChatId",weChatId).withData("weChats",data).creat();
    }

    /**
     * 微信号被关注者申请
     *
     * @param command {@link BindCommand}
     * @return {@link ModelAndView}
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
     * @param applyId value of {@link com.zhezhu.share.domain.id.wechat.FollowApplyId}
     * @return {@link ModelAndView}
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
     * @param command {@link ApplyAuditCommand}
     * @return {@link ModelAndView}
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
     * @param command {@link ApplyAuditCommand}
     * @return {@link ModelAndView}
     */
    @RequestMapping(value ="/audit/follower",method = RequestMethod.DELETE)
    public ModelAndView onCancelAuditFollower(@RequestBody ApplyAuditCommand command){
        logger.debug("URL /wechat/audit/follower Method=DELETE {}",command);

        wechatApplicationService.cancelApplyAudit(command);
        return newModelAndViewBuilder("/wechat/cancelAuditFollowersSuccess").creat();
    }

    /**
     * 查询微信号的关注者(学生)
     *
     * @param openId  微信openId
     * @return {@link ModelAndView}
     */
    @RequestMapping(value ="/query/followers/{openId}",method = RequestMethod.GET)
    public ModelAndView onGetFollowerOfStudent(@PathVariable String openId,
                                               @RequestParam(required = false,defaultValue = "true") boolean isAudited){
        logger.debug("URL /wechat/query/followers/{} Method=GET ",openId);

        List<FollowerData> followers = weChatQueryService.getFollowers(openId, WeChatCategory.Student,isAudited);
        return newModelAndViewBuilder("/wechat/followerList").withData("followers",followers).creat();
    }

    /**
     * 查询微的关注者(学生)
     *
     * @param page 页码
     * @param size 页容
     * @return {@link ModelAndView}
     */
    @RequestMapping(value ="/query/all/followers/{page}/{size}",method = RequestMethod.GET)
    public ModelAndView onGetAllFollowerOfStudent(@PathVariable int page,@PathVariable int size){
        logger.debug("URL /wechat/query/all/followers/{}/{} Method=GET ",page,size);

        List<FollowerData> followers = weChatQueryService.getAllFollowers(pageStart(page,size),size,false);
        return newModelAndViewBuilder("/apply/studentFollowApplyAuditingList").withData("followers",followers).creat();
    }

    /**
     * 查询可申请的关注者
     *
     * @param name 姓名
     * @param clazzId 班级id {@link com.zhezhu.share.domain.id.school.ClazzId}
     * @param credentialsName 证件名称 如身份证
     * @param credentialsValue 证件号
     * @param gender 性别 {@link Gender}
     * @return {@link ModelAndView}
     */
    @RequestMapping(value ="/apply/query/followers",method = RequestMethod.GET)
    public ModelAndView onQueryFollowerCanBeApplied(@RequestParam String name,
                                                 @RequestParam String clazzId,
                                                 @RequestParam(required = false) String credentialsName,
                                                 @RequestParam(required = false) String credentialsValue,
                                                 @RequestParam(required = false) String gender
                                                    ){
        logger.debug("URL /wechat/apply/query/followers?name={}&clazzId={} Method=GET ",name,clazzId);
        Gender gender1 = gender == null ? null : Gender.valueOf(gender);
        String[] personIds = weChatQueryService.findFollowerBy(name, clazzId, credentialsName, credentialsValue,gender1);
        return newModelAndViewBuilder("/wechat/followerList").withData("personIds",personIds).creat();
    }

}