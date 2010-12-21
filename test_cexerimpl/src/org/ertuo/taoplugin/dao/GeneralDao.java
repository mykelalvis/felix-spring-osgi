package org.ertuo.taoplugin.dao;

import java.util.List;

/**
 * �ṩ����dao����
 * ��ɾ�Ĳ�
 * @author mo.duanm
 *
 */
public interface GeneralDao<T> {

    /**
     * ���ӣ�����
     * @param t
     */
    void create(T t);

    /**
     * ɾ��
     * @param t
     */
    void delete(T t);

    /**
     * ͨ��id��ȡ����ʵ��
     * @param id
     */
    T get(Object id);

    /**
     * ͨ��sql��ѯ
     * @param sql
     * @return
     */
    List<T> find(String sql);

}
