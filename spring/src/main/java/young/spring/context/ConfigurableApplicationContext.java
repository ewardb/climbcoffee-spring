package young.spring.context;

import young.spring.beans.BeansException;

/**
 * SPI interface to be implemented by most if not all application contexts.
 * Provides facilities to configure an application context in addition
 * to the application context client methods in the
 * {@link ApplicationContext} interface.
 *
 *
 * SPI :Service Provider Interface
 * 是JDK内置的一种 服务提供发现机制，可以用来启用框架扩展和替换组件，主要是被框架的开发人员使用
 *
 *
 *
 */
public interface ConfigurableApplicationContext extends ApplicationContext {


    /**
     * 刷新容器
     *
     * @throws BeansException
     */
    void refresh() throws BeansException;


    /**
     * 注册钩子
     */
    void registerShutdownHook();

    /**
     * 关闭容器
     */
    void close();



}
