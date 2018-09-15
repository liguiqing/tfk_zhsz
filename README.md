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
        调用接口 /wechat/oauth2
        http  method  GET 
             request  params:
                 code    string required
                 status  string
        return 
            {
                "accessToken": "",  //微信授权令,访问需要鉴权接口时用
                "openId": "",       //微信openId
                "wechats":[{         //微信与系统关联信息,未来关联时为空
                    "openId": "",    //微信openId
                    "role": "",      //用户角色[Teacher,Student,Parent],多值以,分隔
                    "personId": "",  //用户唯一标识
                    "name": "", 
                    "phone": ""
                }]
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
                    "role": "",      //用户角色[Teacher,Student,Parent]
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
                  openId  string required 微信openId
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
        如果返回为空,表示没有关注班级

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
                        "grades": null
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
               gradeLevel    string 年级序列,1.4返回值的schools[n].grads[m].level
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
             request  body:
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
                  clazzId    string
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
         调用接口 /assess/teacher/to/student
         http  method  GET  
               request params:
                  schoolId    string required
           teacherPersonId    string required
           studentPersonId    string required            
         return   
            {
                "indexes": [
                    {
                        "alias": "积极举手发言", 
                        "categoryName": "Intelligence", 
                        "children": null, 
                        "description": "", 
                        "group": "", 
                        "indexId": "INXdf18303617d745f7a8faffec293b00d4", 
                        "name": "积极举手发言", 
                        "plus": true, 
                        "score": 10, //最高得分
                        "weight": 0  //权重
                    },
                    {
                        "alias": "未提交作业", 
                        "categoryName": "Intelligence", 
                        "children": null, 
                        "description": "", 
                        "group": "", 
                        "indexId": "INXc793be2278c2496eb6fd2df2879e88e7", 
                        "name": "未提交作业", 
                        "plus": false, 
                        "score": 10, 
                        "weight": 0
                    }
                ], 
                "assessee": { //被评价者,学生
                    "assesseeId": "ASEb581bb2e92a544fea413fb502fe34c15", 
                    "assessorId": "", 
                    "schoolId": "SCH27508814ae724270a63c0ad759baf2ff", 
                    "student": {
                        "clazzes": [
                            {
                                "clazzId": "CLA87ea796aa34b438aad9fb5a232dd3218", 
                                "clazzName": "1班", 
                                "gradeLevel": 1, 
                                "gradeName": "一年级", 
                                "schoolId": "", 
                                "type": "United"
                            }
                        ], 
                        "contacts": [
                            {
                                "name": "电话号码", 
                                "value": "13600001234"
                            }
                        ], 
                        "gender": "", 
                        "gradeLevel": 1, 
                        "gradeName": "一年级", 
                        "name": "赖名建", 
                        "personId": "PER5c8b0bde05ed4c70b4e0f89128b9ad2d", 
                        "schoolId": "SCH27508814ae724270a63c0ad759baf2ff", 
                        "studentId": "STU8810101cc91347aa825d8e57f6a225ed"
                    }, 
                    "teacher": null
                }, 
                "assessor": { //评价者,老师
                    "assesseeId": "", 
                    "assessorId": "ASReb2da8aa398d4c518a3360272e984843", 
                    "schoolId": "SCH27508814ae724270a63c0ad759baf2ff", 
                    "student": null, 
                    "teacher": {
                        "clazzes": [ ], 
                        "contacts": [
                            {
                                "name": "电话号码", 
                                "value": "1390001234"
                            }
                        ], 
                        "name": "姜典来", 
                        "personId": "PER2d71568f7f08455aa1508931019e409d", 
                        "schoolId": "SCH27508814ae724270a63c0ad759baf2ff", 
                        "teacherId": "TEAf015b5642368433ba95dbca81b791b2b"
                    }
                }
            }

### 1.11 老师评价学生
        调用接口 /assess/teacher/to/student
        http  method  POST  
             request body:
                {
                    "schoolId": "", 
                    "teacherPersonId": "", 
                    "studentPersonId": "", 
                    "assesses": [
                        {
                            "indexId": "", 
                            "score": 5
                        }, 
                        {
                            "indexId": "", 
                            "score": -5
                        }, 
                        {
                            "word": ""
                        }
                    ]
                }
             
                {
                    "assesseeId":"", //被评者personId
                    "assessorId":"", //主评者personId
                    "indexId":"", 
                    "score":10,   //有值indexId不能为空
                    "word":"亚马爹"
                }
        return   
            {
                "assessIds": []
            }
            
### 1.12 查询人员某个时段的全部评价
        调用接口 /assess/list/all/{schoolId}/{role}/{personId}
        http  method  GET  
              path params:
                  schoolId    string 
                      role    string value:Teacher;Student;Parent;
                  personId    string
                  
            request params:
                      from    date pattern:yyyy-MM-dd  为null时为本周第一天
                        to    date pattern:yyyy-MM-dd  为null时为本周最后一天
        return   //按doneDate倒序排列
            {
                "assessIes": [
                     {
                         "indexName": "", 
                         "doneDate": "2019-01-01", 
                         "indexScore": 10, 
                         "score": 8, 
                         "word": "亚马爹", 
                         "assesseeName": "张三", 
                         "assesseeId": "", 
                         "clazzId": ""
                     }
                ]
            }
            
### 1.13 查询人员某个时段某category全部评价,时段通过category进行自动计算
        调用接口 /assess/list/category/{schoolId}/{personId}
        http  method  GET  
              path params:
                  schoolId    string 
                  personId    string
                  
            request params:
                  category    string values of: Year:学年;Term:学期;Month:月;,Weekend:周;Day:天; 默认值Day
                      role    string values of: Student:学生;Teacher:教师;Parent:家长; 默认值Student 
        return   //按doneDate倒序排列
            {
                "assesses": [
                    {
                        "indexName": "",
                        "doneDate": "yyyy-MM-dd",
                        "indexScore": 0.0,
                        "score": 0.0,
                        "word": "",
                        "assesseeName": "", //被评价者姓名
                        "assesseeId": "", //被评价者ID
                        "clazzId": ""     //被评价者clazzId
                    }
                ]
            }
            
### 1.14 询个人的班级评价排名
        调用接口 /assess/rank/personal/grade/{schoolId}/{personId}
        http  method  GET  
              path params:
                  schoolId    string 
                  personId    string
                  
            request params:
                  category    string values of: Year:学年;Term:学期;Month:月;,Weekend:周;Day:天; 默认值Day
                     
        return   //按doneDate倒序排列
            {
                "ranks": [{
                	"schoolId": "",
                	"clazzId": "",
                	"personId": "",
                	"assesseeName","" //学生姓名                	
                	"rankScope": "School", //values of Clazz/Grade/School
                	"rankCategory": "Day", //values of Day/Weekend/Month/Term/Year
                	"rankNode": "2018-09-01",
                	"rankDate": "2018-09-01",
                	"score": 0.0,
                	"promoteScore": 0.0,
                	"rank": 10,
                	"promote": 1
                }]                   
            }
            
### 1.15 询个人的年级评价排名
        调用接口 /assess/rank/personal/clazz/{clazzId}/{personId}
        http  method  GET  
              path params:
                   clazzId    string 
                  personId    string
                  
            request params:
                  category    string values of: Year:学年;Term:学期;Month:月;,Weekend:周;Day:天; 默认值Day
                     
        return   //按doneDate倒序排列
            {
                "ranks": [{
                	"schoolId": "",
                	"clazzId": "",
                	"personId": "",
                	"assesseeName","" //学生姓名                	
                	"rankScope": "Clazz", //values of Clazz/Grade/School
                	"rankCategory": "Day", //values of Day/Weekend/Month/Term/Year
                	"rankNode": "2018-09-01",
                	"rankDate": "2018-09-01",
                	"score": 0.0,
                	"promoteScore": 0.0,
                	"rank": 10,
                	"promote": 1
                }]                   
            } 
            
### 1.16 查询当天/本周/本月/本学期/本学年班级所有学生评价排名
        当天调用接口   /assess/rank/clazz/day/{clazzId}
        本周调用接口   /assess/rank/clazz/weekend/{clazzId}
        本月期调用接口 /assess/rank/clazz/month/{clazzId}
        本学期调用接口 /assess/rank/clazz/term/{clazzId}
        本学年调用接口 /assess/rank/clazz/year/{clazzId}
        
        http  method  GET  
              path params:
                   clazzId    string 
                  
            request params:
            showLastIfNone    boolean 如果此值为true,当期无排名时,会返回本年度最后一次排名
                     
        return   //按doneDate倒序排列
            {
                "ranks": [{
                	"schoolId": "",
                	"clazzId": "",
                	"personId": "",
                	"assesseeName","" //学生姓名
                	"rankScope": "Clazz", //values of Clazz/Grade/School
                	"rankCategory": "Day", //values of Day/Weekend/Month/Term/Year
                	"rankNode": "2018-09-01",
                	"rankDate": "2018-09-01",
                	"score": 0.0,
                	"promoteScore": 0.0,
                	"rank": 10,
                	"promote": 1
                }]                   
            }               
            
### 1.17 查询当天/本周/本月/本学期/本学年年级所有学生评价排名
        当天调用接口   /assess/rank/grade/day/{schoolId}
        本周调用接口   /assess/rank/grade/weekend/{schoolId}
        本月期调用接口 /assess/rank/grade/month/{schoolId}
        本学期调用接口 /assess/rank/grade/term/{schoolId}
        本学年调用接口 /assess/rank/grade/year/{schoolId}
        
        http  method  GET  
              path params:
                  schoolId    string 
                  
            request params:
            showLastIfNone    boolean 如果此值为true,当期无排名时,会返回本年度最后一次排名
                     
        return   //按doneDate倒序排列
            {
                "ranks": [{
                	"schoolId": "",
                	"clazzId": "",
                	"personId": "",
                	"rankScope": "Clazz", //values of Clazz/Grade/School
                	"rankCategory": "Day", //values of Day/Weekend/Month/Term/Year
                	"rankNode": "2018-09-01",
                	"rankDate": "2018-09-01",
                	"score": 0.0,        //得分
                	"promoteScore": 0.0, //与上期进退步分数
                	"rank": 10,          //排名
                	"promote": 1         //与上期进退步排名
                }]                   
            }
            
### 1.18 生成学校评价组
        当天调用接口   /assess/team/gen/{schoolId}     
        http  method  GET  
              path params:
                  schoolId    string                                                                                                                                                        