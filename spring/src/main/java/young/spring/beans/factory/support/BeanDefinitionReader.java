package young.spring.beans.factory.support;

import young.spring.beans.BeansException;
import young.spring.core.io.Resource;
import young.spring.core.io.ResourceLoader;

/**
 *  Simple interface for bean definition readers.
 *  beanDefinition 读取器
 */
public interface BeanDefinitionReader {


    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(Resource resource) throws BeansException;


    void loadBeanDefinitions(Resource ... resources) throws BeansException;

    void loadBeanDefinitions(String... locations) throws BeansException;

    void loadBeanDefinitions(String location) throws BeansException;




}
