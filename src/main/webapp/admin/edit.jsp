<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">

	$(function(){
		
		$('#admin_yhgl_editForm').form({
			url:'<c:out value="${pageContext.request.contextPath}"/>/userAction!reg.do',
		    success:function(data){
		       var obj = $.parseJSON(data);
		       if(obj.success){
		    	 $('#admin_yhgl_editDialog').dialog('close');  
		    	 $('#yhgl_table').datagrid('reload');
		       }
		       $.messager.show({
		    		title:'提示',
		    		msg:obj.msg
		    	});
		    }
		});
		
		$('#admin_yhgl_editForm input').bind('keyup', function(event){
			if(event.keyCode=='13'){
				$('#admin_yhgl_editForm').submit();
			}
		});
		
	});

</script>

	<div id="admin_yhgl_editDialog" style="width: 250px" class="easyui-dialog" data-options="title:'添加',closed:true,modal:true,buttons:[{
				text:'保存',
				iconCls:'icon-save',
				handler:function(){
					$('#admin_yhgl_editForm').submit();
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#admin_yhgl_editDialog').dialog('close'); 
				}
			}]">
		<form id="admin_yhgl_editForm" method="post" >
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