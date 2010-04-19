package org.ertuo.douche.biz.nineteen;

import java.util.List;
import java.util.Map;

/**
 * ʮ��¥����
 * 
 * @author mo.duanm
 * 
 */
public interface NineTeenManager {

	/**
	 * @return
	 */
	List<String> getFloors();

	/**
	 * ���¥���¥�������ӹ�ϵ�б�
	 * 
	 * @param url
	 * @return Map<����id,¥��id>
	 */
	Map<String, String> getFloorList(String url);

	/**
	 * ��������б�
	 * 
	 * @return
	 */
	List<String> getNewsTitles();

	/**
	 * �ظ�ָ��id������
	 * 
	 * @param floorId
	 *            ¥��id
	 * @param newsId
	 *            ����id
	 */
	void answer(String floorId, String newsId);
}
