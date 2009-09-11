package org.ertuo.douche.dao.opration;

import java.util.List;
import java.util.Map;

import org.ertuo.douche.dao.domain.WebProxyDo;

/**
 * 代理中国网站有效代理存储
 * 
 * @author mo.duanm
 *
 */
public interface ProxyCnDao {
	
	/**
	 * 创建一个有效代理到xml
	 * @param webProxyDo
	 */
	void createProxy(WebProxyDo webProxyDo);
	
	/**
	 * 创建多个有效代理到xml
	 * @param webProxyDos
	 */
	void createProxy(List<WebProxyDo> webProxyDos);
	
	/**
	 * 从xml中获得有效代理
	 * @return <code>WebProxyDo<code>集合 
	 * <p>如果文件中不存在 返回一个空Map</p>
	 * 
	 */
	List<WebProxyDo> getInvailProxys();
	
	/**
	 * 清楚无效的代理
	 * @param webProxyDo
	 */
	void removePeoxy(WebProxyDo webProxyDo);


}
