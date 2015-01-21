package Action;

import DataBase.*;
import Bean.UserBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by el1ven on 18/9/14.
 */
public class RegisterAction extends ActionSupport implements ModelDriven {

    private UserBean user;//用户对象属性

    public Object getModel(){//实现ModelDriven接口
        if(user == null){
            user = new UserBean();
        }
        return user;
    }

    public String execute() throws Exception{
        int result = DBManager.getInstance().queryForRegister(user);
        HttpServletRequest request = ServletActionContext.getRequest();
        if(result > 0){
            request.setAttribute("MessageOfRegister", "Register Successfully!");
            return "success";
        }else{
            request.setAttribute("MessageOfRegister", "Register Fail!");
            return "fail";
        }
    }

}