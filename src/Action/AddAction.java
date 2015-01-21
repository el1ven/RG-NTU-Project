package Action;

import DataBase.*;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.corba.se.impl.orb.DataCollectorBase;
import org.apache.struts2.ServletActionContext;

/**
 * Created by el1ven on 17/12/14.
 */
public class AddAction extends ActionSupport {//添加

    public String addGrantByFM() throws Exception{//faculty member添加grantList到购物车

        String grantIds = ServletActionContext.getRequest().getParameter("ids");//通过这个来获取传到action中的参数
        String userName = ServletActionContext.getRequest().getParameter("userName");//通过这个来获取传到action中的参数

        String[] grantIdArr = null;
        grantIdArr = grantIds.split(",");

        int result = DBManager.getInstance().addToListByFM(grantIdArr, userName);

        //System.out.println("ids+++ "+grantIds+"~~~"+grantIdArr.length);

        if(result < 0){
            return "fail";
        }
        return "success";
    }

}
