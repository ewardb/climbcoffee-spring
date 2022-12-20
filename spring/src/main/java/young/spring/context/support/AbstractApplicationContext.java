package young.spring.context.support;

import young.spring.beans.BeansException;
import young.spring.context.ApplicationEvent;
import young.spring.context.ApplicationListener;
import young.spring.context.ConfigurableApplicationContext;
import young.spring.context.event.ApplicationEventMulticaster;
import young.spring.context.event.ContextClosedEvent;
import young.spring.context.event.ContextRefreshedEvent;
import young.spring.context.event.SimpleApplicationEventMulticaster;
import young.spring.core.io.DefaultResourceLoader;
import young.spring.beans.factory.ConfigurableListableBeanFactory;
import young.spring.beans.factory.config.BeanFactoryPostProcessor;
import young.spring.beans.factory.config.BeanPostProcessor;

import java.util.Collection;
import java.util.Map;

/**
 * 抽象应用上下文
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";


    private ApplicationEventMulticaster applicationEventMulticaster;




    @Override
    public void refresh() throws BeansException {
        //创建 BeanFactory  并加载 BeanDefinition
        refreshBeanFactory();

        // 获取beanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 添加 ApplicationContextAwareProcessor，让继承自 ApplicationContextAware 的 Bean 对象都能感知所属的 ApplicationContext
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        // 在实例化之前，，执行 BeanFactoryPostProcessor (Invoke factory processors registered as beans in the context.)  eg: 修改beanDefinition
        invokeBeanFactoryPostProcessors(beanFactory);

        // BeanPostProcessor 需要提前于其他 Bean 对象实例化之前执行【注册】操作
        registerBeanPostProcessors(beanFactory);


        // 初始化时间发布者
        initApplicationEventMulticaster();

        // 注册事件监听器
        registerListeners();

        // 提前实例化单例Bean对象
        beanFactory.preInstantiateSingletons();

        // 发布容器刷新完成事件
        finishRefresh();

    }


    private void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }


    public void publishEvent(ApplicationEvent applicationEvent){
        applicationEventMulticaster.multicastEvent(applicationEvent);
    }

    private void registerListeners() {
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        applicationListeners.stream().forEach(applicationListener -> {
            applicationEventMulticaster.addApplicationListener(applicationListener);
        });
    }

    private void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        // 发布容器关闭事件
        publishEvent(new ContextClosedEvent(this));

        getBeanFactory().destroySingletons();
    }

    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory){
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }

    }

    protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory){
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    protected abstract void refreshBeanFactory() throws BeansException;

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(requiredType);
    }


    public Object getBean(String name, Object... args) throws BeansException{
        return getBeanFactory().getBean(name, args);
    }

    public <T> T getBean(String name, Class<T> requiredType){
        return getBeanFactory().getBean(name, requiredType);
    }


    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }


}
