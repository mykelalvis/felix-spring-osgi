package org.ertuo.number.codex.engine

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

interface LtCodexEngine {
	
	/**
	 * ���˺���
	 * @param phoneNumber ��Ҫ�����˵ĺ���
	 * @return ���˺�ĺ���,�������ͨ��������null
	 */
	String filterNum(String phoneNumber)
	
	

}
