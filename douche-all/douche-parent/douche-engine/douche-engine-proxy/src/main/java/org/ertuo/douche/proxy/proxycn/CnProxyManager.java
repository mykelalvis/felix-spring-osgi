package org.ertuo.douche.proxy.proxycn;

import org.ertuo.douche.dao.domain.WebProxyDo;


/**
 * �������
 * <p>
 * <li>�ṩ��ǰ��Ч�Ĵ���
 * </p>
 * 
 * @author mo.duanm
 * 
 * 
 */
public interface CnProxyManager {

	/**
	 * ��õ�ǰ��Ч����
	 * @return
	 */
	WebProxyDo getCurrentInvaidProxy();
	

	/**
	 * ������Ч����
	 */
	void createCanUseProxy();
	
	
	


}
