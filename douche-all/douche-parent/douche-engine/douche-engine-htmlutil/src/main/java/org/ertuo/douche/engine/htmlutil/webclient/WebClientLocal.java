package org.ertuo.douche.engine.htmlutil.webclient;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;



/**
 * 
 * �������ӣ����д���
 * @author mo.duanm
 *
 */
public interface WebClientLocal  {
	static  WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER_7);
	 
 
	/**
	 * ���һ�����д����webclient
	 * 
	 * @return
	 */
	public WebClient getProxyWebClient();
	
	/**
	 * ����url����htmlҳ��
	 * @param url
	 * @return ���ʧ�� ����null
	 */
	public HtmlPage getHtmlPageByUrl(String url);
	
	
	/**
	 * ����һ����ť�ύ���ҳ��
	 * @param submit �ύ�����İ�ť
	 * @return ���ʧ�� ����null
	 */
	public HtmlPage getClickHtmlPage(HtmlSubmitInput submit);
	 

}
