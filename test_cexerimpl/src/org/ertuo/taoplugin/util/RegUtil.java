package org.ertuo.taoplugin.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegUtil {
	/**
	 * ͨ�����򷵻�һ���ַ���,����ж������������м���"\n"����
	 * 
	 * @param str
	 *            ��Ҫ������ַ���
	 * @param reg
	 *            ������ʽ
	 * @param nest
	 *            �Ƿ�Ƕ�ײ�ѯ
	 * @return ������������ַ���
	 */
	public static String getStrByReg(String str, String reg, boolean nest) {
		StringBuffer sb = new StringBuffer();
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(str);
		if (nest) {
			while (m.find()) {
				sb.append(m.group() + "\n");
			}
		} else {
			if (m.find()) {
				sb.append(m.group());
			}
		}
		return sb.toString();
	}

}
