<%@ page contentType="text/html;charset=GBK" language="java" isELIgnored="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
	<head>
		<meta name="layout" content="edit" />
		<title>�ֻ������б�</title>
	</head>

	<body>
		<!-- end #header -->
		<!-- end #header-wrapper -->
							<form name="example" method="post" action="list">

							<h2 class="title">
								<label class="label" for="login">ʱ��:</label>
								<input type="text" name="date" value="20100915" size="20" />
							</h2>
							
							<h2 class="title">
								<label class="label" for="login">����:</label>
								<input type="text" name="city"
									value="�人" size="20" />
							</h2>
								<br />
								<input type="submit" name="button" value="�ύ����" />
							</div>
						</form>
		<!-- end #page -->

		<!-- end #footer -->
		<div align="center"><span style="font-family: ����_gb2312; color: black; font-size: 16pt;"><span style="font-family: ����ϸ��; font-size: 16pt;"><strong><font size="7" face="����" color="#ff0000" style="background-color: rgb(255, 255, 0);">${updateTime}����</font></strong></span></span></div>
		<div align="center"><span style="font-family: ����_gb2312; color: black; font-size: 16pt;"><span style="font-family: ����ϸ��; font-size: 16pt;"><strong><font size="6" face="����" color="#ff00ff" style="background-color: rgb(255, 255, 0);">�����ڸ��£������ע��</font></strong></span></span></div>
		
		<c:forEach items="${sellNums}" var="num">
		<p style="margin-top: 0pt; margin-bottom: 0pt; margin-left: 54pt;">
			${num.display}
			<span style="font-family: times new roman; font-size: 22pt;">${num.display}</span>
			<span style="font-family: times new roman; font-size: 22pt;">&nbsp;Ԥ���</span>
			<span style="font-family: times new roman; color: rgb(255, 0, 0); font-size: 22pt;">${num.phoneNum.prise}</span>
			<span style="font-family: times new roman; font-size: 22pt;"></span>
		</p>
		</c:forEach>	
		
		
		<div align="center"><span style="font-family: ����_gb2312; color: black; font-size: 16pt;"><span style="font-family: ����ϸ��; font-size: 16pt;"><font size="6" face="����" color="#ff0000">������ԥʲô������</font></span></span></div>
		<div align="center"><span style="font-family: ����_gb2312; color: black; font-size: 16pt;"><span style="font-family: ����ϸ��; font-size: 16pt;"><font size="6" face="����" color="#ff0000"></font></span></span>&nbsp;</div>
		<div align="left"><span style="font-family: ����_gb2312; color: black; font-size: 16pt;"><span style="font-family: ����ϸ��; font-size: 16pt;"><font size="5"><font color="#ff0000"><font size="6"><font face="����">��ͳ��3G������ѡ���Լ���������Ӫҵ����ǧҳ���з��ң���ʱ�����������ۻ����һ���һ������ѡ������ĺŶΣ���������3G�����Ϊ��ͨ����Ӫҵ�����պ��еľ�Ʒ�ŶΣ����ڱ�����á�������ơ�Ϊ��������ѡ����ͨ�еľ�Ʒ����Ʒ�еļ�Ʒ�����������ɼ��������3G����������������������</font></font></font></font></span></span></div>
	</div>
	</body>
