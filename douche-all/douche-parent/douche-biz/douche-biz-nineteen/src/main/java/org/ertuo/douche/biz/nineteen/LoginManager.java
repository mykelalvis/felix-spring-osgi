/**
 * 
 */
package org.ertuo.douche.biz.nineteen;

/**
 * ��¼������¼���ܣ�cookies����
 * @author mo.duanm
 *
 */
public interface LoginManager {
	
	
	/**
	 * ��¼
	 * @return
	 */
	boolean login();
	
	/**
	 * ͨ��cookies��¼
	 * @return
	 */
	boolean loginOnCookies();
	
	/**
	 * ���ĳ��cookies
	 * @param userName
	 */
	void getCookies(String userName);
	
	/**
	 * ���һ�������cookies
	 */
	void getRandomCookies();

}
