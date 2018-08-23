<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
//通过js添加按钮
$('#yhgl_table').datagrid({
	toolbar: '#yhgl_button'
});

function add(){
	$('#admin_yhgl_editForm').form('load',{name:'',password:'',rePassword:''});
	$('#admin_yhgl_editDialog').dialog('open').dialog('setTitle','添加');
}

function edit(){
	//$('#admin_yhgl_editForm').form('load',{name:'',password:'',rePassword:''});
	var row = $("#yhgl_table").datagrid("getSelected");
	if(row){
		$('#admin_yhgl_editDialog').dialog('open').dialog('setTitle','编辑');
		//回显数据
		$("#admin_yhgl_editForm").form("load", row);
		//设置密码为空
		$('#admin_yhgl_editForm').form('load',{password:'',rePassword:''})
		$('#admin_yhgl_editForm').form({
			url:'<c:out value="${pageContext.request.contextPath}"/>/userAction!editUser.do',
			onSubmit: function(param){    
				param.userid = row.id;  
		    },
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
	} else {
		$.messager.alert('提示','请选择要编辑的行');
	}
}


function del() {
	var row = $("#yhgl_table").datagrid("getSelected");
	if (row) {
		$.messager.confirm('确认', '您确认想要删除记录吗？', function(r) {
			if (r) {
				$.ajax({
					url:'${pageContext.request.contextPath}/userAction!deleteUser.do',
					data:{'userid':row.id},
					success:function(data){
						var obj = $.parseJSON(data);
				       	if(obj.success){
				    		$('#yhgl_table').datagrid('reload');
				      	}
					    $.messager.show({
					    	title:'提示',
					    	msg:obj.msg
					    });
					}
				});
			}
		});
	} else {
		$.messager.alert('提示', '请选择要删除的行');
	}
}

	/* $('#yhgl_table').datagrid({
	 toolbar: [{
	 iconCls: 'icon-edit',
	 handler: function(){alert('编辑按钮')}
	 },'-',{
	 iconCls: 'icon-help',
	 handler: function(){alert('帮助按钮')}
	 }]
	 }); */
</script>
<table id="yhgl_table" class="easyui-datagrid" style="width:400px;height:250px"  
        data-options="url:'${pageContext.request.contextPath}/userAction!getAllUser.do',
        fitColumns:true,singleSelect:true,fit:true,border:false,pagination:true,rownumbers:true,ctrlSelect:true">   
    <thead>   
        <tr>   
            <th data-options="field:'id',width:100,hidden:true">主键</th>   
            <th data-options="field:'name',width:100">登录名</th>  
            <!-- <th data-options="field:'password',width:100">登录密码</th>  --> 
            <th data-options="field:'createtime',width:100">创建时间</th>   
            <th data-options="field:'updatetime',width:100">更新时间</th> 
        </tr>   
    </thead>   
</table>  
 
 
<div id="yhgl_button">
	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="add()" data-options="iconCls:'icon-help',plain:true"> 添加 </a>
	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="edit()" data-options="iconCls:'icon-edit',plain:true"> 编辑 </a>
	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="del()" data-options="iconCls:'icon-remove',plain:true"> 删除 </a>
	
</div>

<jsp:include page="edit.jsp"></jsp:include>
