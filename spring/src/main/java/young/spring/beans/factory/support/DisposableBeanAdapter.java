package young.spring.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import young.spring.beans.BeansException;
import young.spring.beans.factory.DisposableBean;

import java.lang.reflect.Method;

public class DisposableBeanAdapter implements DisposableBean {

    private final Object bean;
    private final String beanName;
    private String destroyMethodName;


    public DisposableBeanAdapter(Object bean, String beanName, String destroyMethodName) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = destroyMethodName;
    }

    @Override
    public void destroy() throws Exception {
        // 1. 实现接口 DisposableBean
        if(bean instanceof DisposableBean){
            ((DisposableBean) bean).destroy();
        }

        // 2. 配置的 destory方法
        if(StrUtil.isNotEmpty(destroyMethodName) && ! (bean instanceof DisposableBean && "destroy".equals(destroyMethodName))){
            Method method = bean.getClass().getMethod(destroyMethodName);
            if(method == null){
                throw new BeansException("Couldn't find a destroy method named '" + destroyMethodName + "' on bean with name '" + beanName + "'");
            }
            method.invoke(bean);
        }
    }


}
