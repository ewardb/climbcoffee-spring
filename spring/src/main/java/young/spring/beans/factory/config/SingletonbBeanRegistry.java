package young.spring.beans.factory.config;

/**
 * 单例注册
 */
public interface SingletonbBeanRegistry {

    Object getSingleton(String beanName);

    void registerSingleton(String beanName, Object singletonObject);
}
