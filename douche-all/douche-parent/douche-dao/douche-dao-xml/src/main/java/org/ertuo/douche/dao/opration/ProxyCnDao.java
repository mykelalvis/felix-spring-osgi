package org.ertuo.douche.dao.opration;

import java.util.Map;

import org.ertuo.douche.dao.domain.WebProxyDo;

/**
 * �����й���վ��Ч����洢
 * 
 * @author mo.duanm
 *
 */
public interface ProxyCnDao {
	
	/**
	 * ����һ����Ч����xml
	 * @param webProxyDo
	 */
	void createProxy(WebProxyDo webProxyDo);
	
	/**
	 * ���������Ч����xml
	 * @param webProxyDos
	 */
	void createProxy(Map<String,WebProxyDo> webProxyDos);
	
	/**
	 * ��xml�л����Ч����
	 * @return <code>WebProxyDo<code>����
	 */
	Map<String,WebProxyDo> getInvailProxys();
	
	/**
	 * �����Ч�Ĵ���
	 * @param webProxyDo
	 */
	void removePeoxy(WebProxyDo webProxyDo);


}
