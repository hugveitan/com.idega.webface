<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="/WEB-INF/tld/webface.tld" prefix="w" %>

<html>
<head>
  <meta HTTP-EQUIV="Content-Type" CONTENT="text/html;CHARSET=iso-8859-1">
  <title>MyFaces - the free JSF Implementation</title>
  <link rel="stylesheet" type="text/css" href="style/webfacestyle.css">
</head>

<body>

<f:view>
	<h:form id="testScreen" name="form1" enctype="multipart/form-data">
	<w:create_article_page id="testpage" />
	</h:form>
</f:view>

</body>
</html>
