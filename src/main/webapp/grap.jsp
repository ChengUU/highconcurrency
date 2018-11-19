<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>Title</title>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.0.js"></script>

    <script type="text/javascript">
        $(document).ready(function(){
            // 模拟30000个异步请求, 进行并发
            var max=30000;
            for(var i=1;i<=max;i++){
                // jQuery POST请求,请注意这是异步请求
                $.post({
                    //请求id为1的红包
                    //url:"./userRedPacket/grapRedPacket.do?redPacketId=1&userId="+i,
                    url:"./userRedPacket/grapRedPacketForVersion.do?redPacketId=1&userId="+i,
                    success:function(result){}
                });
            }
        });
    </script>
</head>
<body>

</body>
</html>
