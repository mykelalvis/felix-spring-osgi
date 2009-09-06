package org.ertuo.douche.dao.domain;

import java.util.Date;


/**
 * ��������
 * @author mo.duanm
 *
 */
public class WebProxyDo {
	
	private int id;
	
	private String url;
	
	private int port;
	
	private Date useDate;
	
	private Date checkDate;

	 

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

 

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public String toString(){
		return url+":"+port;
	}

}