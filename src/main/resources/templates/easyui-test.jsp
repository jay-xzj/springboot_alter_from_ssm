<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>easyui-test</title>
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4/themes/icon.css" />
<script type="text/javascript" src="/js/jquery-easyui-1.4/jquery.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4/locale/easyui-lang-zh_CN.js"></script>
</head>
<body class="easyui-layout">   
    <div data-options="region:'north',title:'重要事项',split:true" style="height:100px;"></div>   
    <div data-options="region:'south',title:'其他',split:true" style="height:100px;"></div>   
    <div data-options="region:'east',iconCls:'icon-reload',title:'工具栏',split:true" style="width:100px;"></div>   
    <div data-options="region:'west',title:'菜单',split:true" style="width:100px;">
    
    <ul  class="easyui-tree" data-options="url:'/js/tree.json'"></ul>  
    		
    </div>   
    <div data-options="region:'center',title:'内容表单'" style="padding:5px;background:#eee;"></div>   
</body>  
</html>
