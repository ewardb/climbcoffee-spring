package young.spring.bean;


import young.spring.factorybean.IUserDao;
import young.spring.beans.BeansException;
import young.spring.beans.factory.*;
import young.spring.context.ApplicationContext;
import young.spring.context.ApplicationContextAware;

public class UserService implements InitializingBean, DisposableBean, BeanNameAware, BeanClassLoaderAware, ApplicationContextAware, BeanFactoryAware {


    private ApplicationContext applicationContext;

    private String beanName;

    private ClassLoader classLoader;

    private BeanFactory beanFactory;


    private String uId;
    private String company;
    private String location;
    private UserDao userDao;

    private IUserDao iUserDao;



    @Override
    public void setApplicationAware(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        System.out.println("applicationContext =" + this.applicationContext);
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) throws BeansException {
        this.classLoader = classLoader;
        System.out.println("classloader =" + this.classLoader);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        System.out.println("beanFactory =" + this.beanFactory);
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName  = beanName;
        System.out.println("beanName =" + this.beanName);
    }


    @Override
    public void destroy() throws Exception {
        System.out.println("执行：UserService.destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("执行：UserService.afterPropertiesSet");
    }


    public String queryUserInfo() {
        return iUserDao.queryUserName(uId) + "," + company + "," + location;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }


    public IUserDao getiUserDao() {
        return iUserDao;
    }

    public void setiUserDao(IUserDao iUserDao) {
        this.iUserDao = iUserDao;
    }
}
