package org.ertuo.credit.taobao.controll.lt


import java.util.logging.Logger;

import javax.persistence.EntityManager 
import javax.persistence.EntityManagerFactory;
import org.apache.commons.lang.StringUtils 
import org.ertuo.credit.taobao.domain.PhoneNum 
import org.ertuo.credit.taobao.domain.SellNum;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * �������
 * @author mo.duanm
 *
 */
class PhoneNumController {
	
	private static final Logger logger = Logger.getLogger(PhoneNumController.class.getName())
	
	EntityManagerFactory entityManagerFactory
	
	static defaultAction = "list"
	
	final UserService userService = UserServiceFactory.getUserService();
	
	def list={
		EntityManager em=entityManagerFactory.createEntityManager()
		def counts=PhoneNum.list().size()
		def phoneNums=em.createQuery("select h from PhoneNum h").setMaxResults(100).getResultList()
		return [phoneNums:phoneNums,counts:counts]
	}
	
	def add={
		PhoneNum phoneNum=new PhoneNum()
		logger.info("�������"+params.num)
		if(StringUtils.isNotBlank(params.num)){
			logger.info("����"+params.city)
			phoneNum.num=params.num
			phoneNum.prise=params.prise
			phoneNum.date=params.date
			phoneNum.city=params.city
			def existNum=PhoneNum.findWhere(num:params.num)
			if(existNum==null){
				phoneNum.save()
			}else{
				logger.warning "����["+params.num+"]�Ѿ�����"
			}
		}
	}
	
	def delAll={
		def phoneNums=PhoneNum.list()
		phoneNums.each {
			//�����ҳ�sellnum����ɾ��
			def sellNums=SellNum.findWhere(phoneNum:it)
			sellNums.each { it.delete()
			}
			it.delete()
		}
		redirect(action:"list")
	}
}
