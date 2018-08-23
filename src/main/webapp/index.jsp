<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML>
<html>
<head>
<title>BASE DEMO</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript" src="jslib/jquery-easyui-1.5.2/jquery.min.js"></script>
<script type="text/javascript" src="jslib/jquery-easyui-1.5.2/jquery.easyui.min.js"></script>
<script type="text/javascript" src="jslib/jquery-easyui-1.5.2/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="jslib/syUtil.js"></script>
<link rel="stylesheet" href="jslib/jquery-easyui-1.5.2/themes/default/easyui.css" type="text/css"></link>
<link rel="stylesheet" href="jslib/jquery-easyui-1.5.2/themes/icon.css" type="text/css"></link>
<script type="text/javascript">
	function addTab(opts){
		var t = $('#index_centerTabs');
		if(t.tabs('exists',opts.title)){
			t.tabs('select',opts.title);
		}else{
			t.tabs('add',opts);
		}
	
	}
	
	function logout() {
		
		$.messager.confirm('确认', '您确认要退出登录吗？', function(r) {
			if (r) {
				$.ajax({
					url:'${pageContext.request.contextPath}/userAction!logout.do',
					success:function(data){
						var obj = $.parseJSON(data);
				       	if(obj.success){
				    		
				      	}
					    $.messager.show({
					    	title:'提示',
					    	msg:obj.msg
					    });
					}
				});
			}
		});
	}
	
	$(function(){
		<%
		String uname = (String)request.getSession().getAttribute("username");
		%>
		var uname ='<%=uname%>';
		if(uname !='null' && uname.length>0){
			$('#user_login_dialog').dialog('close');
			$('#index-uname').text(uname);
			$('#index-uname').show();
			$('#index-logout').show();
		}
	});
</script>

</head>

<body class="easyui-layout">
	<%-- 上 --${pageContext.request.contextPath} --%>
	<div data-options="region:'north'" style="height: 60px;">
		当前登录用户：<div id="index-uname" style="display:none"></div>
		<div id="index-logout" style="display:none">
  			<a href="javascript:logout()">退出</a>  
  		</div>
	</div>
	<%-- 下 --%>
	<div data-options="region:'south'" style="height: 20px;"></div>
	<%-- 左 --%>
	<div data-options="region:'west'" style="width: 200px">
		<%-- 标题去掉收缩功能，fit控制是否自动计算宽度 --%>
		<div class="easyui-panel" data-options="title:'功能导航',border:false,fit:true">
			<div class="easyui-accordion" data-options="fit:true,border:false">
				<%--异步加载树
				<div title="系统菜单" data-options="iconCls:'icon-save'">
					<ul class="easyui-tree" data-options="url:'${pageContext.request.contextPath}/menuAction!getTreeNode.do',lines:true"></ul>
				</div>
				--%>

				<div title="系统菜单" data-options="iconCls:'icon-save'">
					<ul class="easyui-tree" data-options="url:'${pageContext.request.contextPath}/menuAction!getAllTreeNode.do',parentField:'pid',lines:true,onLoadSuccess:function(node, data){
						$(this).tree('collapseAll');
					},onClick: function(node){
						if(node.attributes.url){
							var url = '${pageContext.request.contextPath}'+node.attributes.url
							addTab({title:node.text,href:url,closable:true});
						}
					}"></ul>
				</div>
				<div title="Title2" data-options="iconCls:'icon-reload'">aaaadsfsdafad</div>
			</div>
		</div>
	</div>
	<%-- 右 --%>
	<div data-options="region:'east',title:'east title',split:true" style="width: 200px"></div>
	<%-- 中:为必须模块 --%>
	<div data-options="region:'center',title:'欢迎使用BASE示例系统'">
		<div id="index_centerTabs" class="easyui-tabs" data-options="fit:true,border:false">
			<div title="首页">adsadsfsdfsdfs</div>
		</div>
	</div>
	<%-- 
     modal模式化使底层不可点击
     closable隐藏右上角关闭按钮
     toolbar表示上边按钮 buttons表示下边按钮
     closed表示初始化隐藏窗口
      --%>

	<jsp:include page="user/login.jsp"></jsp:include>
	<jsp:include page="user/reg.jsp"></jsp:include>

</body>
</html>
