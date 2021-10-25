package com.superhahastar.benben.pojo;

/**
 * @author lijie.zhang
 * @date 2021/10/25 15:08
 */
public class Alert {

    public long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "id=" + id +
                '}';
    }
}
