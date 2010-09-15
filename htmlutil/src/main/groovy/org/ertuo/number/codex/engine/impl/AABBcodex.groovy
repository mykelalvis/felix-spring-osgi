

/**
 * @author mo.duanm
 *
 */
package org.ertuo.number.codex.engine.impl

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.ertuo.number.codex.engine.LtCodexEngine;

import sun.beans.editors.IntEditor;

class AABBcodex implements LtCodexEngine{
	
	
	private Logger log=Logger.getLogger(AABBcodex.class);
	
	private String pnum;
	
	private int[] pnumints;
	
	/**
	 * ���캯��
	 * @param pnum �ֻ���
	 */
	public AABBcodex(String pnum){
		this.pnum=pnum;
		pnumints=this.split2int(pnum)
	}
	
	/**
	 * @see org.ertuo.number.codex.engine.LtCodexEngine#filterNum(java.lang.String)
	 */
	String filterNum(String phoneNumber){
	}
	
	/**
	 * ����11λ���ȵ�����
	 * @param phoneNumber
	 * @return
	 */
	private int[] split2int(String phoneNumber){
		def ints=new int[phoneNumber.length()]
		for(i in 0..<phoneNumber.length()) {
			ints[i]=Integer.parseInt(""+phoneNumber.charAt(i))
		}
		return ints;
	}
	
	/**
	 * ��ָ���ַ�����ֳ�ָ�����ȵ��ַ���
	 * @param pnum
	 * @param arrayLength
	 * @param arrs
	 * @return
	 */
	Map<String,String> splitLengthStr(String pnum,int arrayLength,Map<String,String> arrs){
		if(StringUtils.isBlank(pnum)||pnum.length()<arrayLength){
			return null;
		}
		def nest={str->
			if(str.length()==arrayLength){
				log.info "���"+str
				arrs.put str, str
			}else{
				this.splitLengthStr str, arrayLength, arrs
			}
		}
		for(i in 1..<pnum.length()){
			String a=pnum.substring (0,i)
			nest(a)
			String b=pnum.substring (i)
			nest(b)
		}
		return arrs
	}
	
	List split2arr(String pnum,int arrayLength,List arrs){
		//1.�����λ�����ȡ��������
		def remain=pnumints.length-(pnumints.length/arrayLength)*arrayLength
		
		//1.1���11λ������ȡ�������ֵ�
			def map=this.get2Num (11,2)
		//2.ʣ�����ְ�װ�������������
			map.each { 
				def nums=it.value
				def x=it
				
				}
		return null
	} 
	
	private Map<String,int[]> get2Num(int length,int cycTimes){
		def arr=new int[cycTimes]
		for(x in 0..<cycTimes){
			for(y in 0..length){
				//arr[x]=y
				log.info (x +" " +y)
			}
		}
		
		def map=[:]
		/*for(x in 0..length){
			for(y in 0..length){
				if(x==y){
					continue;
				}
				def nums=[x,y]
				map.put (String.valueOf(x)+String.valueOf(y), nums)
			}
		}*/
		return map;
		
	}
	
	/**
	 * ��ֳ�ָ�����ȵĶ�ά���飬��ֺ���������������������
	 * 12345678901����3λ�����
	 * 123 456 789 01
	 * 1 234 567 890 1
	 * 1 234 567 8 901
	 * 12 345 678 901
	 * 123 45 678 901
	 * @param arrayLength ָ���ĳ���
	 * @return
	 */
	List<String[]> split2array(int arrayLength){
		
		//��Ϊ��Ҫ���������������Ƚ��������ַ�����*2
		int length=arrayLength*2
		//�����ά����
		def rs=new ArrayList<String[]>()
		//��*2���λ�ÿ�ʼ����
		for(i in length..pnumints.length){
			// *2λ�����Ĳ�֣���ʼһ����
			//ȡ�õ�һ����λ��
			def k=i-length
			//��ɵ�һ�����������ֵ
			def joinArray=pnum.substring(k, length+k) 
			//����һ�������ֳ�2������
			def firstAr=joinArray.substring(0,arrayLength)
			//log.debug("��һ������:"+firstAr)
			def secondAr=joinArray.substring(arrayLength)
			//log.debug("�ڶ�������:"+secondAr)
			
			def add={nestNum,kaishi->
				def others=[]
				def beginStr=firstAr+","+secondAr
				this.nestAry(kaishi,arrayLength,nestNum , others)
				others.each {
					//beginStr=beginStr+","+it
					if(it instanceof List){
						def allAry=new String()[2+it.size()]
						for( h in 0..it.size()) {
							allAry[h+2]=it
						}
						rs.add allAry
					}else{
						def allAry=[firstAr, secondAr, it]as String[]
						rs.add allAry
					}
				}
				//def allAry=beginStr.split(",")
				
			}
			
			def after=pnumints.length-i-arrayLength;
			def before=i-length-arrayLength
			//Ѱ����������
			//������
			if(after>=0){
				def nestNum=pnum.substring(i)
				add(nestNum,after)
			}
			//��ǰ��
			if(before>=0){
				def nestNum=pnum.substring(0,i-length)
				add(nestNum,before)
			}
			//ǰ�󶼲���ȡ�ù涨������ϵ����
			//ǰ�󶼲�����
			if(before<0&&after<0){
				def inner=[firstAr, secondAr]as String[]
				rs.add(inner)
			}
		}
		
		return rs;
	}
	
	private void nestAry(int before,int arrayLength,String pnum,List others){
		if(pnum.length()<arrayLength){
			return;
		}
		for( g in 0..before){
			try {
				def other=pnum.substring(g, g+arrayLength)
				others.add other
				int bb=pnum.length()-g-arrayLength*2
				if(bb>0){
					def newpnum=pnum.substring((g+arrayLength))
					def otherInother=[]
					nestAry (bb, arrayLength, newpnum, otherInother)
					others.add otherInother
				}
			} catch (Exception e) {
				log.error("��"+g+"��ѯ"+pnum,e)
			}
		}
	}
	
	
	/**
	 * �����������д���
	 * @param numPhone
	 */
	private void process(int[] numPhone){
	}
}
