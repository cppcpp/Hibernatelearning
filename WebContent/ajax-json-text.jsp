<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
    $(function(){
    	$("#request").click(function(){
    		$.ajax({
    			type:'post',
    			url:"query_checkAllUser.action",
    			dataType:'json',
    			success:function(data){
    				//alert("success");
    				console.log(typeof(data));
    				console.log(data);
    				//json字符串转换成json对象
    				var json=eval(data);
    				var table="<table border='1'>"
    				table+="<tr><td>name</td><td>password</td><td>count</td><td>flag</td></tr>";
    				for(var i=0;i<json.length;i++){
    					table+="<tr>";
    					table+="<td>"+json[i].name+"</td><td>"+json[i].password+"</td><td>"+json[i].count+"</td><td>"+json[i].flag+"</td>";
    					table+="</tr>";
    				}
    				table+="</table>";
    				$("#table").append(table);
    			},
    			error:function(){
    				alert("error");
    			}
    		});
    		
    		
    	})
    	
    	
    });
    
    
</script>

</head>

<body>
	<input type="button" value="请求ajax" id="request">
	<div id="table"></div>
	<div>
		<!-- multipart/form-data 使表单元素以二进制编码的方式提交-->
		<form enctype="multipart/form-data">
		<input type="file" value="请选择要上传文件">
		</form>
	</div>
</body>
</html>
