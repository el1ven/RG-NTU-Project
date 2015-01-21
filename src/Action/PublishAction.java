package Action; /**
 * Created by el1ven on 27/10/14.
 */
import DataBase.*;
import Bean.GrantBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;


public class PublishAction extends ActionSupport implements ModelDriven {
    private GrantBean grant;//用户对象属性
    //private ArrayList<GrantBean> grantList = new ArrayList<GrantBean>();//用于页面显示的grant数据集合

    public Object getModel(){//实现ModelDriven接口
        if(grant == null){
            grant = new GrantBean();
        }
        return grant;
    }

    public String execute() throws Exception{
        int result = 0;
        try {
            if(grant!= null){
                result = DBManager.getInstance().grantPost(grant);
            }else{
                return "fail";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(result > 0){
            return "success";
        }else{
            return "fail";
        }
    }

    public String publishAfterModify() throws Exception{
        int result = 0;
        try {
            if(grant!= null){
                result = DBManager.getInstance().grantPostAfterModify(grant);
            }else{
                return "fail";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(result > 0){
            return "success";
        }else{
            return "fail";
        }
    }

}
