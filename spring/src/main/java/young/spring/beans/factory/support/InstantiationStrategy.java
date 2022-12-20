package young.spring.beans.factory.support;


import young.spring.beans.BeansException;
import young.spring.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * Bean 实例化策略
 */
public interface InstantiationStrategy {


    Object instantiate( BeanDefinition beanDefinition,String beanName, Constructor ctor, Object [] args) throws BeansException;
}
