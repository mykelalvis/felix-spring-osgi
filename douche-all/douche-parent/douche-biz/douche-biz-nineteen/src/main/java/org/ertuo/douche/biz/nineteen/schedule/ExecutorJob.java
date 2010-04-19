package org.ertuo.douche.biz.nineteen.schedule;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ertuo.douche.biz.nineteen.NineTeenManager;

/**
 */
public class ExecutorJob implements Runnable {

	private static Logger logger = Logger.getLogger(ExecutorJob.class);

	private NineTeenManager nineTeenManager;

	/**
	 *ʮ��¥����
	 */
	public void run() {
		logger.debug("��������ʼ����");
		// �������¥��
		List<String> floors = nineTeenManager.getFloors();
		for (String floor : floors) {
			// ���¥��������
			Map<String, String> newsList = nineTeenManager.getFloorList(floor);
			for (String key : newsList.keySet()) {
				// �ָ�����
				nineTeenManager.answer(newsList.get(key), key);
			}
		}
	}
}
