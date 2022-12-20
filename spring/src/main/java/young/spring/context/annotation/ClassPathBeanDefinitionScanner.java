package young.spring.context.annotation;

import cn.hutool.core.util.StrUtil;
import young.spring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import young.spring.beans.factory.config.BeanDefinition;
import young.spring.beans.factory.support.BeanDefinitionRegistry;
import young.spring.stereotype.Component;

import java.util.Set;

public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {


    private BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }


    public void doScan(String[] basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            candidates.stream().forEach(beanDefinition -> {
                // 解析 Bean 的作用域 singleton、prototype
                String beanScope = resolveBeanScope(beanDefinition);
                if (StrUtil.isNotEmpty(beanScope)) {
                    beanDefinition.setScope(beanScope);
                }
                registry.registerBeanDefinition(determineBeanName(beanDefinition), beanDefinition);
            });


            // 注册处理注解的 BeanPostProcessor（@Autowired、@Value）
            registry.registerBeanDefinition("young.spring.beans.factory.annotation.internalAutowiredAnnotationProcessor", new BeanDefinition(AutowiredAnnotationBeanPostProcessor.class));


        }
    }


    private String resolveBeanScope(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if (null != scope) return scope.value();
        return StrUtil.EMPTY;
    }


    private String determineBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String value = component.value();
        if (StrUtil.isBlank(value)) {
            return StrUtil.lowerFirst(value);
        }
        return value;
    }


}
