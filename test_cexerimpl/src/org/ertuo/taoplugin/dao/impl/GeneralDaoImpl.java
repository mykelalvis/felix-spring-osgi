package org.ertuo.taoplugin.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.ertuo.taoplugin.dao.GeneralDao;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.transaction.annotation.Transactional;

/**
 * ���ò�����
 * @author mo.duanm
 *
 */
public abstract class GeneralDaoImpl<T> extends JpaDaoSupport implements GeneralDao<T> {

    public abstract Class getTclass();

    @Transactional
    public void create(T t) {
        try {
            getJpaTemplate().persist(t);
        } catch (DataIntegrityViolationException e) {
            //����Ѿ��־û����͸����������
            getJpaTemplate().merge(t);
        }

    }

    @Transactional
    public void delete(T t) {
        getJpaTemplate().remove(t);

    }

    public T get(Object id) {
        return (T) getJpaTemplate().find(getTclass(), id);

    }

    public List<T> find(String sql) {
        if (StringUtils.isBlank(sql)) {
            sql = "select w from  " + getTclass().getSimpleName() + " w";
        }
        return getJpaTemplate().find(sql);
    }
}
