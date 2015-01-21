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
 * Created by el1ven on 6/1/15.
 */
public class FullTextAction extends ActionSupport {

    private String userType;//用户类型
    private String searchType;//搜索的类型
    private Map result;//json形式的字符串
    private Map resultOfTotalCount;//json形式的字符串2
    private HttpServletRequest request;//request对象
    private List<Map<String, Object>> list;//用于存储取出来的结果
    private Map<String, Object> map;//用于封装结果
    private String condition;//fulltext搜索条件，可以按照title, series, content来进行搜索
    private String status;//搜索Fresh还是Archive状态的数据

    //用于分页的属性

    private int pageNow = 1;//当前页面，从第一页开始
    private int pageSize = 3;//页面数据大小

    private int rowCount;//总行数
    private int pageCount;//总页数


    public FullTextAction() throws Exception {

        this.request = ServletActionContext.getRequest();
        this.list = new ArrayList<Map<String, Object>>();
        this.map = new HashMap<String, Object>();


    }

    public String execute()throws Exception{

        // 获取 ajax 传递的 grantId值

        this.userType = request.getParameter("userType");
        this.searchType = request.getParameter("searchType");
        this.pageNow = Integer.parseInt(request.getParameter("pageNow"));
        this.condition = request.getParameter("searchCondition");

        if(userType.equals("RSO") || userType.equals("SRO")){
            this.status = request.getParameter("searchStatus");
        }

        //传进去不同种类的参数，好确定不同种类的search
        this.rowCount = DBManager.getInstance().grantCountForFullText(this.userType, this.searchType, this.condition, this.status);
        list = DBManager.getInstance().getSearchData(this.userType, this.searchType, this.pageNow, this.pageSize, this.condition, this.status);
        map.put("list", list);
        map.put("totalCount", String.valueOf(this.rowCount));
        this.setResult(map);


        return SUCCESS;

    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public Map getResult() {
        return result;
    }

    public void setResult(Map result) {
        this.result = result;
    }

    public List<Map<String, Object>> getList() {
        return list;
    }

    public void setList(List<Map<String, Object>> list) {
        this.list = list;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public int getPageNow() {
        return pageNow;
    }

    public void setPageNow(int pageNow) {
        this.pageNow = pageNow;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public Map getResultOfTotalCount() {
        return resultOfTotalCount;
    }

    public void setResultOfTotalCount(Map resultOfTotalCount) {
        this.resultOfTotalCount = resultOfTotalCount;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
