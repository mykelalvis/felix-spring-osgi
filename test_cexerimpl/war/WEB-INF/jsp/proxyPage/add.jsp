<%@ page contentType="text/html;charset=GBK" language="java" isELIgnored="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
	<head>
		<meta name="layout" content="edit" />
		<title>��ӳ���ҳ��</title>
	</head>
	<body>
	<form name="example" method="get" action="add">

							<h2 class="title">
								<label class="label" for="login">��ַ:</label>
								<input type="text" name="url"  size="20" />
							</h2>
							
							<h2 class="title">
								<label class="label" for="login">����:</label>
								<input type="text" name="reg" size="20" />
							</h2>
							<h2 class="title">
								<label class="label" for="login">�ָ���:</label>
								<input type="text" name="spit" size="20" />
							</h2>
								<br />
								<input type="submit" name="button" value="�ύ����" />
								${rs}
							</div>
						</form>
	</body>