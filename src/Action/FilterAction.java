package Action;

import Bean.GrantBean;
import Bean.SchoolBean;
import DataBase.DBManager;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by el1ven on 2/1/15.
 */
public class FilterAction extends ActionSupport {

    private String filter1;//条件1
    private String filter2;//条件2
    private String filter3;//条件3

    private String userType;//根据不同的userType进行不同的动作

    private ArrayList<GrantBean> grantListAfterFilter; //用于页面显示的grant数据集合
    private ArrayList<SchoolBean> schListForFilter;


    public String execute() throws Exception{

        String result = "";

        try{

                Map request=(Map) ActionContext.getContext().get("request");//创建request对象
                this.grantListAfterFilter = DBManager.getInstance().grantFilterResults(this.filter1, this.filter2, this.filter3, this.userType);
                request.put("grantListAfterFilter", this.grantListAfterFilter);
                this.schListForFilter = DBManager.getInstance().queryForSchOfFilter();
                request.put("schListForFilter", this.schListForFilter);

                result = userType;

        }catch(Exception e){

            result = "fail";

        }

        return result;
    }

    public void setFilter1(String filter1){this.filter1 = filter1;}
    public String getFilter1(){return this.filter1;}

    public void setFilter2(String filter2){this.filter2 = filter2;}
    public String getFilter2(){return this.filter2;}

    public void setFilter3(String filter3){this.filter3 = filter3;}
    public String getFilter3(){return this.filter3;}

    public void setUserType(String userType){this.userType = userType;}
    public String getUserType(){return this.userType;}

    public void setGrantListAfterFilter(ArrayList<GrantBean> grantListAfterFilter){this.grantListAfterFilter = grantListAfterFilter;}
    public ArrayList<GrantBean> getGrantListAfterFilter(){return this.grantListAfterFilter;}

    public void setSchListForFilter(ArrayList<SchoolBean> schListForFilter){this.schListForFilter = schListForFilter;}
    public ArrayList<SchoolBean> getSchListForFilter(){return this.schListForFilter;}
}
