<%@ page contentType="text/html;charset=GBK" language="java" isELIgnored="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
	<head>
		<meta name="layout" content="edit" />
		<title>ˢ�Ա�������������������������</title>
	</head>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.3.1.min.js"></script> 
    <script type="text/javascript" src="/js/jquery.timers-1.1.2.js"></script> 
	<script>
	
    function xpaths(){
   		var url=$('#url').attr('value');
    	var xpath=$('#xpath').attr('value');
        $.get("search/xpath",{url:url,xpath:xpath}, function(data){
  			//$("#"+id).attr('innerHTML',data);
  			if(data!='û���ҵ���Ʒ'){
  					//$('#rs').append('�ҵ���Ʒ-'+data);
  					$('#rs').append('</br>��ʼ���ģģ��')
  					xishuashua(data);
  			}else{
  					$('#rs').append('</br>û���ҵ���Ʒ�������url���߹ؼ���')
  			}
    	}); 
   	 }
   	 
   	 
   function xishuashua(key){
   		var url=$('#url').attr('value');
    	var xpath=$('#xpath').attr('value');
		$("title").everyTime(25000,function(i) {
		    $.get("search/click",{url:url,xpath:xpath,key:key}, function(data){
  			if(data!='û���ҵ���Ʒ'){
  				$('#rs').append('</br>'+i+data)
  			}
    	});   
		});
	}
	</script>

	<body>
		<!-- end #header -->
		<!-- end #header-wrapper -->
		<div><a href="http://t.sina.com.cn/1663655120?s=6uyXnP" target="_blank"><img border="0" src="http://service.t.sina.com.cn/widget/qmd/1663655120/75d27faf/1.png"/></a></div>
							<form name="example" method="get" action="xpath">

							<h2 class="title">
								<label class="label" for="login">������ַ:</label>
								<input type="text" id="url" name="url" value="${url}" size="90" />
							</h2>
							<h2 class="title">
								<label class="label" for="login">�ؼ���:</label>
								<input type="text" id="xpath" name="xpath" value="${xpath}" size="90" />
							</h2>
								<br />
								<input type="button" name="button" value="ϲ��ϲ��" onclick="xpaths()"/>
							</div>
						</form>
			<div id="rs">
			 
		</div>
	
	
	</body>
