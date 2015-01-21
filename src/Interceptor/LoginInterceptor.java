package Interceptor; /**
 * Created by el1ven on 11/10/14.
 */
import Bean.UserBean;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import java.util.Map;

public class LoginInterceptor extends AbstractInterceptor {
    /**登录拦截器**/
    public String intercept(ActionInvocation invocation) throws Exception{
        //通过ActionInvocation对象来获取ActionContext对象
        HttpServletRequest request = ServletActionContext.getRequest();
        String url = request.getRequestURI();//获取请求url
        //System.out.println(url);
        if(isCheck(url)){
            Map session = invocation.getInvocationContext().getSession();
            UserBean user = (UserBean)session.get("user");
            if(user == null){
                return "toLogin";
            }
        }
        return invocation.invoke();//正常执行
    }


    private boolean isCheck(String path){
        //这些不过滤,直接放行
        if(path.endsWith("/login.action")||path.endsWith("/register.action") || path.endsWith("/publish.action")||path.endsWith("/deadline.action")){
            return false;
        }
        return true;
    }

}
