<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<html>
  <head>
    <title><tiles:getAsString name="title"/></title>
  </head>
  <body>
        <table width="100%">
      <tr style="height: 100px;background-color: red;">
        <td colspan="2" style="text-align: center;">
          <tiles:insertAttribute name="header" />
        </td>
      </tr>
      <tr>
        <td width="20%" style="border-right: 1px solid #CCC;">
          <tiles:insertAttribute name="menu" />
        </td>
        <td style="text-align: center;">
          <tiles:insertAttribute name="body" />
        </td>
      </tr>
      <tr style="height: 100px;background-color: yellow;">
        <td colspan="2" style="text-align: center;">
          <tiles:insertAttribute name="footer" />
        </td>
      </tr>
    </table>
  </body>
</html>