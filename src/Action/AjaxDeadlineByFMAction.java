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
 * Created by el1ven on 14/1/15.
 */
public class AjaxDeadlineByFMAction extends ActionSupport {

    //FM设置deadline
    private int rgid;
    private int userId;
    private String deadline1;
    private String deadline2;
    private String deadline3;
    private String info;//操作信息

    private HttpServletRequest request;//request对象
    private Map result;//返回json结果
    private Map<String, Object> map;//用于封装结果
    private List<Map<String, Object>> list;//用于DBManage返回的数据

    public AjaxDeadlineByFMAction(){
        this.request = ServletActionContext.getRequest();
        this.list = new ArrayList<Map<String, Object>>();
        this.map = new HashMap<String, Object>();
    }

    public String execute()throws Exception{
        this.info = request.getParameter("info");

        if(this.info.equals("getDeadlineDataByFM")){
            this.rgid = Integer.parseInt(request.getParameter("rgid"));
            this.userId = DBManager.getInstance().getUserIdByName(request.getParameter("userName"));
            list = DBManager.getInstance().getDeadlineDataByFM(this.rgid, this.userId);
            this.map.put("list", list);
        }

        if(this.info.equals("setDeadlineDataByFM")){
            this.rgid = Integer.parseInt(request.getParameter("rgid"));
            this.userId = DBManager.getInstance().getUserIdByName(request.getParameter("userName"));
            if(request.getParameter("deadline1").equals("")){
                this.deadline1 = "None";
            }else{
                this.deadline1 = request.getParameter("deadline1");
            }
            if(request.getParameter("deadline2").equals("")){
                this.deadline2 = "None";
            }else{
                this.deadline2 = request.getParameter("deadline2");
            }
            if(request.getParameter("deadline3").equals("")){
                this.deadline3 = "None";
            }else{
                this.deadline3 = request.getParameter("deadline3");
            }
            int opt = 0;
            opt = DBManager.getInstance().setDeadlineDataByFM(this.rgid, this.userId, this.deadline1, this.deadline2, this.deadline3);
            if(opt>0){
                map.put("backInfo", "Delete Successfully!");
            }else{
                map.put("backInfo", "Delete Error!");
            }
        }

        this.setResult(map);
        return SUCCESS;
    }

    public int getRgid() {
        return rgid;
    }

    public void setRgid(int rgid) {
        this.rgid = rgid;
    }

    public String getDeadline1() {
        return deadline1;
    }

    public void setDeadline1(String deadline1) {
        this.deadline1 = deadline1;
    }

    public String getDeadline2() {
        return deadline2;
    }

    public void setDeadline2(String deadline2) {
        this.deadline2 = deadline2;
    }

    public String getDeadline3() {
        return deadline3;
    }

    public void setDeadline3(String deadline3) {
        this.deadline3 = deadline3;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<Map<String, Object>> getList() {
        return list;
    }

    public void setList(List<Map<String, Object>> list) {
        this.list = list;
    }

}
