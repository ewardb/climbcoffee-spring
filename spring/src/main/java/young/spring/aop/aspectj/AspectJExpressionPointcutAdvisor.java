package young.spring.aop.aspectj;

import org.aopalliance.aop.Advice;
import young.spring.aop.Pointcut;
import young.spring.aop.PointcutAdvisor;


/**
 * Spring AOP Advisor that can be used for any AspectJ pointcut expression.
 *
 * 切面
 *
 *
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    // 切点 表达式
    private String expression;
    // 切点载体
    private AspectJExpressionPointcut pointcut;
    // 具体的拦截方法
    private Advice advice;

    public void setExpression(String expression){
        this.expression = expression;
    }


    public void setAdvice(Advice advice){
        this.advice = advice;
    }


    @Override
    public Pointcut getPointcut() {
        if(pointcut == null){
            pointcut = new AspectJExpressionPointcut(expression);
        }
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }
}
