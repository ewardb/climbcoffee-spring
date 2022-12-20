package young.spring.common;

import young.spring.beans.BeansException;
import young.spring.beans.factory.ConfigurableListableBeanFactory;
import young.spring.beans.PropertyValue;
import young.spring.beans.PropertyValues;
import young.spring.beans.factory.config.BeanDefinition;
import young.spring.beans.factory.config.BeanFactoryPostProcessor;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();

        propertyValues.addPropertyValue(new PropertyValue("company", "改为：字节跳动"));
    }

}
