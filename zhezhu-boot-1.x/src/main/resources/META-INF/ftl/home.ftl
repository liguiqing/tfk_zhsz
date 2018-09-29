<div >
    <h5>Hello Babya</h5>
</div>
<script src="https://cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
<script>
    var userName = '${(Shiro.getUser().getUserName())!"admin"}';
    if(userName == 'admin'){
        window.location.href='${request.contextPath}/apply/all/auditing/1/100'
    } 
</script>
