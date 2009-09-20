package org.ertuo.douche.biz.nineteen.schedule;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.ertuo.douche.biz.nineteen.NineTeenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 */
@Service
public class ExecutorJob implements Runnable {

	private static Logger logger = Logger.getLogger(ExecutorJob.class);

	@Autowired
	private NineTeenManager nineTeenManager;

	/**
	 *ʮ��¥����
	 */
	public void run() {
		logger.debug("��������ʼ����");
		//�������¥��
		List<String> floors=nineTeenManager.getFloors();
		for (String floor : floors) {
			//���¥��������
			Map<String,String> newsList=nineTeenManager.getFloorList(floor);
			for(String key:newsList.keySet()){
				//�ָ�����
				nineTeenManager.answer(newsList.get(key), key);
			}
		}
	}
}
