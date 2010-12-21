package org.ertuo.taoplugin.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "ScheduleProcess")
public class ScheduleProcess {

    @Id
    String  id;

    /**
     * ����
     */
    @Column
    String  date;

    /**
     * ��ҳ��
     */
    @Column
    int     totalPage;

    /**
     * ��ǰ����ҳ
     */
    @Column
    int     currPage;

    /**
     * �Ƿ��Ѿ����µ�����ƽ̨
     */
    @Column
    boolean isUpdateTOP;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public boolean isUpdateTOP() {
        return isUpdateTOP;
    }

    public void setUpdateTOP(boolean isUpdateTOP) {
        this.isUpdateTOP = isUpdateTOP;
    }

}
