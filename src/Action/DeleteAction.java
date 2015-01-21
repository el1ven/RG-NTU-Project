package Action;

import DataBase.*;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by el1ven on 11/12/14.
 */
public class DeleteAction extends ActionSupport {

    private int grantId = 0;

    private String grantIdOfStr = "";

    public void setGrantIdOfStr(String grantIdOfStr){this.grantIdOfStr = grantIdOfStr;}
    public String getGrantIdOfStr(){return this.grantIdOfStr;}

    public String deleteOfRSO() throws Exception{
        int result = 0;
        try {
            String grantIds = ServletActionContext.getRequest().getParameter("ids");//通过这个来获取传到action中的参数
            String[] grantIdArr = null;
            grantIdArr = grantIds.split(",");
            result = DBManager.getInstance().grantDeleteByRSO(grantIdArr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(result > 0){
            return "success";
        }else{
            return "fail";
        }
    }

    public String deleteOfFM() throws Exception{
        int result = 0;
        try {
            String grantIds = ServletActionContext.getRequest().getParameter("ids");//通过这个来获取传到action中的参数
            String[] grantIdArr = null;
            grantIdArr = grantIds.split(",");
            String userName = ServletActionContext.getRequest().getParameter("userName");
            int userId = DBManager.getInstance().getUserIdByName(userName);
            result = DBManager.getInstance().grantDeleteByFM(userId, grantIdArr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(result > 0){
            return "success";
        }else{
            return "fail";
        }
    }

    public String deleteDeadline() throws Exception{

        int result = 0;

        try{
            //获取传递的参数
            HttpServletRequest request = null;
            request = ServletActionContext.getRequest();
            String did = request.getParameter("did");
            this.grantIdOfStr = request.getParameter("grantId");
            result = DBManager.getInstance().deleteDeadline(did);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(result > 0){
            return "success";
        }else {
            return "fail";
        }
    }



    public String deleteFile() throws Exception{

        int result = 0;

        try{
            //获取传递的参数
            HttpServletRequest request = null;
            request = ServletActionContext.getRequest();
            String fid = request.getParameter("fid");
            this.grantIdOfStr = request.getParameter("grantId");
            result = DBManager.getInstance().deleteFile(fid,grantIdOfStr);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(result > 0){
            return "success";
        }else {
            return "fail";
        }
    }

}
