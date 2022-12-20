package young.spring.context;

import young.spring.beans.factory.Aware;

public interface ApplicationContextAware extends Aware {

    void setApplicationAware(ApplicationContext applicationAware);

    /**
     * 另外由于 ApplicationContext 并不是在 AbstractAutowireCapableBeanFactory 中 createBean 方法下的内容，
     * 所以需要像容器中注册 addBeanPostProcessor ，
     * 再由  createBean  统一调用 applyBeanPostProcessorsBeforeInitialization 时进行操作。
     */

}
