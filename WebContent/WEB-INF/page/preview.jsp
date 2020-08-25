<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<title>FilePreview test</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<script type="text/javascript" src="${request.contextPath}/static/js/pdf.js"></script>
</head>
<body>

	<div id="example1"></div>
	<!-- <iframe src='https://view.officeapps.live.com/op/view.aspx?src=http://47.112.221.30:8080/environmental_protection/upload/productionPlan/f744bdd6e1954f86ba143f4591f80d7d.xls' width='100%' height='100%' frameborder='1'></iframe>
     -->
</body>
<script type="text/javascript">
	var options = {
		height : "900px",
		width : "800px",
		pdfOpenParams : {
			view : 'FitV',
			page : '0'
		},
		name : "mans",
		fallbackLink : "<p>您的浏览器暂不支持此pdf，请下载最新的浏览器</p>"
	};
	var url = 'http://47.112.221.30:8080/environmental_protection/upload/productionPlan/TCPIP.pdf';
	PDFObject.embed(url, "#example1", options);
</script>
</html>
