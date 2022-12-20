package young.spring.beans.factory.support;

import young.spring.beans.factory.config.BeanDefinition;

public interface BeanDefinitionRegistry {



    /**
     * 向注册表中注册 BeanDefinition
     *
     * @param beanName
     * @param beanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);


    /**
     * 判断是否包含指定名称的BeanDefinition
     * @param beanName
     * @return
     */
    Boolean containsBeanDefinition(String beanName);

}
