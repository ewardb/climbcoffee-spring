package young.spring.beans.factory.config;


import young.spring.beans.BeansException;
import young.spring.beans.factory.BeanFactory;

/**
 * Extension of the {@link BeanFactory}
 * interface to be implemented by bean factories that are capable of
 * autowiring, provided that they want to expose this functionality for
 * existing bean instances.
 *
 *  提供 暴露一些功能的能力
 */
public interface AutowireCapableBeanFactory extends BeanFactory {


    /**
     * 执行 beanPostProcessors 接口实现的 postProcessBeforeInitialization 方法
     * @return
     * @throws BeansException
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;


    /**
     * 执行 beanPostProcessors 接口实现的 postPrecessAfterInitialization 方法
     * @return
     * @throws BeansException
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;




}
