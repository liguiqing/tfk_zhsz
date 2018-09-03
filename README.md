# 折竹 

## http 接口返回数据结构:        
        {
            "status":{
                "success": true,   //响应状态,只有值为true时才有 ooo/xxx数据
                "code": "",        //响应代码,
                "msg": "",         //响应消息,
            },
            "ooo":{}    //if Object
            "xxx":[]    //if Array
        }

## 友师优评  流程
>rootpath /ysyp
##  1.教师用户访问
### 1.1 微信小程序登录微信成功
        调用接口 /wechat/oauth2/{model}
        http  method  GET
             path params:
                 model  string 默认值m1
                       
             request  params:
                 code    string required
                 status  string
        return 
            {
                "accessToken": "", //微信授权令
                "openId": ""       //微信openId
            }
     
### 1.2 微信查询用户注册信息
        调用接口 /wechat/join/{openId}
        http  method  GET  
              path params:
                  openId  微信openId
        return 
             {"wechats":
                [{
                    "openId": "",    //微信openId
                    "role": "",      //用户角色[Teacher,Student,Parent],多值以,分隔
                    "personId": "",  //用户唯一标识
                    "name": "", 
                    "phone": ""
                }]
            }
        如果返回为空,表示没有注册,调用1.3接口
        如果role含teacher进入教师流程,调用
        
### 1.3 查询教师班级信息
        调用接口 /apply/audited/{applierId}
        http  method  GET  
             path params:
               applierId  string 接口 *1.2* 返回的personId
                       
             request  params:
               openId string required 微信openId
        return 
            {"clazzs":
                {
                    "schoolId": "", 
                    "schoolName": "", 
                    "clazzId": "", 
                    "clazzName": "", 
                    "applyId": "", 
                    "auditId": "", 
                    "applyDate": date, 
                    "auditDate": "", 
                    "auditCause": "", 
                    "applyDesc": "", 
                    "applierId": "", 
                    "applierName": "", 
                    "auditorId": "", 
                    "auditorName": "", 
                    "ok": true
                }
            }
        如果返回为空,表示没有关注班级,界面进入班级关注流程

### 1.4 查询学校信息
        调用接口 /school/{page}/{size}
        http  method  GET  
             path params:
               page  number 页码
               size  number 页容
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
                        "name": "", 
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
               schoolId    string 学校唯一标识,1.4返回值的schools[n].schoolId
               gradeLevel  string 年级序列,1.4返回值的schools[n].grads[m].level
        return   
            {"clazzs": [
                {
                    "clazzId": "", 
                    "name": "", 
                    "gradeName": "", 
                    "gradeLevel": 0
                }, 
                {
                    "clazzId": "", 
                    "name": "", 
                    "gradeName": "", 
                    "gradeLevel": 0
                }
            ]}     
            
### 1.6 提交班级关注申请
        调用接口 /apply/school
        http  method  POST  
             request body:
                {
                    "applyingSchoolId": "", 
                    "applyingClazzId": "", 
                    "applierId": "", 
                    "applierName": "", 
                    "applierPhone": "", 
                    "applyDate": date, 
                    "cause": ""
                }
        return   
            {
                "applyId": ""
            }
            
### 1.7 查询已经通过审核班级关注申请
        调用接口 /apply/audited/{applierId}
        http  method  GET  
              path params:
                 applierId    string
        return   
            {
                "clazzs": [
                    {
                        "schoolId": "", 
                        "schoolName": "", 
                        "clazzId": "", 
                        "clazzName": "", 
                        "applyId": "", 
                        "auditId": "", 
                        "applyDate": date, 
                        "auditDate": null, 
                        "auditCause": "", 
                        "applyDesc": "", 
                        "applierId": "", 
                        "applierName": "", 
                        "auditorId": "", 
                        "auditorName": "", 
                        "ok": true
                    }
                ]
            }
### 1.8 查询班级学生信息
        调用接口 /student/list/clazz/{schoolId}/{clazzId}
        http  method  GET  
              path params:
                 schoolId    string
                 clazzId     string
        return   
            {
                "students": [
                    {
                     "schoolId":"",
                     "clazzId":"",
                     "studentId":"",
                     "personId":"",
                     "name":"",
                     "gender":"Male/Female",
                     "gradeName":"",
                     "gradeLevel":1,
                     "clazzName":""
                    }
                ]
            }
 
### 1.9 查询班级学生信息,按姓分组排序
        调用接口 /student/list/clazz/nameSorted/{schoolId}/{clazzId}
        http  method  GET  
              path params:
                 schoolId    string
                 clazzId     string
        return   
            {
                 "students":["letter":"C",
                     "students": [
                        {
                         "schoolId":"",
                         "clazzId":"",
                         "studentId":"",
                         "personId":"",
                         "name":"",
                         "gender":"Male",
                         "gradeName":"",
                         "gradeLevel":1,
                         "clazzName":""
                        }]
                ]
            }
 
 ### 1.10 查询学校年级评价指标
         调用接口 /index/owner/{ownerId}/{group}
         http  method  GET  
               path params:
                  ownerId     string schoolId
                    group     string 级别,grade.level
               request params:
             withChildren     boolean
             
         return   
             {
                  "indexes":[
                      {
                          "indexId":"",
                          "categoryName":"",
                          "name":"Name",
                          "score":0.00,
                          "weight":0.00,
                          "description":"",
                          "group":"",
                          "children":[] // same as this
                      }
                  ]
             }
                                                                                           