package young.spring.bean;

import young.spring.beans.factory.annotation.Autowired;
import young.spring.stereotype.Component;

import java.util.Random;


@Component(value = "dogService")
public class DogService implements IDogService{

    private String token;


    @Autowired
    private UserDao userDao;


    @Override
    public String queryDogInfo() {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return userDao.queryUserName("10001") + "，" + "gou哥，100001，深圳";
    }

    @Override
    public String register(String userName) {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "注册gou：" + userName + " success！";
    }



    @Override
    public String toString() {
        return "dogService#token = { " + token + " }";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

}
