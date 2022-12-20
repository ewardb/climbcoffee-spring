package young.spring.beans.factory.config;

import young.spring.beans.PropertyValues;

//https://blog.csdn.net/xiehuanhuan1991/article/details/79615960
//SuppressWarnings压制警告，即去除警告
@SuppressWarnings("rawtypes")
public class BeanDefinition {

    String SCOPE_SINGLETON = ConfigurableBeanFactory.SCOPE_SINGLETON;

    String SCOPE_PROTOTYPE = ConfigurableBeanFactory.SCOPE_PROTOTYPE;


    private Class beanClass;

    private PropertyValues propertyValues;

    private String initMethodName;

    private String destroyMethodName;


    private String scope = SCOPE_SINGLETON;

    private boolean singleton = true;

    private boolean prototype = false;


    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
        this.propertyValues = new PropertyValues();
    }


    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues == null ? new PropertyValues() : propertyValues;
    }


    public void setScope(String scope) {
        this.scope = scope;
        singleton = scope.equals(SCOPE_SINGLETON);
        prototype = scope.equals(SCOPE_PROTOTYPE);
    }

    public boolean isSingleton() {
        return singleton;
    }

    public boolean isPrototype() {
        return prototype;
    }


    public Class getBeanClass() {
        return beanClass;
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }


    public PropertyValues getPropertyValues() {
        return propertyValues;
    }


    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }
}
