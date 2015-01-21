package Action;

import DataBase.*;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;
import java.util.*;

import net.sf.json.*;

/**
 * Created by el1ven on 26/12/14.
 */
public class SubscribeAction extends ActionSupport {

    private String grantId;//只需要知道点击ResearchGrant的Id即可
    private Map result;//json形式的字符串

    public String execute()throws Exception{

        // 获取 ajax 传递的 grantId值
        HttpServletRequest request = null;
        request = ServletActionContext.getRequest();
        String id = request.getParameter("grantId");

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list = DBManager.getInstance().getSubscirbeData(id);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", list);
        this.setResult(map);

        return SUCCESS;

    }

    public void setGrantId(String grantId){this.grantId = grantId;}
    public String getGrantId(){return this.grantId;}

    public void setResult(Map result){this.result = result;}
    public Map getResult(){return this.result;}


}
