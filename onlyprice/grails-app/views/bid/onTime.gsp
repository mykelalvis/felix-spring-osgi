 <html>
 <head>
        <title>出价吧</title>
        <meta name="layout" content="main" />
 </head>
 					<g:if test="${rs}" 
 						<g:form action="bid" method="post" useToken="true">
 						<input type="hidden" name="goods.id" value="${bid?.goods?.id}" />
						<div class="one-goods" style="width: 150px;">
							<p><a href="${grailsApplication.config.grails.serverURL}/goods/ajaxAdd?numId=${rs.goods.fId}">${rs.goods.title}</a></p>
							<p><span class="one-price"> 淘宝价&nbsp;￥</span><span class="shop-price">${rs.goods.price}</span></p>
							<p>出价:<input name="price" value="${bid?.price}" /></p>
						</div>
						</g:form>
					</g:if>	
					<g:else>
    			 		当前时间段没有竞价产品。
					</g:else>
</html>		  