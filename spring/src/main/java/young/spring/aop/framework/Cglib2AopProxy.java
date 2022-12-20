package young.spring.aop.framework;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import young.spring.aop.AdvisedSupport;

import java.lang.reflect.Method;

/**
 * cglib 代理
 */
public class Cglib2AopProxy implements AopProxy {


    private AdvisedSupport advisedSupport;


    public Cglib2AopProxy(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }


    @Override
    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(advisedSupport.getTargetSource().getTarget().getClass());
        enhancer.setInterfaces(advisedSupport.getTargetSource().getTargetClass());
        enhancer.setCallback(new DynamicAdvisedInterceptor(advisedSupport));
        return enhancer.create();
    }


    public static class DynamicAdvisedInterceptor implements MethodInterceptor {

        private final AdvisedSupport advised;

        public DynamicAdvisedInterceptor(AdvisedSupport advised) {
            this.advised = advised;
        }

        /**
         * All generated proxied methods call this method instead of the original method.
         * The original method may either be invoked by normal reflection using the Method object,
         * or by using the MethodProxy (faster).
         *
         * @param o           "this", the enhanced object 增强的代理对象
         * @param method      intercepted Method 拦截方法
         * @param objects     argument array; primitive types are wrapped 参数
         * @param methodProxy used to invoke super (non-intercepted method); may be called
         *                    as many times as needed   代理方法
         * @return any value compatible with the signature of the proxied method. Method returning void will ignore this value.
         * @throws Throwable any exception may be thrown; if so, super method will not be invoked
         * @see MethodProxy
         */
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            CglibMethodInvocation methodInvocation = new CglibMethodInvocation(advised.getTargetSource().getTarget(), method, objects, methodProxy);
            //被拦截的  最终调用  methodInvocation 方法执行器 执行
            if (advised.getMethodMatcher().matches(method, advised.getTargetSource().getTarget().getClass())) {
                return advised.getMethodInterceptor().invoke(methodInvocation);
            }
            //未被拦截的  最终调用 methodInvocation 方法执行器 执行
            return methodInvocation.proceed();
        }
    }


    public static class CglibMethodInvocation extends ReflectiveMethodInvocation {
        private final MethodProxy methodProxy;

        public CglibMethodInvocation(Object target, Method method, Object[] arguments, MethodProxy methodProxy) {
            super(target, method, arguments);
            this.methodProxy = methodProxy;
        }

        @Override
        public Object proceed() throws Throwable {
            return methodProxy.invoke(this.target, this.arguments);
        }
    }


//    private static class FastClassInfo
//    {
//        // 被代理类的FastClass   原生类的   只有自己的 和 父类的方法
//        FastClass f1;
//        // 代理类的FastClass    子类的     有自己的父类的   生成的  方法    方法多！！！！
//        FastClass f2;
//        int i1;
//        int i2;
//    }
//
//    methodProxy.invoke(this.target, this.arguments)
//    return fci.f1.invoke(fci.i1, obj, args);
//
//    methodProxy.invokeSuper()
//    return fci.f2.invoke(fci.i2, obj, args);





}
