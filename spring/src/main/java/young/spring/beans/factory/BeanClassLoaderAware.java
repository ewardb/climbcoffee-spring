package young.spring.beans.factory;

import young.spring.beans.BeansException;

public interface BeanClassLoaderAware extends Aware {


    void setBeanClassLoader(ClassLoader classLoader) throws BeansException;

}
