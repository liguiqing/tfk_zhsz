折竹 之 友师优评 V1.0x

# 友师优评 流程

## 所有http 接口 返回的的数据结构:
{
    "status": {
        "success": true, 
        "code": "", 
        "msg": ""
    }, 
    "xxx": { }, //if object
    "xxx": []   //if array
}

##  1.教师用户访问
### 1.1 微信小程序登录微信成功
        调用接口 /ysyp/wechat/oauth2/{model}
        http  method  GET
             path params:
               model  默认值m1
                       
             request  params:
                code          required
                status  
        return 
            {
                "accessToken": null, //微信授权令
                "openId": null,//微信openId
            }
     
### 1.2 微信查询用户注册信息
        调用接口 /ysyp/wechat/join/{openId}
        http  method  GET  
             path params:
               openId  微信openId
        return 
             {"wechat":
                {
                    "openId": null,   //微信openId
                    "roles": null,     //用户角色[Teacher,Student,Parent],多值以,分隔
                    "personId": null, //用户唯一标识
                    "name": null, 
                    "phones": null
                }
            }
        如果返回为空,表示没有注册,调用1.3接口
        如果role含teacher进入教师流程,调用
        
### 1.3 查询教师班级信息
        调用接口 /apply/audited/{applierId}
        http  method  GET  
             path params:
               applierId  接口 *1.2* 返回的personId
                       
             request  params:
               openId required 微信openId
        return 
            {"clazzs":
                {
                    "schoolId": null, 
                    "schoolName": null, 
                    "clazzId": null, 
                    "clazzName": null, 
                    "applyId": null, 
                    "auditId": null, 
                    "applyDate": null, 
                    "auditDate": null, 
                    "auditCause": null, 
                    "applyDesc": null, 
                    "applierId": null, 
                    "applierName": null, 
                    "auditorId": null, 
                    "auditorName": null, 
                    "ok": false
                }
            }
        如果返回为空,表示没有关注班级,界面进入班级关注流程

### 1.4 查询学校信息
        调用接口 /school/{page}/{size}
        http  method  GET  
             path params:
               page  页码
               size  页容
        return 
            {
                "schools": [
                    {
                        "schoolId": "", 
                        "name": "name1", 
                        "grads": null
                    }, 
                    {
                        "schoolId": "", 
                        "name": "name2", 
                        "grads": null
                    }, 
                    {
                        "schoolId": "", 
                        "name": "name2", 
                        "grads": [
                            {
                                "name": "一年级", 
                                "level": 1
                            }, 
                            {
                                "name": "二年级", 
                                "level": 2
                            }, 
                            {
                                "name": "三年级", 
                                "level": 3
                            }
                        ]
                    }
                ]
            }  
            
### 1.5 查询学校年级班级信息
        调用接口 /clazz/grade/{schoolId}/{gradeLevel}
        http  method  GET  
             path params:
               schoolId    学校唯一标识,1.5返回值的schools[n].schoolId
               gradeLevel  年级序列,1.4返回值的schools[n].grads[m].level
        return   
            {"clazzs": [
                {
                    "clazzId": null, 
                    "name": "c1", 
                    "gradeName": null, 
                    "gradeLevel": 0
                }, 
                {
                    "clazzId": null, 
                    "name": "c2", 
                    "gradeName": null, 
                    "gradeLevel": 0
                }
            ]}     
            
### 1.6 提交班级关注申请
        调用接口 /apply/school
        http  method  POST  
             request body:
                {
                    "applyingSchoolId": null, 
                    "applyingClazzId": null, 
                    "applierId": null, //1.2返回值的personId
                    "applierName": null, 
                    "applierPhone": null, 
                    "applyDate": null, 
                    "cause": null
                }
        return   
            {
                "applyId": ""//关注唯一标识
            }
            
### 1.7 查询已经通过审核班级关注申请
        调用接口 /apply/audited/{applierId}
        http  method  GET  
              path params:
                 applierId    //1.2返回值的personId
        return   
            {
                "clazzs": [
                    {
                        "schoolId": null, 
                        "schoolName": null, 
                        "clazzId": "", 
                        "clazzName": "", 
                        "applyId": null, 
                        "auditId": null, 
                        "applyDate": null, 
                        "auditDate": null, 
                        "auditCause": null, 
                        "applyDesc": null, 
                        "applierId": null, 
                        "applierName": null, 
                        "auditorId": null, 
                        "auditorName": null, 
                        "ok": true
                    }
                ]
            }
                                      