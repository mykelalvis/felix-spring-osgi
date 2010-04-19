package org.ertuo.douche.proxy.proxycn.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ertuo.douche.dao.domain.WebProxyDo;
import org.ertuo.douche.dao.opration.Repository;
import org.ertuo.douche.proxy.proxycn.CnProxyManager;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.google.inject.Inject;

public class CnProxyManagerImpl implements CnProxyManager {

	private final Log log = LogFactory.getLog(CnProxyManagerImpl.class);

	@Inject
	private Repository<WebProxyDo> proxyCnDao;

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
	 * ��ǰ��Ч�Ĵ���,ϵͳ�ڶ�ʹ�������������
	 */
	private WebProxyDo currentWebProxy;

	private static final WebClient webClient = new WebClient();

	static {
		webClient.setJavaScriptEnabled(true);
		webClient.setThrowExceptionOnScriptError(false);
	}

	public void createCanUseProxy() {

		for (String proxy_url : proxyCnUrl) {

			HtmlPage htmlPage = this.getHtmlPageByUrl(proxy_url);
			if (htmlPage == null) {
				continue;
			}
			List<HtmlElement> list = htmlPage.getElementsByTagName("td");
			for (HtmlElement node : list) {
				String context = node.getTextContent();
				String ip = this.getIpByReg(context, ip_reg);

				if (StringUtils.isNotBlank(ip)) {
					WebProxyDo webProxy = new WebProxyDo();
					// ��һ���ڵ�id
					/*
					 * Node firstNode = node.getPreviousSibling(); try {
					 * webProxy.setId(useId +
					 * Integer.parseInt(firstNode.getTextContent())); } catch
					 * (NumberFormatException e) { continue; }
					 */

					// �ڶ����ڵ�
					webProxy.setUrl(ip);

					// �������ڵ�port
					DomNode threeNode = node.getNextSibling();
					String port = threeNode.getTextContent();
					if (StringUtils.isNotBlank(port)
							&& StringUtils.isNumeric(port)) {
						webProxy.setPort(Integer.parseInt(port));
					} else {
						continue;
					}
					// ����id
					webProxy.setId(ip + ":" + String.valueOf(port));

					/*
					 * // �������ڵ�checkDate Node sixNode =
					 * threeNode.getNextSibling().getNextSibling()
					 * .getNextSibling(); try { // ƴ�ӵ�ǰ���+���ʱ�� Date date =
					 * DateUtils.parseDate(
					 * (Calendar.getInstance()).get(Calendar.YEAR) + "-" +
					 * sixNode.getTextContent(), new String[] {
					 * "yyyy-MM-dd HH:SS" }); webProxy.setCheckDate(date); }
					 * catch (DOMException e) { e.printStackTrace(); } catch
					 * (ParseException e) { e.printStackTrace(); }
					 */
					// �����Ƿ����
					if (this.testWebProxy(webProxy) != null) {
						if (webProxy != null) {
							proxyCnDao.persist(webProxy);
						}

					}

				}

			}
		}

	}

	/**
	 * ���Դ���Ĵ����Ƿ����
	 * 
	 * @param webProxy
	 * @return
	 */
	private WebProxyDo testWebProxy(WebProxyDo webProxy) {
		WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER_6,
				webProxy.getUrl(), webProxy.getPort());
		try {
			webClient.setJavaScriptEnabled(false);
			webClient.setTimeout(1 * 1000);
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
		List<WebProxyDo> webProxys = this.getCanUseProxy();
		if (webProxys.size() == 0) {
			return null;
		}
		int size = webProxys.size();
		Random random = new Random();
		// �����
		int select = random.nextInt(size);

		currentWebProxy = webProxys.get(select);

		if (currentWebProxy == null) {
			// ѭ���ص�
			this.getCurrentInvaidProxy();
		}
		// ��ǰ��������
		if (this.testWebProxy(currentWebProxy) == null) {
			// ��ǰ����Ѿ�ʧЧ�ˣ������
			proxyCnDao.delete(currentWebProxy);
			log.info(currentWebProxy.toString() + "�Ѿ�ʧЧ,�����");
			// ѭ���ص�
			this.getCurrentInvaidProxy();
		}
		return currentWebProxy;
	}

	private List<WebProxyDo> getCanUseProxy() {
		// return proxyCnDao.getInvailProxys();
		return new ArrayList<WebProxyDo>();
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
