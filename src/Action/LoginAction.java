package Action;

import DataBase.*;
import Bean.UserBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by el1ven on 18/9/14.
 */

public class LoginAction extends ActionSupport implements ModelDriven, Runnable {

    private UserBean user;//用户对象属性

    public Object getModel(){//实现ModelDriven接口
        if(user == null){
            user = new UserBean();
        }
        return user;
    }

    public String execute() throws Exception{

        int result = 0;

        try{
             result = DBManager.getInstance().queryForLogin(user);

        }catch(Exception e){
            e.printStackTrace();
        }

        if(result > 0){
            //设置登录session
            ActionContext context= ActionContext.getContext();
            Map session = (Map)context.getSession();
            session.put("userName", user.getUserName());
            session.put("isLogin", "true");
            //获取登录类型，不同用户登录的界面是不一样的
            String userType = DBManager.getInstance().queryForType(user);

            //为了让show函数获取userType然后显示不同界面
            session.put("userType", userType);



            return userType;
        }else{
            return "fail";
        }

    }


    @Override
    public void run() {


        SimpleDateFormat curTime = new SimpleDateFormat("yyyy-MM-dd");
        String curTimeOfStr = curTime.format(new Date());
        System.out.println(curTimeOfStr);

    }
}
