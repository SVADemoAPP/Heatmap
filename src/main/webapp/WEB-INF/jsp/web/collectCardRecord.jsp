<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="../shared/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>集卡记录</title>
<link href="<c:url value='/plugins/data-tables/media/css/demo_table.css'/>" rel="stylesheet" type="text/css" />
<style>
.table{
    width:96%;
    margin:20px 50px;
}

</style>
</head>
<body style="background-color:#fff6d9;">
	<div class="tab-head">
           	<input id="prize" type="button" value="奖品领取清单">
           	<input id="card" type="button" value="集齐福卡人员名单">
     </div>
	<div id="c1" style="display: block;">
   		<div style="margin:10px 10px;height:100px;text-align:center;font-weight:bold;font-size:30px;">奖品领取清单</div>
        <table style="text-align:center;margin:20px 20px;" border="1" id="prizeTable" class="table">
            <thead>
                <tr>
                    <th></th>
                    <th>姓名</th>
                    <th>工号</th>
                    <th>奖品级别</th>
                    <th>是否领取</th>
                    <th>操作</th>
                </tr>
            </thead>
        </table>
   	</div>
   <div id="c2" style="display:none;">
    	<div style="margin:10px 10px;height:100px;text-align:center;font-weight:bold;font-size:30px;">集齐福卡人员名单</div>
        <table style="text-align:center" border="1" id="cardTtable" class="table">
            <thead>
                <tr>
                    <th></th>
                    <th>姓名</th>
                    <th>工号</th>
                    <th>"燃"卡数量</th>
                    <th>"情"卡数量</th>
                    <th>"小"卡数量</th>
                    <th>"站"卡数量</th>
                    <th>是否合成</th>
                </tr>
        </table>
    </div>
<script src="<c:url value='/plugins/jquery.js'/>" type="text/javascript"></script>
<script src="<c:url value='/plugins/data-tables/media/js/jquery.dataTables.js'/>"type="text/javascript"></script>
<script src="<c:url value='/js/util.js'/>" type="text/javascript"></script>
<script src="<c:url value='/js/web/collectRecord.js'/>" type="text/javascript">
</script>
</body>
</html>