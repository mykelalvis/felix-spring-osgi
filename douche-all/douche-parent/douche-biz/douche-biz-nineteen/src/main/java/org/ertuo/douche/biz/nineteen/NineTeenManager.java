package org.ertuo.douche.biz.nineteen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.ertuo.douche.engine.htmlutil.webclient.WebClientLocal;
import org.springframework.beans.factory.annotation.Autowired;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlHiddenInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

/**
 * ʮ��¥����
 * @author mo.duanm
 *
 */
public interface NineTeenManager {
	 
	 
	 /**
	  * ��½
	 * @return
	 */
	boolean login() ;
	 
	 /**
	 * @return
	 */
	List<String> getFloors();
	
	/**
	 * ���¥���¥�������ӹ�ϵ�б�
	 * @param url
	 * @return Map<����id,¥��id> 
	 */
	 Map<String,String> getFloorList(String url);

	
	/**
	 * ��������б�
	 * @return
	 */
	 List<String> getNewsTitles();
	/**
	 *  �ظ�ָ��id������
	 * @param floorId ¥��id
	 * @param newsId ����id
	 */
	 void answer(String floorId,String newsId) ;
}
