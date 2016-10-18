package com.qf.administrator.sports.modules.coach.bean;

/**
 * Created by Administrator on 2016/10/12.
 */
public class MyBean {
    private EventBusBean eventBusBean;

    public MyBean(EventBusBean eventBusBean) {
        this.eventBusBean = eventBusBean;
    }

    public EventBusBean getEventBusBean() {
        return eventBusBean;
    }

    public void setEventBusBean(EventBusBean eventBusBean) {
        this.eventBusBean = eventBusBean;
    }
}
