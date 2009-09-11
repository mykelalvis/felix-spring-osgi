package org.ertuo.douche.dao.opration.xml.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.ertuo.douche.dao.domain.WebProxyDo;
import org.ertuo.douche.dao.opration.ProxyCnDao;
import org.springframework.beans.factory.annotation.Autowired;

import com.thoughtworks.xstream.XStream;

/**
 * �����й��洢��xml��ʽ��ʵ����
 * 
 * @author mo.duanm
 * 
 */
// @Service("proxyCnDao")
public class ProxyCnDaoXmlImpl implements ProxyCnDao {

	@Autowired
	private XStream stream;

	private static final String location = "d:/douche/proxy-data.xml";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ertuo.douche.dao.opration.ProxyCnDao#createProxy(org.ertuo.douche
	 * .dao.domain.WebProxyDo)
	 */
	public void createProxy(WebProxyDo webProxyDo) {

	}

	public List<WebProxyDo> getInvailProxys() {
		try {
			return (List<WebProxyDo>) stream.fromXML(new FileInputStream(
					location));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return new ArrayList<WebProxyDo>();
	}

	public void createProxy(List<WebProxyDo> webProxyDos) {
		if (webProxyDos == null) {
			throw new IllegalArgumentException("����[" + webProxyDos + "]Ϊnull");
		}

		// ԭxml�д洢�Ķ���
		List<WebProxyDo> webProxys = this.getInvailProxys();
		//���մ洢�ļ���
		List<WebProxyDo> finalWebProxyDos=new ArrayList<WebProxyDo>(webProxys);
		//���˵��ظ��Ĵ���
		for (WebProxyDo oldWebProxyDo : webProxys) {
			for (WebProxyDo newWebProxyDo : webProxyDos) {
				//���ip�Ͷ˿ڶ���ͬ ��ʾ�ظ��Ĵ���
				if (StringUtils.equals(newWebProxyDo.getUrl(), oldWebProxyDo
						.getUrl())
						&& (newWebProxyDo.getPort()==
								oldWebProxyDo.getPort())) {
					continue;

				}else{
					finalWebProxyDos.add(newWebProxyDo);
				}
			}
		}
		try {
			//�洢��xml��
			stream.toXML(finalWebProxyDos, new FileOutputStream(location));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void removePeoxy(WebProxyDo webProxyDo) {
		List<WebProxyDo> webProxyDos = this.getInvailProxys();
		webProxyDos.remove(webProxyDo);
		this.createProxy(webProxyDos);
	}

}
