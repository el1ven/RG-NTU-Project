package Action;

import Bean.SchoolBean;
import DataBase.*;
import Bean.GrantBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by el1ven on 28/10/14.
 */
public class ShowAction extends ActionSupport{

    private ArrayList<GrantBean> grantList = new ArrayList<GrantBean>();//用于页面显示的grant数据集合

    private ArrayList<SchoolBean> schListForFilter;//用于显示在界面上方的搜索条件

    //用于分页的属性
    private int pageNow = 1;//当前页面，从第一页开始
    private int pageSize = 10;//页面数据大小
    private int rowCount;//总行数
    private int pageCount;//总页数

    public void pageTest() throws Exception{

        if(rowCount%pageSize == 0){
            pageCount = rowCount/pageSize;
        }else{
            pageCount = rowCount/pageSize + 1;
        }
        if(pageNow < 2){
            pageNow = 1;
        }
        if(pageNow >= pageCount){
            pageNow = pageCount;
        }
    }

    public void getSchool() throws Exception{
        this.schListForFilter = DBManager.getInstance().queryForSchOfFilter();
    }

    public String showRSO() throws Exception{//完成RSO分页并显示出来结果

        try{

            Map request=(Map) ActionContext.getContext().get("request");//创建request对象

            this.rowCount = DBManager.getInstance().grantCount();
            pageTest();

            //System.out.println(this.pageNow+" "+this.pageSize);
            if(pageNow < 2)pageNow = 1;


            ArrayList<GrantBean> list = DBManager.getInstance().grantShowRSO(pageNow,pageSize);

            this.setGrantList(list);//注入
            request.put("grantList", this.getGrantList());//grantlist变量用于前台界面显示

            getSchool();
            //System.out.println("Size:   ***"+this.schListForFilter.size());
            request.put("schListForFilter",this.schListForFilter);//grantlist变量用于前台界面显示



        }catch(Exception e){

            e.printStackTrace();

        }


        ActionContext context= ActionContext.getContext();
        Map session = (Map)context.getSession();
        String userType = session.get("userType").toString();
        //通过session来获取用户类型跳转到相应的显示界面

        return userType;//不同用户跳转到不同界面显示
    }

    public String showRSO2() throws Exception{//完成RSO2分页并显示出来结果

        try{

            Map request=(Map) ActionContext.getContext().get("request");//创建request对象

            this.rowCount = DBManager.getInstance().grantCount2();
            pageTest();

            //System.out.println(this.pageNow+" "+this.pageSize);

            if(pageNow < 2)pageNow = 1;

            ArrayList<GrantBean> list = DBManager.getInstance().grantShowRSO2(pageNow,pageSize);

            this.setGrantList(list);//注入
            request.put("grantList", this.getGrantList());//grantlist变量用于前台界面显示

            getSchool();
            request.put("schListForFilter",this.schListForFilter);//grantlist变量用于前台界面显示

        }catch(Exception e){
            e.printStackTrace();
        }


        ActionContext context= ActionContext.getContext();
        Map session = (Map)context.getSession();
        String userType = session.get("userType").toString();
        //通过session来获取用户类型跳转到相应的显示界面

        return userType;//不同用户跳转到不同界面显示
    }

    public String showSRO() throws Exception{//完成researchgrant分页并显示出来

        try{

            Map request=(Map) ActionContext.getContext().get("request");//创建request对象


            this.rowCount = DBManager.getInstance().grantCount();
            pageTest();

            //System.out.println(this.pageNow+" "+this.pageSize);

            if(pageNow < 2)pageNow = 1;

            ArrayList<GrantBean> list = DBManager.getInstance().grantShowRSO(pageNow,pageSize);//因为都是用的RG，所以函数和RSO一样

            this.setGrantList(list);//注入
            request.put("grantList", this.getGrantList());//grantlist变量用于前台界面显示

            getSchool();
            request.put("schListForFilter",this.schListForFilter);//grantlist变量用于前台界面显示

        }catch(Exception e){

            e.printStackTrace();

        }


        ActionContext context= ActionContext.getContext();
        Map session = (Map)context.getSession();
        String userType = session.get("userType").toString();
        //通过session来获取用户类型跳转到相应的显示界面

        return userType;
    }

    public String showSRO2() throws Exception{//完成researchgrant分页并显示出来

        try{

            Map request=(Map) ActionContext.getContext().get("request");//创建request对象

            this.rowCount = DBManager.getInstance().grantCount2();
            pageTest();

            //System.out.println(this.pageNow+" "+this.pageSize);

            if(pageNow < 2)pageNow = 1;

            ArrayList<GrantBean> list = DBManager.getInstance().grantShowRSO2(pageNow,pageSize);//因为都是用的RG，所以函数和RSO一样

            this.setGrantList(list);//注入
            request.put("grantList", this.getGrantList());//grantlist变量用于前台界面显示

            getSchool();
            request.put("schListForFilter",this.schListForFilter);//grantlist变量用于前台界面显示

        }catch(Exception e){

            e.printStackTrace();

        }



        ActionContext context= ActionContext.getContext();
        Map session = (Map)context.getSession();
        String userType = session.get("userType").toString();
        //通过session来获取用户类型跳转到相应的显示界面

        return userType;
    }





    public String showFM1() throws Exception{//显示FacultyMember part1页面

        try{

            Map request=(Map) ActionContext.getContext().get("request");//创建request对象

            this.rowCount = DBManager.getInstance().grantCount();
            pageTest();

            //System.out.println(this.pageNow+" "+this.pageSize);
            if(pageNow < 2)pageNow = 1;

            ArrayList<GrantBean> list = DBManager.getInstance().grantShowFM1(pageNow,pageSize);

            this.setGrantList(list);//注入
            request.put("grantList", this.getGrantList());//grantlist变量用于前台界面

            getSchool();
            //System.out.println("Size:   ***"+this.schListForFilter.size());
            request.put("schListForFilter",this.schListForFilter);//grantlist变量用于前台界面显示


        }catch (Exception e){

            e.printStackTrace();

        }

        return "FM1";

    }

    public String showFM2() throws Exception{//显示FacultyMember part2页面

        String result = "";

       try{

           Map request=(Map) ActionContext.getContext().get("request");//创建request对象
           ArrayList<GrantBean> list = DBManager.getInstance().grantShowFM2();
           this.setGrantList(list);//注入
           request.put("grantList", this.getGrantList());//grantlist变量用于前台界面
           result = "FM2";


       }catch (Exception e){
           result = "fail";
           e.printStackTrace();
       }

        return result;
    }



    public String showDetail()throws Exception{//点击标题显示每个research grant的detail

        String result = "";

        try{
            String grantId = ServletActionContext.getRequest().getParameter("grantId");//通过这个来获取通过链接传到action中的参数
            int grantIdOfInt = Integer.parseInt(grantId);

            Map request=(Map) ActionContext.getContext().get("request");//创建request对象


            ArrayList<GrantBean> list = DBManager.getInstance().grantDetail(grantIdOfInt);
            this.setGrantList(list);//注入
            request.put("grantList", this.getGrantList());//grantlist变量用于前台界面

            //System.out.println("grantListSize: " + list.size());
            result = "detail";

        }catch(Exception e){

            result = "fail";
            e.printStackTrace();

        }

        return result;
    }

    public String showDetail2()throws Exception{//点击标题显示每个research grant的detail

        String result = "";

        try{

            String grantId = ServletActionContext.getRequest().getParameter("grantId");//通过这个来获取通过链接传到action中的参数
            int grantIdOfInt = Integer.parseInt(grantId);

            Map request=(Map) ActionContext.getContext().get("request");//创建request对象


            ArrayList<GrantBean> list = DBManager.getInstance().grantDetail2(grantIdOfInt);
            this.setGrantList(list);//注入
            request.put("grantList", this.getGrantList());//grantlist变量用于前台界面

            //System.out.println("grantListSize: " + list.size());
            result = "detail";

        }catch (Exception e){

            result = "fail";
            e.printStackTrace();

        }

        return result;
    }

    public String showModify()throws Exception{//修改，现需要显示每个rg的detail，所以和showDetail函数差不多

        try {

            String grantId = ServletActionContext.getRequest().getParameter("grantId");//通过这个来获取通过链接传到action中的参数
            int grantIdOfInt = Integer.parseInt(grantId);

            Map request = (Map) ActionContext.getContext().get("request");//创建request对象


            ArrayList<GrantBean> list = DBManager.getInstance().grantDetail(grantIdOfInt);
            this.setGrantList(list);//注入
            request.put("grantList", this.getGrantList());//grantlist变量用于前台界面
        }catch(Exception e){
            e.printStackTrace();
        }

        return "modify";
    }

    public String showModify2()throws Exception{//show modify after delete one deadline 删除deadline或者是file之后我们还需要回到formModify这个页面！

        String result = "";

        try{

            String grantId = ServletActionContext.getRequest().getParameter("grantId");//通过这个来获取通过链接传到action中的参数
            int grantIdOfInt = Integer.parseInt(grantId);

            Map request=(Map) ActionContext.getContext().get("request");//创建request对象


            ArrayList<GrantBean> list = DBManager.getInstance().grantDetail(grantIdOfInt);
            this.setGrantList(list);//注入
            request.put("grantList", this.getGrantList());//grantlist变量用于前台界面

            result = "modify";

        }catch (Exception e){

            result = "fail";
            e.printStackTrace();

        }


        return result;
    }

    public String showModify3()throws Exception{//show modify after delete one file 删除file之后我们还需要回到formModify这个页面！

        String result = "";

        try{

            String grantId = ServletActionContext.getRequest().getParameter("grantId");//通过这个来获取通过链接传到action中的参数
            int grantIdOfInt = Integer.parseInt(grantId);

            Map request=(Map) ActionContext.getContext().get("request");//创建request对象


            ArrayList<GrantBean> list = DBManager.getInstance().grantDetail(grantIdOfInt);
            this.setGrantList(list);//注入
            request.put("grantList", this.getGrantList());//grantlist变量用于前台界面

            result = "modify";


        }catch(Exception e){

            result = "fail";
            e.printStackTrace();
        }

        return result;
    }

    public void setGrantList(ArrayList<GrantBean> grantList){this.grantList = grantList;}
    public ArrayList<GrantBean> getGrantList(){return grantList;}

    public void setSchListForFilter(ArrayList<SchoolBean> schListForFilter){this.schListForFilter = schListForFilter;}
    public ArrayList<SchoolBean> getSchListForFilter(){return this.schListForFilter;}

    public void setPageNow(int pageNow){this.pageNow = pageNow;}
    public int getPageNow(){return this.pageNow;}

    public void setPageSize(int pageSize){this.pageSize = pageSize;}
    public int getPageSizeO(){return this.pageSize;}

    public void setRowCount(int rowCount){this.rowCount = rowCount;}
    public int getRowCount(){return this.rowCount;}

    public void setPageCount(int pageCountForShowRSO){this.pageCount = pageCountForShowRSO;}
    public int getPageCount(){return this.pageCount;}

}
