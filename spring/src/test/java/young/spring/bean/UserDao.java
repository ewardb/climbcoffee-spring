package young.spring.bean;

import young.spring.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserDao {

    private static Map<String, String> hashMap = new HashMap<>();


    static {
        hashMap.put("10001", "小傅哥，北京，亦庄");
        hashMap.put("10002", "八杯水，上海，尖沙咀");
        hashMap.put("10003", "阿毛，天津，东丽区");
    }

    public void initDataMethod(){
        System.out.println("执行：init-method");
        hashMap.put("10001", "qiangSir");
        hashMap.put("10002", "八杯水");
        hashMap.put("10003", "阿毛");
    }


    public void destroyDataMethod(){
        System.out.println("执行：destroy-method");
        hashMap.clear();

    }

    public String queryUserName(String uId) {
        return hashMap.get(uId);
    }

}
