package Action;

import DataBase.DBManager;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by el1ven on 12/1/15.
 */

public class AjaxMarkAction extends ActionSupport {

    private int rgidForMarkDown;
    private String contentForMarkDown;
    private String info;//判断操作类型

    private HttpServletRequest request;//request对象
    private Map result;//返回json结果
    private Map<String, Object> map;//用于封装结果

    public AjaxMarkAction(){
        this.request = ServletActionContext.getRequest();
        this.map = new HashMap<String, Object>();
    }

    public String execute()throws Exception{
        this.info = request.getParameter("info");
        int opt = 0;

        ActionContext context= ActionContext.getContext();
        Map session = (Map)context.getSession();
        String userName = session.get("userName").toString();//获取session中的用户名，可以根据这个查询提交RG的教授所在的学院
        int userId =  DBManager.getInstance().getUserIdByName(userName);//获取用户ID

        if(this.info.equals("markRG")){
            this.rgidForMarkDown = Integer.parseInt(request.getParameter("rgidForMarkDown"));
            this.contentForMarkDown = request.getParameter("contentForMarkDown");
            opt = DBManager.getInstance().addMarkDownForOutdate(userId, this.rgidForMarkDown, this.contentForMarkDown);
        }
        if(opt>0){
            map.put("backInfo", "MarkDown Successfully!");
        }else{
            map.put("backInfo", "MarkDown Error!");
        }
        this.setResult(map);
        return SUCCESS;
    }

    public int getRgidForMarkDown() {
        return rgidForMarkDown;
    }

    public void setRgidForMarkDown(int rgidForMarkDown) {
        this.rgidForMarkDown = rgidForMarkDown;
    }

    public String getContentForMarkDown() {
        return contentForMarkDown;
    }

    public void setContentForMarkDown(String contentForMarkDown) {
        this.contentForMarkDown = contentForMarkDown;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public Map getResult() {
        return result;
    }

    public void setResult(Map result) {
        this.result = result;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

}
