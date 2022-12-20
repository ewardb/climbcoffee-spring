package young.spring.aop;


/**
 * Core Spring pointcut abstraction.
 *
 * <p>A pointcut is composed of a {@link ClassFilter} and a {@link MethodMatcher}.
 * Both these basic terms and a Pointcut itself can be combined to build up combinations
 * <p>
 *
 */
public interface Pointcut {



    /**
     * Return the ClassFilter for this pointcut.
     *
     * 返回类过滤器
     *
     * @return the ClassFilter (never <code>null</code>)
     */
    ClassFilter getClassFilter();


    /**
     * Return the MethodMatcher for this pointcut.
     *
     *  返回方法匹配器
     *
     * @return the MethodMatcher (never <code>null</code>)
     */
    MethodMatcher getMethodMatcher();



}
