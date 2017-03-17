<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<head>
<title><tiles:getAsString name="title" /></title>
</head>
<body>
	<table width="100%">
		<tr style="height: 100px;background-color: red;">
			<td style="text-align: center;"><tiles:insertAttribute
					name="header" /></td>
		</tr>
		<tr>
			<td style="text-align: center;"><tiles:insertAttribute
					name="body" /></td>
		</tr>

		<tr>
			<td style="text-align: center; background-color: gray;"><tiles:insertAttribute
					name="footer" /></td>
		</tr>
	</table>
</body>
</html>