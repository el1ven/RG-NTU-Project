package Action;

import DataBase.*;
import Bean.SchoolBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by el1ven on 24/12/14.
 */
public class SchoolAction extends ActionSupport {
    private ArrayList<SchoolBean> schList;

    public String execute()throws Exception{
        Map request=(Map) ActionContext.getContext().get("request");//创建request对象
        this.schList = DBManager.getInstance().queryForSch();
        request.put("schList",this.schList);//grantlist变量用于前台界面显示
        return "success";
    }

    public void setSchList(ArrayList<SchoolBean> schList){this.schList = schList;}
    public ArrayList<SchoolBean> getSchList(){return this.schList;}
}
