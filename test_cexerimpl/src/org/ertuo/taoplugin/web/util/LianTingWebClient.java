package org.ertuo.taoplugin.web.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.ertuo.taoplugin.bean.SaleNum;
import org.ertuo.taoplugin.util.RegUtil;
import org.springframework.stereotype.Component;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.GaeCache;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

@Component
public class LianTingWebClient {

    private final static Logger logger = Logger.getLogger(LianTingWebClient.class.getName());

    public HtmlPage getCookiePage(String cityCookies) {
        return this.getCurrentPage(cityCookies, null);
    }

    /*public HtmlPage getDefaultPage(String cityCookies) {
        HtmlPage page = null;
        String loginUrl = "http://shop.10010.com/number/searchNumber.action";
        WebClient client = new WebClient(BrowserVersion.FIREFOX_3_6);
        client.setThrowExceptionOnScriptError(false);
        client.setCssEnabled(false);
        client.setCache(new GaeCache());
        try {
            page = client.getPage(loginUrl);
        } catch (FailingHttpStatusCodeException e) {
            logger.warning("��ȡҳ��[" + loginUrl + "]�쳣," + e.getMessage());
        } catch (MalformedURLException e) {
            logger.warning("��ȡҳ��[" + loginUrl + "]�쳣," + e.getMessage());
        } catch (IOException e) {
            logger.warning("��ȡҳ��[" + loginUrl + "]�쳣," + e.getMessage());
        }
        return page;
    }*/

    /**
     * ����ʹ��firefoxת����cookiesֵ
     * @param cityCookies
     * @return
     */
    public HtmlPage getCurrentPage(String cityCookies, String currPage) {
        if (StringUtils.isBlank(currPage)) {
            currPage = "0";
        }
        List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
        requestParameters.add(new NameValuePair("pageInfo.currPage", currPage));
        return this.getPostPage(cityCookies, requestParameters);

    }

    /**
     * ��֤�����Ƿ����
     * @param cityCookies
     * @param num
     * @return
     */
    public boolean verifyNum(String cityCookies, String num) {
        List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
        requestParameters.add(new NameValuePair("searchNumberInfo.numberCardId", num));
        HtmlPage page = this.getPostPage(cityCookies, requestParameters);
        String rs = RegUtil.getStrByReg(page.asText(), "(" + num + ")", true);
        if (rs.split("\n").length == 2) {
            return true;
        }
        return false;

    }

    /**
     * ���һ��post�ύҳ��
     * @param cityCookies
     * @param requestParameters
     * @return
     */
    private HtmlPage getPostPage(String cityCookies, List<NameValuePair> requestParameters) {
        HtmlPage hp = null;
        try {
            logger.info("��ʼ��ѯ����[" + cityCookies + "]");
            String loginUrl = "http://shop.10010.com/number/searchNumber.action";
            WebRequest request = new WebRequest(new URL(loginUrl));

            //requestParameters.add(new NameValuePair("pageInfo.totalPager", "544"));
            request.setRequestParameters(requestParameters);
            request.setHttpMethod(HttpMethod.POST);
            request.setCharset("GBK");
            request.setAdditionalHeader("Cookie", "city=" + cityCookies);
            WebClient client = new WebClient(BrowserVersion.FIREFOX_3_6);
            //�����滻��gae����
            client.setCache(new GaeCache());
            client.setJavaScriptEnabled(true);
            client.setThrowExceptionOnScriptError(false);
            client.setCssEnabled(false);
            client.setTimeout(30 * 1000);
            hp = client.getPage(client.getCurrentWindow().getTopWindow(), request);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            logger.info("������ͨ�쳣" + sw.toString());
        }
        return hp;
    }

    public HtmlButtonInput getButtonByStr(HtmlForm form, String str) {
        //final HtmlDivision div = (HtmlDivision) page.getByXPath("//div[@name='John']").get(0);
        org.w3c.dom.NodeList br = form.getElementsByTagName("input");
        for (int i = 0; i < br.getLength(); i++) {
            HtmlInput node = (HtmlInput) br.item(i);
            if (StringUtils.equals(node.getAttribute("type"), "button")) {
                if (StringUtils.equals(str, node.getAttribute("onclick"))) {
                    return (HtmlButtonInput) node;
                }
            }
        }
        return null;
    }

    /**
     * ��ѯ���е�tr����
     * @param page
     */
    public List<SaleNum> getTr(HtmlPage page) {
        List<SaleNum> sns = new ArrayList<SaleNum>();
        org.w3c.dom.NodeList br = page.getElementsByTagName("tr");
        for (int i = 0; i < br.getLength(); i++) {
            HtmlElement node = (HtmlElement) br.item(i);
            String context = node.getTextContent();
            String phoneNum = RegUtil.getStrByReg(context, "(186)[0-9]{8}", false);
            String prise = RegUtil.getStrByReg(context, "[0-9]{0,4}(Ԫ)", false);
            if (StringUtils.isNotBlank(prise) && StringUtils.isNotBlank(phoneNum)) {
                SaleNum sn = new SaleNum();
                prise = prise.replaceAll("Ԫ", "");
                //logger.info(phoneNum + " " + prise + "Ԫ");
                sn.setPrise(prise);
                sn.setNum(phoneNum);
                sns.add(sn);
            }
        }
        return sns;
    }

    /**
     * ��html�л����ҳ��
     * @param content
     * @return
     */
    public int getTotalPages(String content) {
        //����Ԥ��10000ҳ
        String temp = RegUtil.getStrByReg(content, "(��)[0-9]{0,5}(ҳ)", false);
        String totalStr = temp.replaceAll("(��)|(ҳ)", "");
        if (StringUtils.isNumeric(totalStr)) {
            return Integer.valueOf(totalStr);
        } else {
            return 0;
        }
    }

    public String[] getTotalPage(HtmlPage page) {
        HtmlTableDataCell totalPageCell = (HtmlTableDataCell) page.getElementById("totalnumpageh");
        String currPages = totalPageCell.getTextContent();
        String str = RegUtil.getStrByReg(currPages, "\\d{0,3}", true);
        String[] strs = str.split("\n");
        String[] rs = null;
        if (strs.length > 1) {
            rs = new String[] { strs[0], strs[2] };
        }

        logger.info("��ǰҳ��" + totalPageCell.getTextContent());
        return rs;
    }
}
