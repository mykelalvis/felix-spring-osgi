package org.ertuo.htmlutil.proxycn;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ertuo.htmlutil.proxycn.domain.WebProxy;
import org.nuxeo.common.xmap.XMap;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

/**
 * ����洢��
 * <p>
 * <li>�ṩ����洢���ṩ��ǰ��Ч����
 * </p>
 * @author mo.duanm
 *
 * 
 */
public class LastProxy  {
	
	private final Log log=LogFactory.getLog(LastProxy.class);

	/**
	 * ��ô����webҳ��
	 */
	private static final String proxyCnUrl = "http://www.proxycn.com/html_proxy/http-1.html";
	
	
	private static final String testUrl="http://www.19lou.com/";


	/**
	 * ���õĴ�����
	 */
	private static List<WebProxy> canUseProxy = new ArrayList<WebProxy>();

	/**
	 * ��ǰ��Ч�Ĵ���,ϵͳ�ڶ�ʹ�������������
	 */
	private WebProxy currentWebProxy;
	
	

	/**
	 * ��ǰ��ʹ���еĴ�������,ÿ��ʹ�ú󣬶�����һ��
	 */
	private static int useId = 0;
	
	
    private static final WebClient webClient = new WebClient();
    
    private XMap xmap=new XMap();
	
	
	static {
		webClient.setJavaScriptEnabled(false);
		webClient.setThrowExceptionOnScriptError(false);
	}

	public void test() {
		this.getCurrentWebProxy();
	}

	private void createCanUseProxy() {
		HtmlPage htmlPage = this.getHtmlPageByUrl(proxyCnUrl);
		NodeList br = htmlPage.getElementsByTagName("tr");
		for (int i = 0; i < br.getLength(); i++) {
			Node node = br.item(i);
			String context = node.getTextContent();
			if (context.contains("whois")) {
				WebProxy webProxy = new WebProxy();
				// ��һ���ڵ�id
				Node firstNode = node.getFirstChild();
				try {
					webProxy.setId(useId
							+ Integer.parseInt(firstNode.getTextContent()));
				} catch (NumberFormatException e) {
					continue;
				}

				// �ڶ����ڵ�ip
				Node secondNode = firstNode.getNextSibling();
				// JavaScriptEngine engine=new JavaScriptEngine(new
				// WebClient());
				// Object o=engine.execute(htmlPage,secondNode.getTextContent(),
				// "aaa", 0);

				// ʹ��һ��������ȡIP ȡ���������Ե������
				String[] ips = secondNode.getTextContent().split("[^1-9||^.]");
				String ip = "";

				for (String _ip : ips) {
					// ƴ������
					ip = ip + _ip;
				}
				// ȥ��ƴ����.�ظ���.��ͷ
				ip = ip.replace("..", ".").replaceFirst(".", "");
				webProxy.setUrl(ip);

				// �������ڵ�port
				Node threeNode = secondNode.getNextSibling();
				webProxy.setPort(threeNode.getTextContent());
				// �������ڵ�checkDate
				Node sixNode = threeNode.getNextSibling().getNextSibling()
						.getNextSibling();
				try {
					// ƴ�ӵ�ǰ���+���ʱ��
					Date date = DateUtils.parseDate((Calendar.getInstance())
							.get(Calendar.YEAR)
							+ "-" + sixNode.getTextContent(),
							new String[] { "yyyy-MM-dd HH:SS" });
					webProxy.setCheckDate(date);
				} catch (DOMException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				//�����Ƿ����
				if(this.getCanUseWebProxy(webProxy)!=null){
					canUseProxy.add(webProxy);
				}
				
			}
		}
        /*if(canUseProxy!=null){
        	WebProxy proxy=new WebProxy();
        	try {
				String webProxyXml=xmap.asXmlString(proxy, "utf-8", null);
				System.out.println(webProxyXml);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }*/
	}

	
	/**
	 * ���Դ���Ĵ����Ƿ����
	 * @param webProxy
	 * @return
	 */
	private WebProxy getCanUseWebProxy(WebProxy webProxy){
		WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER_6,
				webProxy.getUrl(), Integer.parseInt(webProxy
						.getPort()));
		try {
			webClient.setJavaScriptEnabled(false);
			webClient.setTimeout(1000);
			webClient.getPage(testUrl);
			log.info("����["+webProxy.getUrl()+";"+webProxy.getPort()+"]����");
			
		} catch (Exception e) {
			log.error("����["+webProxy.getUrl()+";"+webProxy.getPort()+"]"+e.getMessage());
			return null;
		}
		return webProxy;
		
	}

	public void setCurrentWebProxy(WebProxy currentWebProxy) {
		this.currentWebProxy = currentWebProxy;
	}

	public WebProxy getCurrentWebProxy() {
		//ѭ�������˵�ǰ�洢�Ĵ����ܺͣ�����
		if(useId>=this.getCanUseProxy().size()){
			useId=0;
		}
		currentWebProxy = this.getCanUseProxy().get(useId);
		useId++;
		if (currentWebProxy == null) {
			//ѭ���ص�
			this.getCurrentWebProxy();
		}
		//��ǰ��������
		if(this.getCanUseWebProxy(currentWebProxy)==null){
			//ѭ���ص�
			this.getCurrentWebProxy();
		}
		return currentWebProxy;
	}

	public List<WebProxy> getCanUseProxy() {
		if (canUseProxy.size() == 0) {
			this.createCanUseProxy();
		}
		return canUseProxy;
	}

	public void setCanUseProxy(List<WebProxy> canUseProxy) {
		LastProxy.canUseProxy = canUseProxy;
	}
	
	
	/**
	 * @param url
	 * @return
	 */
	public HtmlPage getHtmlPageByUrl(String url){
		HtmlPage htmlPage=null;
	 
			try {
				htmlPage = (HtmlPage) webClient
						.getPage(url);
			} catch (Exception e) {
				 log.error("�ɼ�ҳ��["+url+"]ʧ��",e);
			}
		return htmlPage;	
			
	}
	
	/**
	 * @param submit
	 * @return
	 */
	public HtmlPage getClickHtmlPage(HtmlSubmitInput submit){
		HtmlPage replys = null;
		try {
			replys = (HtmlPage) submit.click();
		} catch (IOException e) {
			log.error("���ʧ�ܣ�",e);
		}
		
		return replys;
	}

}
