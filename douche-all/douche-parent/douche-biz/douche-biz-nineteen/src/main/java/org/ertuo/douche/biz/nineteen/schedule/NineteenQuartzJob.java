package org.ertuo.douche.biz.nineteen.schedule;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ertuo.douche.biz.nineteen.NineTeenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 */
@Service
public class NineteenQuartzJob {

	private static Logger logger = Logger.getLogger(NineteenQuartzJob.class);

	@Autowired
	private NineTeenManager nineTeenManager;

	/**
	 *ʮ��¥����
	 */
	public void cronExecuteLog() {
		logger.debug("��������ʼ����");
		//��½
		nineTeenManager.login();
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
