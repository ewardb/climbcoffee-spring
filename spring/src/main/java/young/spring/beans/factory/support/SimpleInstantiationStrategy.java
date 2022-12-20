package young.spring.beans.factory.support;

import young.spring.beans.BeansException;
import young.spring.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SimpleInstantiationStrategy implements InstantiationStrategy {


    @Override
    public Object instantiate( BeanDefinition beanDefinition, String beanName,Constructor ctor, Object[] args) {
        Class clazz = beanDefinition.getBeanClass();
        try {
            if (args == null) {
                return clazz.getDeclaringClass().newInstance();
            } else {
                return clazz.getDeclaredConstructor(ctor.getParameterTypes()).newInstance(args);
            }
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
            throw new BeansException("Failed to instantiate [" + clazz.getName() + "]", e);

        }

    }


}
