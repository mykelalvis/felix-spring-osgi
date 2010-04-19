package org.ertuo.douche.biz;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Discuz��̳������
 * 
 * @author Administrator
 * 
 */
public class DiscuzUtil {

	private final static Logger log = Logger.getLogger(DiscuzUtil.class);

	/**
	 * ȡ�������г��ȴ���2��¥��id
	 * 
	 * @param href
	 * @return
	 */
	public static String getFid(String href) {

		if (StringUtils.isBlank(href)) {
			throw new IllegalArgumentException("getTid����Ϊ��");
		}

		String fidReg = ".*(forum)-[0-9]\\d{1,9}-[0-9].(html)";
		String fidNumReg = "(forum)-[0-9]\\d{1,9}-";

		if (!href.matches(fidReg)) {
			log.warn("��������[" + href + "]����������[" + fidReg + "]");
			return null;
		}

		String rs = null;
		Pattern p = Pattern.compile(fidNumReg, Pattern.DOTALL);
		Matcher m = p.matcher(href);
		while (m.find()) {
			rs = m.group();
		}
		if (StringUtils.equals(rs, href)) {
			log.warn("��������[" + href + "]����������[" + fidNumReg + "],�Ҳ���tid");
			return null;
		}
		return rs.replaceAll("-", "").replaceAll("forum", "");

	}

	/**
	 * ȡ�������г��ȴ���7������id
	 * 
	 * @param href
	 * @return ���������û�����������ķ���null
	 */

	public static String getTid(String href) {

		if (StringUtils.isBlank(href)) {
			throw new IllegalArgumentException("getTid����Ϊ��");
		}

		// �Ƿ���Discuz���ӵ�����
		String tidReg = ".*(thread)-[0-9]\\d{6,9}-[0-9]-[0-9].(html)";
		// ȡ������tid������
		String tidNumReg = "[0-9]\\d{6,9}";

		if (!href.matches(tidReg)) {
			log.warn("��������[" + href + "]����������[" + tidReg + "]");
			return null;
		}

		String rs = null;
		Pattern p = Pattern.compile(tidNumReg, Pattern.DOTALL);
		Matcher m = p.matcher(href);
		while (m.find()) {
			rs = m.group();
		}
		if (StringUtils.equals(rs, href)) {
			log.warn("��������[" + href + "]����������[" + tidNumReg + "],�Ҳ���tid");
			return null;
		}
		return rs;
	}

	public void test() {
		String f_href = "http://74.55.54.148/forum-96-2.html";
		String t_href = "http://74.55.54.148/thread-1005189-1-2.html";
		String tid = DiscuzUtil.getTid(t_href);
		String fid = DiscuzUtil.getFid(f_href);
		log.debug(tid + " " + fid);
	}

}
