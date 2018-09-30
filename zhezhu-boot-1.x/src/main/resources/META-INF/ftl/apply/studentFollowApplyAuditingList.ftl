<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <title>友师优评</title>
    <link rel="stylesheet" href="https://www.tfkclass.com/wxweb/css/index.css">
</head>
<body>
    <header>
        <div class="container">
            <div class="logo">
                <a href="#"><img src="https://www.tfkclass.com/wxweb/img/logo_header.png" alt="logo"></a>
                <a href="javascript:void(0)" class="menu" id='nav-menu'></a>
            </div>
            <ul>
                <li><a href="${request.contextPath}/apply/all/auditing/1/100" >审核班级申请</a></li>
                <li><a href="#" class="active">审核学生申请</a></li>
            </ul>
        </div>
    </header>
    <div class="container2 mgt20">
        <div class="container-box">
            <div class="box-tit">待审核的学生申请</div>
            <div class="box-content" id="clazzApply">
            <#if followers??>
                <#list followers as follower>
                <div class="item" id="${follower.applyId!""}">
                    <div>
                        <p class="name">${follower.applierName!""}
                            <small ><i class="icon-phone"></i>${follower.applierPhone!""}</small>
                            <small >申请</small>
                        </p>
                        <p class="school">${follower.schoolName!""}</p>
                        <p class="grads">${follower.gradeName!""}<span>${follower.clazzName!""}</span><span>${follower.name!""}</span<span>${follower.studentNo!""}</span</p>
                    </div>
                    <div class="btn-group">
                        <#if follower.canBeOk>
                        <a href="#" class="btn_primary" data-ok="true" data-applyId="${follower.applyId!""}"
                           data-applierId="${follower.applierId!""}" >通过</a>
                        <#else>
                        <i class="t_red">学号不匹配</i>
                        </#if>
                        <a href="#" data-ok="false" data-applyId="${follower.applyId!""}" data-applierId="${follower.applierId!""}">不通过</a>
                    </div>
                </div>
                </#list>
            </#if>
            </div>
        </div>
    </div>
    <script src="https://cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
    <script>
        var contextPath = '${request.contextPath}';
        $(function () {
            $('#nav-menu').on('click', function () {
                $('.container ul').slideToggle();
            })
            $('#clazzApply a').on('click', function () {
                console.log(this.dataset.appyId);
                var data = this.dataset;
                $.ajax({
                    type: 'POST',
                    dataType: "json",
                    contentType: "application/json",
                    url: contextPath + '/audit/school',
                    data: JSON.stringify(data),
                    success: function (res) {
                        console.log(res);
                    }
                });
            })
        })

    </script>
</body>
</html>