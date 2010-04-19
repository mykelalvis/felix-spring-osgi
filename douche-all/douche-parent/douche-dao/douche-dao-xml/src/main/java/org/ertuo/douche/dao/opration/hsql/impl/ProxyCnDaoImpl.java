package org.ertuo.douche.dao.opration.hsql.impl;

import javax.jdo.PersistenceManager;

import org.ertuo.douche.dao.domain.WebProxyDo;
import org.ertuo.douche.dao.opration.impl.JdoRepository;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * �����й��洢jdo��ʽʵ��
 * 
 * @author mo.duanm
 * 
 */
public class ProxyCnDaoImpl extends JdoRepository<WebProxyDo> {

	@Inject
	public ProxyCnDaoImpl(Provider<PersistenceManager> pmProvider) {
		super(WebProxyDo.class, pmProvider);
	}

}
