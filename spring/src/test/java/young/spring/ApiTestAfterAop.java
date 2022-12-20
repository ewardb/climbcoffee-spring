package young.spring;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.MethodProxy;
import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Test;
import young.spring.aop.AdvisedSupport;
import young.spring.aop.ClassFilter;
import young.spring.aop.MethodMatcher;
import young.spring.aop.TargetSource;
import young.spring.aop.aspectj.AspectJExpressionPointcut;
import young.spring.aop.aspectj.AspectJExpressionPointcutAdvisor;
import young.spring.aop.framework.Cglib2AopProxy;
import young.spring.aop.framework.JdkDynamicAopProxy;
import young.spring.aop.framework.ProxyFactory;
import young.spring.aop.framework.ReflectiveMethodInvocation;
import young.spring.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import young.spring.bean.DogService;
import young.spring.bean.DogServiceBeforeAdvice;
import young.spring.bean.DogServiceInterceptor;
import young.spring.bean.IDogService;
import young.spring.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ApiTestAfterAop {

    @Test
    public void test__proxy_method() {
        // 目标对象(可以替换成任何的目标对象)
        Object targetObj = new DogService();


        IDogService dogService = (IDogService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                targetObj.getClass().getInterfaces(),
                new InvocationHandler() {
                    // 方法匹配器
                    MethodMatcher methodMatcher = new AspectJExpressionPointcut("execution(* bean.IDogService.*(..))");

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (methodMatcher.matches(method, targetObj.getClass())) {
                            // 方法拦截器
                            MethodInterceptor methodInterceptor = invocation -> {
                                long start = System.currentTimeMillis();
                                try {
                                    return invocation.proceed();
                                } finally {
                                    System.out.println("监控 - Begin By AOP");
                                    System.out.println("方法名称：" + invocation.getMethod().getName());
                                    System.out.println("方法耗时：" + (System.currentTimeMillis() - start) + "ms");
                                    System.out.println("监控 - End\r\n");
                                }
                            };
                            // 反射调用
                            return methodInterceptor.invoke(new ReflectiveMethodInvocation(targetObj, method, args));
                        }
                        return method.invoke(targetObj, args);
                    }
                });

        String result = dogService.queryDogInfo();

        System.out.println("测试结果：" + result);

    }


    @Test
    public void test_proxy_class() {
        IDogService dogService = (IDogService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{IDogService.class}, (proxy, method, args) -> "你被代理了！");
        String result = dogService.queryDogInfo();
        String d = dogService.register("dd");
        System.out.println(d);
        System.out.println("测试结果：" + result);

    }


    @Test
    public void test_dynamic() {
        // 目标对象
        IDogService dogService = new DogService();
        // 组装代理信息
        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(dogService));
        advisedSupport.setMethodInterceptor(new DogServiceInterceptor());
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(* bean.IDogService.*(..))"));

        // 代理对象(JdkDynamicAopProxy)
        IDogService proxy_jdk = (IDogService) new JdkDynamicAopProxy(advisedSupport).getProxy();
        // 测试调用
        System.out.println("测试结果：" + proxy_jdk.queryDogInfo());

        // 代理对象(Cglib2AopProxy)
        IDogService proxy_cglib = (IDogService) new Cglib2AopProxy(advisedSupport).getProxy();
        // 测试调用
        System.out.println("测试结果：" + proxy_cglib.register("huahua"));
    }


    @Test
    public void test_aop() throws NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* bean.IDogService.*(..))");
        Class<DogService> clazz = DogService.class;
        Method method = clazz.getDeclaredMethod("queryDogInfo");
        System.out.println(pointcut.matches(clazz));
        System.out.println(pointcut.matches(method, clazz));
    }


    @Test
    public void test_advisor() {
        // 目标对象
        IDogService userService = new DogService();
        AspectJExpressionPointcutAdvisor aspectJExpressionPointcutAdvisor = new AspectJExpressionPointcutAdvisor();
        aspectJExpressionPointcutAdvisor.setAdvice(new MethodBeforeAdviceInterceptor(new DogServiceBeforeAdvice()));
        aspectJExpressionPointcutAdvisor.setExpression("execution(* bean.IDogService.*(..))");
        ClassFilter classFilter = aspectJExpressionPointcutAdvisor.getPointcut().getClassFilter();
        if (classFilter.matches(userService.getClass())) {
            AdvisedSupport advisedSupport = new AdvisedSupport();
            TargetSource targetSource = new TargetSource(userService);
            advisedSupport.setMethodInterceptor((MethodInterceptor) aspectJExpressionPointcutAdvisor.getAdvice());
            advisedSupport.setMethodMatcher(aspectJExpressionPointcutAdvisor.getPointcut().getMethodMatcher());
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setProxyTargetClass(false);
            IDogService proxy = (IDogService) new ProxyFactory(advisedSupport).getProxy();
            System.out.println("测试结果：" + proxy.queryDogInfo());
        }
    }


    private AdvisedSupport advisedSupport;


//    @Before
//    public void init() {
//        // 目标对象
//        IDogService dogService = new DogService();
//        advisedSupport = new AdvisedSupport();
//        advisedSupport.setMethodInterceptor(new DogServiceInterceptor());
//        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(* bean.IDogService.*(..))"));
//        advisedSupport.setTargetSource(new TargetSource(dogService));
//        advisedSupport.setProxyTargetClass(false);
//    }


    @Test
    public void test_proxyFactory() {
        advisedSupport.setProxyTargetClass(false);
        IDogService proxy = (IDogService) new ProxyFactory(advisedSupport).getProxy();
        System.out.println(proxy.queryDogInfo());

    }


    @Test
    public void test_beforeAdvice() {
        DogServiceBeforeAdvice dogServiceBeforeAdvice = new DogServiceBeforeAdvice();
        MethodBeforeAdviceInterceptor interceptor = new MethodBeforeAdviceInterceptor(dogServiceBeforeAdvice);
        advisedSupport.setMethodInterceptor(interceptor);
        IDogService proxy = (IDogService) new ProxyFactory(advisedSupport).getProxy();
        System.out.println(proxy.queryDogInfo());


    }


    @Test
    public void test_aop1() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        IDogService dogService = applicationContext.getBean("dogService", IDogService.class);
        System.out.println("测试结果：" + dogService.queryDogInfo());
    }


    //                  -----------------
    //自动扫描
    @Test
    public void test_scan() {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:spring-scan.xml");
        IDogService dogService = classPathXmlApplicationContext.getBean("dogService", IDogService.class);
        System.out.println("测试 dog" + dogService.queryDogInfo());
    }


    //
    @Test
    public void test_property() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-property.xml");

        DogService dogService = applicationContext.getBean("dogService", DogService.class);
        System.out.println("测试结果：" + dogService);
    }


    @Test
    public void test_Autowired() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-scan.xml");

        DogService dogService = applicationContext.getBean("dogService", DogService.class);
        System.out.println("测试结果：" + dogService.queryDogInfo());
    }


    @Test
    public void test_CgLibBean() {
//        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        try {
            System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/qiangsir/Desktop");

            net.sf.cglib.proxy.MethodInterceptor methodInterceptor = new net.sf.cglib.proxy.MethodInterceptor() {
                /**
                 *
                 * @param obj 代理类对象实例
                 * @param method 被代理的原始方法
                 * @param args 参数
                 * @param methodProxy fastClass机制相关
                 * @return
                 * @throws Throwable
                 */
                @Override
                public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                    System.out.println("invoke " + method.getName() + " before ! ");
                    Object result = methodProxy.invokeSuper(obj, args);

                    System.out.println("invoke " + method.getName() + " after ! ");
                    return result;
                }
            };

            net.sf.cglib.proxy.Enhancer enhancer = new net.sf.cglib.proxy.Enhancer();
            enhancer.setSuperclass(DogService.class);
            enhancer.setCallback((net.sf.cglib.proxy.Callback) methodInterceptor);
            DogService o = (DogService) enhancer.create();
            String aa = o.register("aa");
            System.out.println(aa);
            System.out.println(o);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
