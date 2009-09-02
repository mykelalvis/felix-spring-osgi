package org.ertuo.htmlutil.nineteen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.ertuo.htmlutil.webclient.WebClientLocal;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;

/**
 * ��ʳ
 * @author mo.duanm
 *
 */
public class BiteSup {
	private final Logger log=Logger.getLogger(BiteSup.class);
	
	private String[] biteSupUrl=new String[]{
			//"http://food.19lou.com/",
			//"http://tour.19lou.com/",
			//"http://auto.19lou.com/",
			//"http://fashion.19lou.com/",
			"http://love.19lou.com/",
			"http://baby.19lou.com/",
			};
	
	
	private WebClientLocal local=new WebClientLocal(false);
	
	//��������
	private static String guanggao="&nbsp;[h2008] ֧��¥��������[h2003]";
	
	/**
	 * ���¥���б�
	 */
	public List<String> getFloor(){
		List<String> floorList=new ArrayList<String>();
		HtmlPage page=null;
		for (String site : biteSupUrl) {
			page=local.getHtmlPageByUrl(site);	
		}
		List<HtmlAnchor> auchors= page.getAnchors();
		for (HtmlAnchor htmlAnchor : auchors) {
			String href=htmlAnchor.getHrefAttribute();
			if(StringUtils.isNotBlank(href)){
				String regex = ".*(forum)-[1-9]\\d{0,4}-[1][.](html)";
				if(href.matches(regex)){
					floorList.add(htmlAnchor.getHrefAttribute());
					//log.info(href);
				}
			}
		}
		
		return floorList;
	}
	
	/**
	 * ��������б�
	 * @param url
	 * @return Map<����id,¥��id> 
	 */
	public Map<String,String> getNewsList(String url){
		Map<String,String> newsList=new HashMap<String, String>();
		
		if(StringUtils.isBlank(url)||StringUtils.startsWith(url, "/")){
			return newsList;
		}
		
		String forumName=url.substring(url.lastIndexOf("/")+1, url.indexOf(".html")-1)+"thread-";
		//log.info(forumName);
		//ȡ��¥��
		String floor="";
		String[] floorIds=forumName.split("[^1-9]");
		
		for (String floorId : floorIds) {
			if(StringUtils.isNotBlank(floorId)){
				floor=floorId;
			}
		}
		HtmlPage page=local.getHtmlPageByUrl(url);
		List<HtmlAnchor> anchors= page.getAnchors();
		for (HtmlAnchor htmlAnchor : anchors) {
			if(StringUtils.isBlank(htmlAnchor.getHrefAttribute())){
				continue;
			}
			String href=htmlAnchor.getHrefAttribute();
			if(href.startsWith(forumName)){
				String[] ids=href.split("[^1-9]");
				for (String id : ids) {
					if(id.length()>7){
						//log.info("�ɻظ�������["+href+"]["+id+"]");
						newsList.put(id,floor);
					}
				}
				
				
			}
			
			
			
		}
		 
		
		return newsList;
		
	}
	/**
	 *  �ظ�ָ��id������
	 * @param floorId ¥��id
	 * @param newsId ����id
	 */
	public void answer(String floorId,String newsId) {
		String answerUrl="http://www.19lou.com/post.php?action=reply&fid="+floorId+"&tid="+newsId+"&extra=page%3D1";
		try {
		HtmlPage loginAfterPagee=local.getHtmlPageByUrl(answerUrl);
		HtmlForm postform = (HtmlForm) loginAfterPagee
				.getElementById("postform");
		// ����
		HtmlTextArea message = postform.getTextAreaByName("message");
		// ����value
		message.focus();
		message.setText(guanggao);
		message.blur();
		final HtmlSubmitInput replysubmit = (HtmlSubmitInput) postform
				.getInputByName("replysubmit");
		HtmlPage htmlPage=local.getClickHtmlPage(replysubmit);
		if(htmlPage.asText().contains("http://shop58883417.taobao.com/")){
			log.info("����["+answerUrl+"]�ظ�");
		}
		
		} catch (Exception e) {
			log.error("�ظ�["+answerUrl+"]����",e);
		}
	}
}
