package Action;

import DataBase.DBManager;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by el1ven on 11/1/15.
 */
public class AjaxDeleteAction extends ActionSupport {

    private String info;//判断操作种类
    private Map result;//返回json结果
    private String idsOfStr;//接受的字符串
    private String [] idsArr;//转化成的数组
    private HttpServletRequest request;//request对象

    private Map<String, Object> map;//用于封装结果

    public AjaxDeleteAction(){
        this.request = ServletActionContext.getRequest();
        this.map = new HashMap<String, Object>();
    }

    public String execute() throws Exception{

        this.info = request.getParameter("info");
        int opt = 0;
        if(this.info.equals("deleteRG")){
            this.idsOfStr = request.getParameter("idsOfStr");
            System.out.println("idsOfStr "+idsOfStr);
            this.idsArr = idsOfStr.split("-");
            opt = DBManager.getInstance().grantDeleteByRSO(idsArr);
            //System.out.println("Opt: "+opt);
        }
        if(opt>0){
            map.put("backInfo", "Delete Successfully!");
        }else{
            map.put("backInfo", "Delete Error!");
        }
        this.setResult(map);
        return SUCCESS;
    }

    public Map getResult() {
        return result;
    }

    public void setResult(Map result) {
        this.result = result;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getIdsOfStr() {
        return idsOfStr;
    }

    public void setIdsOfStr(String idsOfStr) {
        this.idsOfStr = idsOfStr;
    }

    public void setIdsArr(String[] idsArr) {
        this.idsArr = idsArr;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public String[] getIdsArr() {
        return idsArr;
    }

}
