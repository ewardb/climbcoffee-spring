package young.spring.event;

import young.spring.context.ApplicationListener;
import young.spring.context.event.ContextClosedEvent;

public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {



    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("关闭事件 :"+ this.getClass().getName());
    }
}
