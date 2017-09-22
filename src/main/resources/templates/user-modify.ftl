<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<div style="padding:10px 10px 10px 10px">
    <form id="userEditForm" class="userForm" method="post">
        <table cellpadding="5">
            <tr>
                <td>用户名:</td>
                <td><input class="easyui-textbox" type="text" name="userName" data-options="required:true" style="width: 280px;"></input></td>
            </tr>
            <tr>
                <td>密码:</td>
                <td><input class="easyui-textbox" type="password" name="password" data-options="required:true" style="width: 280px;"></input></td>
            </tr>
            <tr>
                <td>姓名:</td>
                <td><input class="easyui-textbox" name="name" data-options="validType:'length[0,150]',required:true" style="width: 280px;"></input></td>
            </tr>
            <tr>
                <td>年龄:</td>
                <td><input class="easyui-numberbox" type="text" name="age" data-options="min:1,max:100,precision:0,required:true" />
                </td>
            </tr>
            <tr>
                <td>性别:</td>
                <td>
                    <input class="easyui-radio" type="radio" name="sex" value="1" checked="checked"/> 男
                    <input class="easyui-radio" type="radio" name="sex" value="2"/> 女
                </td>
            </tr>
            <tr>
                <td>出生日期:</td>
                <td>
                    <input class="easyui-datebox" type="text" name="birthday" data-options="required:true" />
                </td>
            </tr>
        </table>
        <input type="hidden" name="userParams"/>
        <input type="hidden" name="userParamId"/>
    </form>
    <div style="padding:5px">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重置</a>
    </div>
</div>
<script type="text/javascript">

    /*$(function(){
        var url = location.search;
        //alert(url);
        var index = url.indexOf("?");
        if (index != -1) {    //判断是否有参数
            var str = url.substr(1); //从第一个字符开始 因为第0个是?号 获取所有除问号的所有符串
            var jsonData = str.split("=");   //用等号进行分隔 （因为知道只有一个参数 所以直接用等号进分隔 如果有多个参数 要用&号分隔 再用等号进行分隔）
            var user = $.parseJSON(jsonData.get(1));          //直接弹出第一个参数 （如果有多个参数 还要进行循环的）
        }
    });*/

    function submitForm(){
        if(!$('#itemeEditForm').form('validate')){
            $.messager.alert('提示','表单还未填写完成!');
            return ;
        }

        var paramJson = [];
        $("#userEditForm .params li").each(function(i,e){
            var trs = $(e).find("tr");
            var group = trs.eq(0).text();
            var ps = [];
            for(var i = 1;i<trs.length;i++){
                var tr = trs.eq(i);
                ps.push({
                    "k" : $.trim(tr.find("td").eq(0).find("span").text()),
                    "v" : $.trim(tr.find("input").val())
                });
            }
            paramJson.push({
                "group" : group,
                "params": ps
            });
        });
        paramJson = JSON.stringify(paramJson);

        $("#userEditForm [name=userParams]").val(paramJson);

        //提交到后台的RESTful
        $.ajax({
            type: "POST",
            url: "/user/update",
            data: $("#userEditForm").serialize(),
            success: function(msg){
                $.messager.alert('提示','用户修改成功!','info',function(){
                    $("#userEditWindow").window('close');
                    $("#userList").datagrid("reload");
                    clearForm();
                });
            },
            error: function(){
                $.messager.alert('提示','修改商品失败!');
            }
        });
    }
    function clearForm(){
        $('#content').form('reset');
    }

</script>
</body>
</html>