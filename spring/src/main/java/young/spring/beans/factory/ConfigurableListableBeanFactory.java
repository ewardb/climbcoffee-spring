package young.spring.beans.factory;


import young.spring.beans.BeansException;
import young.spring.beans.factory.config.AutowireCapableBeanFactory;
import young.spring.beans.factory.config.BeanDefinition;
import young.spring.beans.factory.config.ConfigurableBeanFactory;

/**
 * Configuration interface to be implemented by most listable bean factories.
 * In addition to {@link ConfigurableBeanFactory}, it provides facilities to
 * analyze and modify bean definitions, and to pre-instantiate singletons.
 *
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory,ConfigurableBeanFactory, AutowireCapableBeanFactory {

    // ConfigurableListableBeanFactory 提供了  分析 and 修改 beanDefinition . 可 预实例化。
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;



    void preInstantiateSingletons() throws BeansException;




}
