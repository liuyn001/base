<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">

	$(function(){
		
		$('#user_reg_regForm').form({
			url:'<c:out value="${pageContext.request.contextPath}"/>/userAction!reg.do',
		    success:function(data){
		       var obj = $.parseJSON(data);
		       if(obj.success){
		    	 $('#user_reg_regDialog').dialog('close');  
		       }
		       $.messager.show({
		    		title:'提示',
		    		msg:obj.msg
		    	});
		    }
		});
		
		$('#user_reg_regForm input').bind('keyup', function(event){
			if(event.keyCode=='13'){
				$('#user_reg_regForm').submit();
			}
		});
		
	});

</script>

	<div id="user_reg_regDialog" style="width: 250px" class="easyui-dialog" data-options="title:'注册',closed:true,modal:true,buttons:[{
				text:'注册',
				iconCls:'icon-edit',
				handler:function(){
					<!-- //console.info($('#index_regForm'));//控制台打印 -->
					//$('#index_regForm').submit();//第二种方式初始化表单,提交表单事件
					//regUser();//调用注册方法,可使用form表单的提交事件,或者使用ajax进行异步提交进行注册
					$('#user_reg_regForm').submit();
				}
			}]">
		<form id="user_reg_regForm" method="post" >
			<table>
				<tr>
					<th>登录名</th>
					<td><input name="name" class="easyui-validatebox" data-options="required:true,missingMessage:'登录名称必填'" /></td>
				</tr>
				<tr>
					<th>密码</th>
					<td><input name="password" type="password" class="easyui-validatebox" data-options="required:true,missingMessage:'密码必填'" /></td>
				</tr>
				<tr>
					<th>重复密码</th>
					<td><input name="rePassword" type="password" class="easyui-validatebox" data-options="required:true,validType:'rePassword[\'#user_reg_regForm input[name=password]\']'" /></td>
				</tr>
			</table>
		</form>
	</div>

	<!-- 第二种方式初始化注册窗口 -->
	<div id="index_regDialog2">
		<table>
			<tr>
				<th>登录名</th>
				<td><input /></td>
			</tr>
			<tr>
				<th>密码</th>
				<td><input /></td>
			</tr>
			<tr>
				<th>重复密码</th>
				<td><input /></td>
			</tr>
		</table>
	</div>