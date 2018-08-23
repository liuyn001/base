<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'jsonTest.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript">
		//请求json返回json
		function requestJson(){
			$.ajax({
				type:'post',
				url:'${pageContext.request.contextPath}/requestJson.action',
				contentType:'application/json;chartset=utf-8',
				data:'{"name":"手机","price":9999}',//商品信息
				success:function(data){//返回结果
					console.info(data);
				}
				
			});
		}
	
		//请求key/value返回json
		function responseJson(){
			$.ajax({
				type:'post',
				url:'${pageContext.request.contextPath}/responseJson.action',
				//contentType:'application/json;chartset=utf-8',默认是key/value格式提交
				data:'name=电脑&price=2222',//商品信息
				success:function(data){//返回结果
					alert(data);
					console.info(data);
				}
				
			});
		}
	</script>

  </head>
  
  <body>
    <input type="button" onclick="requestJson()" value="请求json，返回json"/>
    <input type="button" onclick="responseJson()" value="请求key/value，返回json"/>
  </body>
</html>
