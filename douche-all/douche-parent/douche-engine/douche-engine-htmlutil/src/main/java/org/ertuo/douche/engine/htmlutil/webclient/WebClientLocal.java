package org.ertuo.douche.engine.htmlutil.webclient;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.ertuo.proxy.proxycn.LastProxy;
import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

@Service
public class WebClientLocal {

	private final  Logger log= Logger.getLogger(WebClientLocal.class);

	private static  WebClient webClient = new WebClient();

	private LastProxy lastProxy = new LastProxy();

	static {
		webClient.setJavaScriptEnabled(false);
		webClient.setThrowExceptionOnScriptError(false);
	}

	 
	
	/**
	 * ���������webclient
	 * @param withProxy
	 */
	public WebClientLocal(boolean withProxy){
		if(withProxy){
		this.getProxyWebClient();
		}
	}

	/**
	 * ���һ�����д����webclient
	 * 
	 * @return
	 */
	public WebClient getProxyWebClient() {
		
		if (lastProxy.getCurrentWebProxy() != null) {
			webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER_6,
					lastProxy.getCurrentWebProxy().getUrl(), Integer
							.parseInt(lastProxy.getCurrentWebProxy().getPort()));
			webClient.setJavaScriptEnabled(false);
		}
		return webClient;
	}

	/**
	 * @param url
	 * @return
	 */
	public HtmlPage getHtmlPageByUrl(String url) {
		HtmlPage htmlPage = null;

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

}
