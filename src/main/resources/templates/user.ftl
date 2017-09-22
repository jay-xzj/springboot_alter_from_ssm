<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>会员管理系统</title>
    <link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4/themes/icon.css" />
    <script type="text/javascript" src="/js/jquery-easyui-1.4/jquery.min.js"></script>
    <script type="text/javascript" src="/js/jquery-easyui-1.4/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/js/jquery-easyui-1.4/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/js/common2.js"></script>
    <script type="text/javascript" src="/js/json2.js"></script>
</head>
<body>
<div>
    <table id="userList" class="easyui-datagrid"  title="会员列表"
           data-options="singleSelect:false,collapsible:true,pagination:true,url:'/user/list',method:'post',pageSize:5,toolbar:toolbar,pageList:[2,5,10]">
        <thead>
        <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th data-options="field:'id',width:60">ID</th>
            <th data-options="field:'userName',width:200">用户名</th>
            <th data-options="field:'name',width:100">姓名</th>
            <th data-options="field:'age',width:100">年龄</th>
            <th data-options="field:'sex',width:80,align:'right',formatter:formatSet">性别</th>
            <th data-options="field:'birthday',width:80,align:'right',formatter:formatBirthday">出生日期</th>
            <th data-options="field:'created',width:130,align:'center',formatter:formatDate">创建日期</th>
            <th data-options="field:'updated',width:130,align:'center',formatter:formatDate">更新日期</th>
        </tr>
        </thead>
    </table>
</div>
<div id="userAdd" class="easyui-window" title="新增会员"
           data-options="modal:true,closed:true,iconCls:'icon-save',href:'/page/user-add'" style="width:500px;height:390px;padding:10px;">
    The window content.
</div>
<div id="userEditWindow" class="easyui-window" title="修改会员信息"
     data-options="modal:true,closed:true,iconCls:'icon-save',href:'/page/user-modify" style="width:700px;height:490px;padding:20px;">
    The window content.
</div>
<script type="text/javascript">
    function formatDate(val,row){
        var now = new Date(val);
        return now.format("yyyy-MM-dd hh:mm:ss");
    }
    function formatBirthday(val,row){
        var now = new Date(val);
        return now.format("yyyy-MM-dd");
    }
    function formatSet(val,row){
        if(val == 1){
            return "男";
        }else if(val == 2){
            return "女";
        }else{
            return "未知";
        }
    }
    function getSelectionsIds(){
        var userList = $("#userList");
        var sels = userList.datagrid("getSelections");
        var ids = [];
        for(var i in sels){
            ids.push(sels[i].id);
        }
        ids = ids.join(",");
        return ids;
    }

    function getSelectedUsers(){
        var userList = $("#userList");
        var user = userList.datagrid("getSelected");
        return user;
    }

    var toolbar = [{
        text:'新增',
        iconCls:'icon-add',
        handler:function(){
            $('#userAdd').window('open');
        }
    },{
        text:'编辑',
        iconCls:'icon-edit',
        handler:function(){
            var ids = getSelectionsIds();
            if(ids.length == 0){
                $.messager.alert('提示','必须选择一条记录才能编辑!');
                return ;
            }
            if(ids.indexOf(',') > 0){
                $.messager.alert('提示','只能选择一条记录!');
                return ;
            }

            $("#userEditWindow").window({
                onLoad :function(){//当页面加载完成之后执行回显
                    //回显数据
                    var data = $("#itemList").datagrid("getSelections")[0];
                    $("#userEditForm").form("load",data);

                    //。。。。
                    $.getJSON('/user/query/'+data.id,function(_data){
                        if(_data && _data.status == 200 && _data.data && _data.data.paramData){
                            $("#userEditForm .params").show();
                            $("#userEditForm [name=userParams]").val(_data.data.paramData);
                            $("#userEditForm [name=userParamId]").val(_data.data.id);

                            //回显商品规格
                            var paramData = JSON.parse(_data.data.paramData);

                            var html = "<ul>";
                            for(var i in paramData){
                                var pd = paramData[i];
                                html+="<li><table>";
                                html+="<tr><td colspan=\"2\" class=\"group\">"+pd.group+"</td></tr>";

                                for(var j in pd.params){
                                    var ps = pd.params[j];
                                    html+="<tr><td class=\"param\"><span>"+ps.k+"</span>: </td><td><input autocomplete=\"off\" type=\"text\" value='"+ps.v+"'/></td></tr>";
                                }

                                html+="</li></table>";
                            }
                            html+= "</ul>";
                            $("#userEditForm .params td").eq(1).html(html);
                        }
                    });

                }
            }).window("open");
        }
    },{
        text:'删除',
        iconCls:'icon-cancel',
        handler:function(){
            var ids = getSelectionsIds();
            if(ids.length == 0){
                $.messager.alert('提示','未选中用户!');
                return ;
            }
            $.messager.confirm('确认','确定删除ID为 '+ids+' 的会员吗？',function(r){
                if (r){
                    $.post("/user/delete",{'ids':ids}, function(data){
                        console.log("data : "+data.status);
//                        data = "["+data+"]";
                        if(data.status == 200){
                            $.messager.alert('提示','删除会员成功!',undefined,function(){
                                $("#userList").datagrid("reload");
                            });
                        }else if(data.status == 505){
                            $.messager.alert('提示','删除会员部分成功!',undefined,function(){
                                $("#userList").datagrid("reload");
                            });
                        }else{
                            $.messager.alert('提示','删除会员失败!',undefined,function(){
                                $("#userList").datagrid("reload");
                            });
                        }
                    });
                }
            });
        }
    },'-',{
        text:'导出',
        iconCls:'icon-remove',
        handler:function(){
            // 获取当前页的分页信息
            var optins = $("#userList").datagrid("getPager").data("pagination").options;
            var page = optins.pageNumber;
            var rows = optins.pageSize;

            // 生成一个Form表单，把分页参数信息提交到后台服务器
            $("<form>").attr({
                "action":"/user/export/excel",
                "method":"POST"
            }).append("<input type='text' name='page' value='"+page+"'/>")
                    .append("<input type='text' name='rows' value='"+rows+"'/>").submit();
        }
    }];
</script>
</body>
</html>