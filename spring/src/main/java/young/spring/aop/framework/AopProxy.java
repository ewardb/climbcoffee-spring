package young.spring.aop.framework;

/**
 * Delegate interface for a configured AOP proxy, allowing for the creation
 * of actual proxy objects.
 *
 * <p>Out-of-the-box implementations are available for JDK dynamic proxies
 * and for CGLIB proxies, as applied by DefaultAopProxyFactory
 *
 * aop 代理的抽象
 */
public interface AopProxy {


    /**
     * 返回代理对象
     * @return
     */
    Object getProxy();
}
