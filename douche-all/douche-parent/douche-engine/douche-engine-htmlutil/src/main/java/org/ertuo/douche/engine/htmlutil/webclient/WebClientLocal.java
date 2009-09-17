package org.ertuo.douche.engine.htmlutil.webclient;

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
	 
 
	/**
	 * ���һ�����д����webclient
	 * 
	 * @return
	 */
	public WebClient getProxyWebClient();
	
	/**
	 * @param url
	 * @return ���ʧ�� ����null
	 */
	public HtmlPage getHtmlPageByUrl(String url);
	
	
	/**
	 * @param submit
	 * @return
	 */
	public HtmlPage getClickHtmlPage(HtmlSubmitInput submit);
	 

}
