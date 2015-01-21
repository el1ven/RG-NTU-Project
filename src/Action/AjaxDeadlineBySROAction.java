package Action;

import DataBase.DBManager;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by el1ven on 12/1/15.
 */
public class AjaxDeadlineBySROAction extends ActionSupport {

    private String rgid;
    private String date;
    private String content;
    private String info;//判断操作类型

    private HttpServletRequest request;//request对象
    private Map result;//返回json结果
    private Map<String, Object> map;//用于封装结果

    public AjaxDeadlineBySROAction(){
        this.request = ServletActionContext.getRequest();
        this.map = new HashMap<String, Object>();
    }

    public String execute()throws Exception{
        this.info = request.getParameter("info");
        int opt = 0;

        if(this.info.equals("addDeadlineBySRO")){
            this.rgid = request.getParameter("rgid");
            this.date = request.getParameter("date");
            this.content = request.getParameter("content");
            opt = DBManager.getInstance().addOneDeadlineBySRO(this.rgid, this.date, this.content);
        }
        if(opt>0){
            map.put("backInfo", "SRO Add Deadline Successfully!");
        }else{
            map.put("backInfo", "SRO Add Deadline Error!");
        }
        this.setResult(map);
        return SUCCESS;
    }

    public String getRgid() {
        return rgid;
    }

    public void setRgid(String rgid) {
        this.rgid = rgid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
