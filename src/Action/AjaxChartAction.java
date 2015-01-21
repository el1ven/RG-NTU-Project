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
 * Created by el1ven on 21/1/15.
 */
public class AjaxChartAction extends ActionSupport {

    private String info;
    private HttpServletRequest request;//request对象
    private Map result;//返回json结果
    private Map<String, Object> map;//用于封装结果
    private List<Map<String, Object>> chartData;//用于DBManage返回的数据

    public AjaxChartAction(){
        this.request = ServletActionContext.getRequest();
        this.chartData = new ArrayList<Map<String, Object>>();
        this.map = new HashMap<String, Object>();
    }

    public String execute()throws Exception{
        this.info = request.getParameter("info");
        if(this.info.equals("infoForChart")){
            chartData = DBManager.getInstance().getChartData();
            map.put("chartData", chartData);
        }
        this.setResult(map);
        return SUCCESS;
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

    public List<Map<String, Object>> getChartData() {
        return chartData;
    }

    public void setChartData(List<Map<String, Object>> chartData) {
        this.chartData = chartData;
    }



}
