package org.ertuo.douche.biz.nineteen.schedule;


import org.apache.log4j.Logger;
import org.ertuo.douche.proxy.proxycn.CnProxyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 */
@Service
public class ExecutorProxyJob implements Runnable {

	private static Logger logger = Logger.getLogger(ExecutorProxyJob.class);

	@Autowired
	private CnProxyManager cnProxyManager;

	/**
	 *ʮ��¥����
	 */
	public void run() {
		logger.debug("��ʼ��������");
		cnProxyManager.createCanUseProxy();
	}
}
