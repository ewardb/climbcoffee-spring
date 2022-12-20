package young.spring.beans.factory;

import young.spring.beans.BeansException;

public interface BeanFactoryAware extends Aware {


    void setBeanFactory(BeanFactory beanFactory) throws BeansException;

}
