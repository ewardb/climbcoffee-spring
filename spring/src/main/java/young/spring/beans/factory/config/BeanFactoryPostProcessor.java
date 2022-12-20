package young.spring.beans.factory.config;

import young.spring.beans.BeansException;
import young.spring.beans.factory.ConfigurableListableBeanFactory;

import java.io.IOException;

/**
 * Allows for custom modification of an application context's bean definitions,
 * adapting the bean property values of the context's underlying bean factory.
 *
 * 允许自定义修改 BeanDefinition 属性信息
 */
public interface BeanFactoryPostProcessor {

    /**
     * 在所有的beanDefinition 加载完成后，实例化bean对象之前， 提供修改 BeanDefinition 属性的机制
     * @param beanFactory
     * @throws BeansException
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;



}
