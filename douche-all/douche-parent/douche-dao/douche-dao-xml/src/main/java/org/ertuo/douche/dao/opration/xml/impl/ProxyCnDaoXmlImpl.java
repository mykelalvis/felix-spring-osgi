package org.ertuo.douche.dao.opration.xml.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.ertuo.douche.dao.domain.WebProxyDo;
import org.ertuo.douche.dao.opration.ProxyCnDao;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.XStream;

/**
 * �����й��洢��xml��ʽ��ʵ����
 * 
 * @author mo.duanm
 * 
 */
@Service("proxyCnDao")
public class ProxyCnDaoXmlImpl implements ProxyCnDao, InitializingBean {

	private final Logger logger = Logger.getLogger(ProxyCnDaoXmlImpl.class);

	@Autowired
	private XStream stream;

	private static final String location = "d:/douche/";

	private static final String fileName = "proxy-data.xml";

	private static final String fileLocation = location + fileName;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ertuo.douche.dao.opration.ProxyCnDao#createProxy(org.ertuo.douche
	 * .dao.domain.WebProxyDo)
	 */
	public void createProxy(WebProxyDo webProxyDo) {
		List<WebProxyDo> list = this.getInvailProxys();
		for (WebProxyDo proxyDo : list) {
			if (StringUtils.equals(proxyDo.getId(), webProxyDo.getId())) {
				logger.debug("����[" + proxyDo.getId() + "]�ظ�");
				return;
			}
		}

		list.add(webProxyDo);
		try {
			stream.toXML(list, new FileOutputStream(fileLocation));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<WebProxyDo> getInvailProxys() {
		try {

			return (List<WebProxyDo>) stream.fromXML(new FileInputStream(
					fileLocation));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return new ArrayList<WebProxyDo>();
	}

	public void createProxy(List<WebProxyDo> webProxyDos) {
		if (webProxyDos == null) {
			throw new IllegalArgumentException("����[" + webProxyDos + "]Ϊnull");
		}

	/*	// ԭxml�д洢�Ķ���
		List<WebProxyDo> webProxys = this.getInvailProxys();
		// ���մ洢�ļ���
		List<WebProxyDo> finalWebProxyDos = new ArrayList<WebProxyDo>(webProxys);
		// ���˵��ظ��Ĵ���
		for (WebProxyDo oldWebProxyDo : webProxys) {
			for (WebProxyDo newWebProxyDo : webProxyDos) {
				// ���ip�Ͷ˿ڶ���ͬ ��ʾ�ظ��Ĵ���
				if (StringUtils.equals(newWebProxyDo.getId(), oldWebProxyDo.getId())) {
					continue;

				} else {
					finalWebProxyDos.add(newWebProxyDo);
				}
			}
		}*/
		try {
			// �洢��xml��
			stream.toXML(webProxyDos, new FileOutputStream(fileLocation));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void removePeoxy(WebProxyDo webProxyDo) {
		List<WebProxyDo> webProxyDos = this.getInvailProxys();
		webProxyDos.remove(webProxyDo);
		this.createProxy(webProxyDos);
	}

	public void afterPropertiesSet() throws Exception {
		File proxyFile = new File(this.fileLocation);
		if (!proxyFile.exists()) {
			new File(this.location).mkdirs();
			new File(fileLocation).createNewFile();
			try {
				stream.toXML(new ArrayList<WebProxyDo>(), new FileOutputStream(
						fileLocation));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
