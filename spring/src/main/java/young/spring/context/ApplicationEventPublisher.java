package young.spring.context;

/**
 * Interface that encapsulates event publication functionality.
 * Serves as super-interface for ApplicationContext.
 *
 * 事件发布者接口
 *
 */
public interface ApplicationEventPublisher {

    void publishEvent(ApplicationEvent applicationEvent);

}
