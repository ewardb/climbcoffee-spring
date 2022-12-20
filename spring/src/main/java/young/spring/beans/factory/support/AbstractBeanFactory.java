package young.spring.beans.factory.support;

import com.google.common.collect.Lists;
import young.spring.beans.BeansException;
import young.spring.beans.factory.FactoryBean;
import young.spring.beans.factory.config.BeanDefinition;
import young.spring.beans.factory.config.BeanPostProcessor;
import young.spring.beans.factory.config.ConfigurableBeanFactory;
import young.spring.utils.StringValueResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * BeanDefinition注册表接口
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    /**
     * BeanPostProcessors to apply in createBean
     */
    List<BeanPostProcessor> beanPostProcessors = Lists.newArrayList();


    /**
     * String resolvers to apply e.g. to annotation attribute values
     */
    private final List<StringValueResolver> embeddedValueResolvers = new ArrayList<>();


    @Override
    public Object getBean(String beanName, Object... args) throws BeansException {
        return doGetBean(beanName, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return (T) getBean(name);
    }


    @Override
    public Object getBean(String beanName) throws BeansException {
        return doGetBean(beanName, null);
    }

    protected <T> T doGetBean(final String beanName, final Object[] args) {
        //单例里如果有对象就拿
        Object singleton = getSingleton(beanName);
        if (singleton != null) {
            return (T) getObjectForBeanInstance(singleton, beanName);
        }
        //没有就去创造
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        Object bean = createBean(beanName, beanDefinition, args);
        return (T) getObjectForBeanInstance(bean, beanName);
    }


    //这里就可以讲所有的bean都托管到spring中了 哈哈哈哈哈
    private Object getObjectForBeanInstance(Object bean, String beanName) {
        if (!(bean instanceof FactoryBean)) {
            return bean;
        }

        Object object = getCachedObjectForFactoryBean(beanName);

        if (object == null) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) bean;
            object = getObjectFromFactoryBean(factoryBean, beanName);
        }

        return object;
    }

    public abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    public abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;


    /**
     * Return the list of BeanPostProcessors that will get applied
     * to beans created with this factory.
     */
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }


    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        beanPostProcessors.remove(beanPostProcessor);
        beanPostProcessors.add(beanPostProcessor);
    }

    @Override
    public void addEmbeddedValueResolver(StringValueResolver valueResolver) {
        this.embeddedValueResolvers.add(valueResolver);
    }

    @Override
    public String resolveEmbeddedValue(String value) throws BeansException {
        String result = value;

        for(StringValueResolver resolver: embeddedValueResolvers){
            result = resolver.resolveStringValue(result);
        }
        return result;
    }





}
