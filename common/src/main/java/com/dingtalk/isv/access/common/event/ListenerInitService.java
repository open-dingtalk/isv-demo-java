package com.dingtalk.isv.access.common.event;

import com.google.common.eventbus.EventBus;

import java.util.Map;
import java.util.Set;

/**
 * Created by lifeng.zlf on 2016/3/23.
 */
public class ListenerInitService {
    private Map<EventBus,EventListener> eventListenerMap;

    public Map<EventBus, EventListener> getEventListenerMap() {
        return eventListenerMap;
    }

    public void setEventListenerMap(Map<EventBus, EventListener> eventListenerMap) {
        this.eventListenerMap = eventListenerMap;
    }

    public void register(){
        Set<EventBus> eventBusSet = eventListenerMap.keySet();
        for(EventBus eventBus:eventBusSet){
            eventBus.register(eventListenerMap.get(eventBus));
        }
    }
}
