package org.ertuo.douche.engine.htmlutil.webclient;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.ertuo.douche.dao.domain.WebProxyDo;
import org.ertuo.douche.db.hsql.HSQLServer;
import org.ertuo.douche.proxy.proxycn.CnProxyManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.RefreshHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;

@Service
public class WebClientLocal implements InitializingBean{
	//�������һ������û��ҵ������
	//��Ϊ��HSQLServer��ʼ����ʱ����Լ��ĳ�ʼ����˳���ͻ��
	//���������������Ա�֤HSQLServer��������
	@Autowired
	private HSQLServer server;

	private final  Logger log= Logger.getLogger(WebClientLocal.class);

	private static  WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3);

	@Autowired
	private CnProxyManager cnProxyManager ;

	static {
		webClient.setJavaScriptEnabled(false);
		//webClient.setThrowExceptionOnScriptError(false);
		//webClient.setThrowExceptionOnFailingStatusCode(false);
		webClient.setCssEnabled(false);
		webClient.setActiveXNative(false);
		webClient.setAppletEnabled(false);
		webClient.setCookiesEnabled(false);
		webClient.setPopupBlockerEnabled(false);
		webClient.setRedirectEnabled(false);
		webClient.setIgnoreOutsideContent(false);
		webClient.setJavaScriptEngine(new JavaScriptEngine(webClient));
	}

	 
	
	/**
	 * ���������webclient
	 * @param withProxy
	 */
	public WebClientLocal(){
	}

	/**
	 * ���һ�����д����webclient
	 * 
	 * @return
	 */
	public WebClient getProxyWebClient() {
		
		if (cnProxyManager.getCurrentInvaidProxy() != null) {
			webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER_6,
					cnProxyManager.getCurrentInvaidProxy().getUrl(),cnProxyManager.getCurrentInvaidProxy().getPort());
			webClient.setJavaScriptEnabled(false);
		}
		return webClient;
	}

	/**
	 * @param url
	 * @return ���ʧ�� ����null
	 */
	public HtmlPage getHtmlPageByUrl(String url) {
		HtmlPage htmlPage =null;

		try {
			htmlPage = (HtmlPage) webClient.getPage(url);
		} catch (Exception e) {
			log.error("�ɼ�ҳ��[" + url + "]ʧ��", e);
		}
		return htmlPage;

	}

	/**
	 * @param submit
	 * @return
	 */
	public HtmlPage getClickHtmlPage(HtmlSubmitInput submit) {
		HtmlPage replys = null;
		try {
			replys = (HtmlPage) submit.click();
		} catch (IOException e) {
			log.error("���ʧ�ܣ�", e);
		}

		return replys;
	}

	public void afterPropertiesSet() throws Exception {
		WebProxyDo webProxyDo=cnProxyManager.getCurrentInvaidProxy();
		if ( webProxyDo!= null) {
			webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER_6,
					webProxyDo.getUrl(), webProxyDo.getPort());
			webClient.setJavaScriptEnabled(false);
		}
	}

 

	 

}
