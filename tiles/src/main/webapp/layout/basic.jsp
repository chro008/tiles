<%@ page language="java" contentType="text/html; charset=gbk"
	pageEncoding="utf-8"%>

<%@ taglib prefix="tiles" uri="http://struts.apache.org/tags-tiles"%>

<!DOCTYPE>


<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>123</title>
<style>
div {
	width: 500px;
	height: 50px;
	background: yellow;
}

#body {
	background: aqua;
}
</style>
</head>
	<body>
		<div id="header">
			<tiles:insert name="header" />
		</div>
		<div id="body">
			<tiles:insert name="body" />
		</div>
		<div id="footer">
			<tiles:insert name="footer" />
		</div>
	</body>
<script type="text/javascript">
	document.writeln("words wrote by script.jsp");
</script>
</html>