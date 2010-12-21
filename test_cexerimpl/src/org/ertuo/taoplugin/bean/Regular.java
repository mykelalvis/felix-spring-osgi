package org.ertuo.taoplugin.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * ������ʽ
 * @author mo.duanm
 */
@Entity(name = "Regular")
public class Regular {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long   id;

    /**
     * ����
     */
    @Column
    String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * ������ʽ
     */
    @Column
    String reg;

    /**
     * �����ѯ���������
     */
    @Column
    int    length;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

}
