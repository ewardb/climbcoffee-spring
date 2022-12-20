package young.spring.bean;

import young.spring.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class DogServiceBeforeAdvice implements MethodBeforeAdvice {




    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("拦截方法 即将执行：" + method.getName());
    }


}
