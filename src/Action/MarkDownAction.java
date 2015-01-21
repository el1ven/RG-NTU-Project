package Action;

import DataBase.DBManager;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import java.util.Map;

/**
 * Created by el1ven on 5/1/15.
 */
public class MarkDownAction extends ActionSupport {

    private String rgidOfStr;
    private String markDown = "Haven't Yet";

    public void setRgidOfStr(String rgidOfStr){ this.rgidOfStr = rgidOfStr;}
    public String getRgidOfStr(){return this.rgidOfStr;}

    public void setMarkDown(String markDown){ this.markDown = markDown;}
    public String getMarkDown(){return this.markDown;}

    public String execute() throws Exception{

        ActionContext context= ActionContext.getContext();
        Map session = (Map)context.getSession();
        String userType = session.get("userType").toString();
        String userName = session.get("userName").toString();
        int userIdOfInt = DBManager.getInstance().getUserIdByName(userName);
        int rgidOfInt = Integer.parseInt(rgidOfStr);

        int result = 0;

        result = DBManager.getInstance().addMarkDownForOutdate(userIdOfInt, rgidOfInt, this.markDown);

        if(result > 0){
            return userType;
        }else{
            return "fail";
        }
    }


}
