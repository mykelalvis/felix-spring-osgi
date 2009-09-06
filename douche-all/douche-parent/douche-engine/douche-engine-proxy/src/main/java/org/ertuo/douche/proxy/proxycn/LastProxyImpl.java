package org.ertuo.douche.proxy.proxycn;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ertuo.douche.dao.domain.WebProxyDo;
import org.ertuo.douche.dao.opration.ProxyCnDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.thoughtworks.xstream.XStream;

@Service("lastProxy")
public class LastProxyImpl implements LastProxy {

	private final Log log = LogFactory.getLog(LastProxyImpl.class);

	@Autowired
	private ProxyCnDao proxyCnDao;

	/**
	 * ��ô����webҳ��
	 */
	private static final String[] proxyCnUrl = new String[] {
			"http://www.proxycn.com/html_proxy/30fastproxy-1.html",
			"http://www.proxycn.com/html_proxy/http-1.html",
			"http://fast.proxycn.com/proxy30/page1.htm",
			"http://www.proxycn.com/html_proxy/countryDX-1.html" };

	private static final String testUrl = "http://www.19lou.com/passportlogin.php?action=login";

	private final static String ip_reg = "[0-9]\\d{0,2}\\.[0-9]\\d{0,2}\\.[0-9]\\d{0,2}\\.[0-9]\\d{0,2}";

	/**
	 * ���õĴ����� key=ip:port
	 */
	private static Map<String, WebProxyDo> canUseProxy = new HashMap<String, WebProxyDo>();

	/**
	 * ��ǰ��Ч�Ĵ���,ϵͳ�ڶ�ʹ�������������
	 */
	private WebProxyDo currentWebProxy;

	/**
	 * ��ǰ��ʹ���еĴ�������,ÿ��ʹ�ú󣬶�����һ��
	 */
	//private static int useId = 0;

	private static final WebClient webClient = new WebClient();

	static {
		webClient.setJavaScriptEnabled(true);
		webClient.setThrowExceptionOnScriptError(false);
	}

	public void createCanUseProxy() {
		for (String proxy_url : proxyCnUrl) {

			HtmlPage htmlPage = this.getHtmlPageByUrl(proxy_url);
			if(htmlPage==null){
				continue;
			}
			NodeList tds = htmlPage.getElementsByTagName("td");
			for (int i = 0; i < tds.getLength(); i++) {
				Node node = tds.item(i);
				String context = node.getTextContent();
				String ip = this.getIpByReg(context, ip_reg);

				if (StringUtils.isNotBlank(ip)) {
					WebProxyDo webProxy = new WebProxyDo();
					// ��һ���ڵ�id
					/*Node firstNode = node.getPreviousSibling();
					try {
						webProxy.setId(useId
								+ Integer.parseInt(firstNode.getTextContent()));
					} catch (NumberFormatException e) {
						continue;
					}*/

					// �ڶ����ڵ�
					webProxy.setUrl(ip);

					// �������ڵ�port
					Node threeNode = node.getNextSibling();
					String port=threeNode.getTextContent();
					if(StringUtils.isNotBlank(port)&&StringUtils.isNumeric(port)){
						webProxy.setPort(Integer.parseInt(port));
					}else{
						continue;
					}
					
					/*// �������ڵ�checkDate
					Node sixNode = threeNode.getNextSibling().getNextSibling()
							.getNextSibling();
					try {
						// ƴ�ӵ�ǰ���+���ʱ��
						Date date = DateUtils.parseDate(
								(Calendar.getInstance()).get(Calendar.YEAR)
										+ "-" + sixNode.getTextContent(),
								new String[] { "yyyy-MM-dd HH:SS" });
						webProxy.setCheckDate(date);
					} catch (DOMException e) {
						e.printStackTrace();
					} catch (ParseException e) {
						e.printStackTrace();
					}*/
					// �����Ƿ����
					if (this.getCanUseWebProxy(webProxy) != null) {
						canUseProxy.put(webProxy.getUrl() + ":"
								+ webProxy.getPort(), webProxy);
					}

				}
			}
		}
		if (canUseProxy != null) {
			proxyCnDao.createProxy(canUseProxy);
		}
	}

	/**
	 * ���Դ���Ĵ����Ƿ����
	 * 
	 * @param webProxy
	 * @return
	 */
	private WebProxyDo getCanUseWebProxy(WebProxyDo webProxy) {
		WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER_6,
				webProxy.getUrl(), webProxy.getPort());
		try {
			webClient.setJavaScriptEnabled(false);
			webClient.setTimeout(1000);
			webClient.getPage(testUrl);
			log.info("����[" + webProxy.getUrl() + ":" + webProxy.getPort()
					+ "]����");

		} catch (Exception e) {
			log.error("����[" + webProxy.getUrl() + ":" + webProxy.getPort()
					+ "]" + e.getMessage());
			return null;
		}
		return webProxy;

	}

	public WebProxyDo getCurrentInvaidProxy() {
		// ѭ�������˵�ǰ�洢�Ĵ����ܺͣ�����
		Map<String,WebProxyDo> webProxys=this.getCanUseProxy();
		if(webProxys.size()==0){
			return null;
		}
		int size=webProxys.size();
		Random random=new Random();
		//�����
		int select=random.nextInt(size); 
		int i=0;
		Iterator<Entry<String, WebProxyDo>> it=webProxys.entrySet().iterator();
		//�ӵ�ǰmap�����ȡһ������
		while(it.hasNext()){
			currentWebProxy=it.next().getValue();
			if(i==select){
				break;
			}
			i++;
		}
        
		 
		if (currentWebProxy == null) {
			// ѭ���ص�
			this.getCurrentInvaidProxy();
		}
		// ��ǰ��������
		if (this.getCanUseWebProxy(currentWebProxy) == null) {
			//��ǰ����Ѿ�ʧЧ�ˣ������
			proxyCnDao.removePeoxy(currentWebProxy);
			log.info(currentWebProxy.toString()+"�Ѿ�ʧЧ,�����");
			// ѭ���ص�
			this.getCurrentInvaidProxy();
		}
		return currentWebProxy;
	}

	private Map<String,WebProxyDo> getCanUseProxy() {
		return proxyCnDao.getInvailProxys();
	}

	/**
	 * @param url
	 * @return
	 */
	private HtmlPage getHtmlPageByUrl(String url) {
		HtmlPage htmlPage = null;

		try {
			htmlPage = (HtmlPage) webClient.getPage(url);
		} catch (Exception e) {
			log.error("�ɼ�ҳ��[" + url + "]ʧ��", e);
			return null;
		}
		return htmlPage;

	}

	/**
	 * @param submit
	 * @return
	 */
	@SuppressWarnings("unused")
	private HtmlPage getClickHtmlPage(HtmlSubmitInput submit) {
		HtmlPage replys = null;
		try {
			replys = (HtmlPage) submit.click();
		} catch (IOException e) {
			log.error("���ʧ�ܣ�", e);
		}

		return replys;
	}

	/**
	 * ����һ���ַ��кʹ�������ƥ����ַ�
	 * 
	 * @param text
	 *            ��Ҫ�����ַ�
	 * @param reg
	 *            ������ʽ
	 * @return ƥ����ַ�
	 */
	private String getIpByReg(String text, String reg) {
		String ip = null;
		Pattern p = Pattern.compile(reg, Pattern.DOTALL);
		Matcher m = p.matcher(text);
		while (m.find()) {
			ip = m.group();
		}
		return ip;
	}

}
