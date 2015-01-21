package DataBase;

import Action.EmailAction;
import Bean.GrantBean;
import Bean.SchoolBean;
import Bean.UserBean;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;

/**
 * Created by el1ven on 25/9/14.
 */
import java.sql.*;
import java.util.*;
import java.io.*;
import java.text.*;
import java.util.Date;

public class DBManager{//设计成单例模式

    private static DBManager managerDB = null;//单例实例变量

    private static SimpleConnectionPool connPool;
    private PreparedStatement pstat = null;
    private PreparedStatement pstat1 = null;
    private PreparedStatement pstat2 = null;
    private PreparedStatement pstat3 = null;
    private PreparedStatement pstat4 = null;
    private PreparedStatement pstat5 = null;
    private PreparedStatement pstat6 = null;
    private PreparedStatement pstat7 = null;
    private PreparedStatement pstat8 = null;
    private PreparedStatement pstat9 = null;
    private PreparedStatement pstat10 = null;

    private static String flagForSendEmail = "No";//全局变量检查是否发邮件

    public String getFlagForSendEmail() {
        return flagForSendEmail;
    }

    public void setFlagForSendEmail(String flagForSendEmail) {
        this.flagForSendEmail = flagForSendEmail;
    }



    private DBManager(){//私有化的构造方法，保证外部的类不能通过构造器来实例化
        connPool = new SimpleConnectionPool();//初始化连接池对象
    }

    public static DBManager getInstance(){//获取单例对象实例
        if(managerDB == null){
            managerDB = new DBManager();
        }
        return managerDB;
    }

    public ArrayList<SchoolBean> queryForSch() throws Exception{

        ArrayList<SchoolBean> schList = new ArrayList<SchoolBean>();
        Connection conn = connPool.getConnection();
        String sql1 = "select * from School";
        pstat = conn.prepareStatement(sql1);
        ResultSet rs = pstat.executeQuery();
        while(rs.next()){
            SchoolBean sch = new SchoolBean();
            sch.setSchId(rs.getInt(1));
            sch.setSchName(rs.getString("name"));
            schList.add(sch);
        }
        //System.out.println("School Size: "+schList.size());
        conn.close();
        rs.close();
        return schList;
    }

    public ArrayList<SchoolBean> queryForSchOfFilter() throws Exception{

        ArrayList<SchoolBean> schList = new ArrayList<SchoolBean>();
        Connection conn = connPool.getConnection();


        try{

            String sql1 = "select * from School";
            pstat7 = conn.prepareStatement(sql1);
            ResultSet rs = pstat7.executeQuery();
            while(rs.next()){
                SchoolBean sch = new SchoolBean();
                if(!rs.getString("name").equals("None")){
                    sch.setSchId(rs.getInt(1));
                    sch.setSchName(rs.getString("name"));
                    schList.add(sch);
                }
            }
            //System.out.println("School Size: "+schList.size());
            pstat7.close();
            rs.close();

        }catch(Exception e){
            e.printStackTrace();
        }



        connPool.pushConnectionBackToPool(conn);

        return schList;
    }

    public int queryForLogin(UserBean user) throws Exception{
        Connection conn = connPool.getConnection();
        String sql = "select * from User where name=? and password=?";
        pstat = conn.prepareStatement(sql);
        pstat.setString(1,user.getUserName());
        pstat.setString(2,user.getUserPassword());
        ResultSet rs = pstat.executeQuery();
        if(rs.next()){//登录成功
            connPool.pushConnectionBackToPool(conn);
            return 1;
        }else{//登录失败，没有这个用户名，重新注册
            connPool.pushConnectionBackToPool(conn);
            return -1;
        }
    }

    public String queryForType(UserBean user) throws Exception{
        Connection conn = connPool.getConnection();
        String sql = "select * from User where name=? and password=?";
        pstat = conn.prepareStatement(sql);
        pstat.setString(1,user.getUserName());
        pstat.setString(2,user.getUserPassword());
        ResultSet rs = pstat.executeQuery();
        if(rs.next()){//登录成功
            connPool.pushConnectionBackToPool(conn);
            return rs.getString("type");
        }else{//登录失败，没有这个用户名，重新注册
            connPool.pushConnectionBackToPool(conn);
            return "NULL";
        }
    }

    public int queryForRegister(UserBean user)throws Exception{
        Connection conn = connPool.getConnection();
        String sql = "select * from User where name=?";
        pstat = conn.prepareStatement(sql);
        pstat.setString(1, user.getUserName());
        ResultSet rs = pstat.executeQuery();
        if(rs.next()){//用户名已经重复，注册失败
            connPool.pushConnectionBackToPool(conn);
            return -1;
        }else{//用户名没有重复继续注册
            String sql1 = "insert into User(name,password,email,type)values(?,?,?,?)";
            pstat = conn.prepareStatement(sql1);
            pstat.setString(1,user.getUserName());
            pstat.setString(2,user.getUserPassword());
            pstat.setString(3,user.getUserEmail());
            pstat.setString(4,user.getUserType());
            //pstat.setString(5,user.getUserSchool1());
            //pstat.setString(6,user.getUserSchool2());
            //pstat.setString(7,user.getUserSchool3());
            pstat.executeUpdate();

            try{

                ArrayList<String> schList = new ArrayList<String>();
                schList.add(user.getUserSchool1());
                schList.add(user.getUserSchool2());
                schList.add(user.getUserSchool3());
                //System.out.println("!!!!   "+schList.size());


                for(int i = 0; i < 3; i++){
                    String sql2 = "insert into UrelateS(uid, sid)values(?,?)";
                    int userIdOfInt = DBManager.getInstance().getUserIdByName(user.getUserName());
                    //System.out.println("School Name   "+schList.get(i));
                    int schIdOfInt = DBManager.getInstance().getSchoolIdByName(schList.get(i));
                    pstat = conn.prepareStatement(sql2);
                    pstat.setInt(1, userIdOfInt);
                    pstat.setInt(2, schIdOfInt);
                    pstat.executeUpdate();
                    //System.out.println("!!!!!!!!!!!!!!!!!!!+  "+i);
                }

            }catch(Exception e){
                e.printStackTrace();
            }

            //防止上传文件的覆盖问题，只要用户第一次注册成功就会在upload下面创建一个具有改id的
            /*int count = 1;
            if(count == 1){
                String sql3 = "select * from User where name=?";
                pstat = conn.prepareStatement(sql3);
                pstat.setString(1, user.getUserName());
                ResultSet rs2 = pstat.executeQuery();
                rs2.next();
                int userId = rs2.getInt(1);
                String userIdOfString = String.valueOf(userId);
                String userType = rs2.getString("type");
                rs.close();
                if(!userType.equals("FM")){
                    String basePath = ServletActionContext.getServletContext().getRealPath("/");
                    String finalPath = basePath + "upload/" + userIdOfString;//获取文件上传路径
                    File file = new File(finalPath);
                    file.mkdir();
                }
                count = 2;
            }*/

            connPool.pushConnectionBackToPool(conn);
            pstat = null;
            rs.close();

            return 1;
        }
    }

    public String queryForLastestDeadline(String currentTime) throws Exception{
        Connection conn = connPool.getConnection();
        String sql1 = "select * from ResearchGrant where status='fresh'";
        pstat = conn.prepareStatement(sql1);
        ResultSet rs1 = pstat.executeQuery();
        while(rs1.next()){
            //针对每一个RG的deadline找出最近的时间和当前时间对比
            int rgid = rs1.getInt("rgid");
            String sql2 = "select * from Deadline where rgid=? order by dtime desc";
            pstat2 = conn.prepareStatement(sql2);
            pstat2.setInt(1, rgid);
            ResultSet rs2 = pstat2.executeQuery();
            rs2.next();//只取一条最近的一条
            String lastestDeadline = rs2.getString("dtime");
            rs2.close();

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = format.parse(lastestDeadline);
            Date d2 = format.parse(currentTime);
            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
            c1.setTime(d1);
            c2.setTime(d2);
            if(d1.before(d2)||d1.equals(d2)){
                //过期要修改数据库字段为“outdate”
                String sql3 = "update ResearchGrant set status = 'outdate' where rgid=?";
                pstat3 = conn.prepareStatement(sql3);
                pstat3.setInt(1, rgid);
                pstat3.executeUpdate();
            }
        }
        rs1.close();
        pstat = null;
        pstat = null;
        pstat = null;
        connPool.pushConnectionBackToPool(conn);
        return "success";
    }

    public void sendEmail2(String email, String deadline, String info, GrantBean g) throws Exception{
        if(info.equals("sendBeforeRSO")){

            EmailAction e = new EmailAction(email, deadline, info, g);//把参数传递进去
            Thread th = new Thread(e);
            th.start();
            System.out.println("Send Email Before RSO OK!");//已经测试成功发了邮件～

        }else if(info.equals("sendBeforeSRO")){

            EmailAction e = new EmailAction(email, deadline, info, g);//把参数传递进去
            Thread th = new Thread(e);
            th.start();
            System.out.println("Send Email Before SRO OK!");//已经测试成功发了邮件～

        }else{

            EmailAction e = new EmailAction(email, deadline, info, g);//把参数传递进去
            Thread th = new Thread(e);
            th.start();
            System.out.println("Send Email WHhen FM OK!");//已经测试成功发了邮件～

        }
    }

    public void sendEmail(int rgid, String info) throws Exception{

        //info用来判断什么操作

        Connection conn = connPool.getNewConnection();

        //ArrayList<String> uidOfNeedSend = new ArrayList<String>();
        ArrayList<String> emailOfNeedSend = new ArrayList<String>();


        try{

            //选出订阅这个RG的人的信息包括email

            if(info.equals("sendAfterModify")){

                String sql1 = "select * from UsubscribeRG a, User b where a.uid=b.uid and b.type='FM' and a.rgid=?";
                pstat = conn.prepareStatement(sql1);
                pstat.setInt(1, rgid);
                ResultSet rs1 = pstat.executeQuery();
                while(rs1.next()){
                    emailOfNeedSend.add(rs1.getString("email"));
                }

                pstat.close();
                rs1.close();

                //把这个RG的具体信息查找出来
                String sql2 = "select * from ResearchGrant where rgid=?";
                pstat2 = conn.prepareStatement(sql2);
                pstat2.setInt(1, rgid);
                ResultSet rs2 = pstat2.executeQuery();
                GrantBean g = new GrantBean();
                while(rs2.next()){
                    //title, description, series, deadlines
                    g.setGrantIdOfStr(String.valueOf(rgid));
                    g.setGrantTitle(rs2.getString("title"));
                    g.setGrantSeries(rs2.getString("series"));
                    g.setGrantContent(rs2.getString("content"));
                }

                EmailAction e = new EmailAction(emailOfNeedSend, g, info);//把参数传递进去
                Thread th = new Thread(e);
                th.start();
                System.out.println("Send Email OK!");//已经测试成功发了邮件～

                rs2.close();
                pstat2.close();

            }

            if(info.equals("sendAfterCreate")){

                String sql1 = "select * from User where type!='RSO'";
                pstat = conn.prepareStatement(sql1);
                ResultSet rs1 = pstat.executeQuery();
                while(rs1.next()){
                    emailOfNeedSend.add(rs1.getString("email"));
                }

                pstat.close();
                rs1.close();

                //把这个RG的具体信息查找出来
                String sql2 = "select * from ResearchGrant where rgid=?";
                pstat2 = conn.prepareStatement(sql2);
                pstat2.setInt(1, rgid);
                ResultSet rs2 = pstat2.executeQuery();
                GrantBean g = new GrantBean();
                while(rs2.next()){
                    //title, description, series, deadlines
                    g.setGrantIdOfStr(String.valueOf(rgid));
                    g.setGrantTitle(rs2.getString("title"));
                    g.setGrantSeries(rs2.getString("series"));
                    g.setGrantContent(rs2.getString("content"));
                }


                EmailAction e = new EmailAction(emailOfNeedSend, g, info);//把参数传递进去
                Thread th = new Thread(e);
                th.start();
                System.out.println("Send Email OK2!");//已经测试成功发了邮件～

                rs2.close();
                pstat2.close();

            }


        }catch (Exception e){
            e.printStackTrace();
        }



        connPool.pushConnectionBackToPool(conn);
    }

    public int grantPost(GrantBean grant)throws Exception{
        Connection conn = connPool.getConnection();

        ActionContext context= ActionContext.getContext();
        Map session = (Map)context.getSession();
        String userName = session.get("userName").toString();//获取sesssion中的用户名，可以根据这个查询用户的ID
        String userIdOfString = String.valueOf(DBManager.getInstance().getUserIdByName(userName));

        //第一步先添加Research Grant这个表
        String timeNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        String sql1 = "insert into ResearchGrant(status,title,series,contact,content,present)values(?,?,?,?,?,?)";
        pstat = conn.prepareStatement(sql1);
        pstat.setString(1, grant.getGrantStatus());
        pstat.setString(2, grant.getGrantTitle());
        pstat.setString(3, grant.getGrantSeries());
        pstat.setString(4, grant.getGrantContact());
        pstat.setString(5, grant.getGrantContent());
        pstat.setString(6, timeNow);//grant 发布时间
        pstat.executeUpdate();

        //利用插入时间来获取发布的rgid
        String sql2 = "select rgid from ResearchGrant where present=?";
        pstat = conn.prepareStatement(sql2);
        pstat.setString(1, timeNow);
        ResultSet rs = pstat.executeQuery();
        int rgid = 0;
        while(rs.next()) {
            rgid = rs.getInt(1);
        }

        //根据rgid来插入文件名

        String basePath = ServletActionContext.getServletContext().getRealPath("/");

        //第一次发布grant需要创建以rgid为文件名的存上传文件,并且只创建一次文件夹
        int countOfLoop = 1;
        if(countOfLoop == 1){
            String finalPath = basePath + "upload/" + rgid;//获取文件上传路径
            File file = new File(finalPath);
            file.mkdir();
            countOfLoop = 2;
        }

        String finalPath = basePath + "upload/" + rgid + "/";//获取文件上传路径
        String fileName = "";
        String fileName2 = "";
        String filePath = "";

        if(grant.getUpload() != null) {//上传文件不为空
            for (int i = 0; i < grant.getUpload().size(); i++) {

                //文件多个上传到文件夹
                InputStream is = new FileInputStream(grant.getUpload().get(i));
                fileName = grant.getUploadFileName().get(i);
                fileName2 = fileName.replace(" ", "~");
                filePath = finalPath + fileName;
                //filePath2 = "\""+finalPath+fileName+"\"";
                OutputStream os = new FileOutputStream(filePath);
                byte[] buffer = new byte[8192];
                int count;
                while ((count = is.read(buffer)) > 0) {
                    os.write(buffer, 0, count);
                }
                os.close();
                is.close();

                //文件名和文件路径写入数据库
                String sql3 = "insert into File(rgid,fileName,filePath,fileName2)values(?,?,?,?)";
                pstat = conn.prepareStatement(sql3);
                pstat.setInt(1, rgid);
                pstat.setString(2, fileName);
                pstat.setString(3, filePath);
                pstat.setString(4, fileName2);
                pstat.executeUpdate();
            }
        }else{
            System.out.println("上传文件为空！");
        }

        //把多个deadline数据插入到数据库中， 把user创建deadline之间的关系存入数据库的表中
        if(grant.getDeadlineDates()!=null){
            for(int i = 0; i < grant.getDeadlineDates().size(); i++){
                String timeNow2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                String deadlineDate = grant.getDeadlineDates().get(i);
                String deadlineContent = grant.getDeadlineContents().get(i);
                String sql4 = "insert into Deadline(rgid, dtime, description, present)values(?,?,?,?)";
                pstat = conn.prepareStatement(sql4);
                pstat.setInt(1, rgid);
                pstat.setString(2, deadlineDate);
                pstat.setString(3, deadlineContent);
                pstat.setString(4, timeNow2);
                pstat.executeUpdate();

                String sql4_1 = "select * from Deadline where present=?";
                pstat = conn.prepareStatement(sql4_1);
                pstat.setString(1, timeNow2);
                ResultSet rs1 = pstat.executeQuery();
                rs1.next();//就一条数据

                int didOfInt = rs1.getInt("did");//获取did
                int userIdOfInt = DBManager.getInstance().getUserIdByName(userName);//获取uid

                String sql4_2 = "insert into UcreateD(did, uid)values(?,?)";//插入到数据库中
                pstat = conn.prepareStatement(sql4_2);
                pstat.setInt(1, didOfInt);
                pstat.setInt(2, userIdOfInt);
                pstat.executeUpdate();
            }
        }




        //把user和grant对应关系存入UmanageRG表中，即纪录哪个人发布了这个research grant
        int userId = 0;

        String sql5 = "select uid from User where name=?";
        pstat = conn.prepareStatement(sql5);
        pstat.setString(1, userName);

        ResultSet rs2 = pstat.executeQuery();
        while(rs2.next()){
            userId = rs2.getInt(1);
            //System.out.println(userId);
        }

        //把关系插入到UmanageRG之中
        String sql6 = "insert into UmanageRG(uid, rgid)values(?,?)";
        pstat = conn.prepareStatement(sql6);
        pstat.setInt(1, userId);
        pstat.setInt(2, rgid);
        pstat.executeUpdate();

        this.flagForSendEmail = "Yes";
        if(this.flagForSendEmail.equals("Yes")){
            sendEmail(rgid, "sendAfterCreate");
            this.flagForSendEmail = "No";
        }

        pstat.close();
        connPool.pushConnectionBackToPool(conn);

        return 1;//发布成功
    }


    public int grantPostAfterModify(GrantBean grant)throws Exception{//修改之后重新发布research grant信息

        Connection conn = connPool.getConnection();

        ActionContext context= ActionContext.getContext();
        Map session = (Map)context.getSession();
        String userName = session.get("userName").toString();//获取sesssion中的用户名，可以根据这个查询用户的ID

        int grantId = Integer.parseInt(grant.getGrantIdOfStr());
        String sql1 = "update ResearchGrant set title=?, series=?, content=?, contact=? where rgid=?";
        pstat = conn.prepareStatement(sql1);
        pstat.setString(1, grant.getGrantTitle());
        pstat.setString(2, grant.getGrantSeries());
        pstat.setString(3, grant.getGrantContent());
        pstat.setString(4, grant.getGrantContact());
        pstat.setInt(5, grantId);
        pstat.executeUpdate();


        //根据rgid来插入文件名

        String basePath = ServletActionContext.getServletContext().getRealPath("/");
        String finalPath = basePath + "upload/" + grantId + "/";//获取文件上传路径
        String fileName = "";
        String filePath = "";

        if(grant.getUpload() != null) {//上传文件不为空
            for (int i = 0; i < grant.getUpload().size(); i++) {

                //文件多个上传到文件夹
                InputStream is = new FileInputStream(grant.getUpload().get(i));
                fileName = grant.getUploadFileName().get(i);
                filePath = finalPath + fileName;
                OutputStream os = new FileOutputStream(filePath);
                byte[] buffer = new byte[8192];
                int count;
                while ((count = is.read(buffer)) > 0) {
                    os.write(buffer, 0, count);
                }
                os.close();
                is.close();


                if(fileName!=""||filePath!=""){
                    //文件名和文件路径写入数据库
                    String sql3 = "insert into File(rgid,fileName,fileName2,filePath)values(?,?,?,?)";
                    pstat = conn.prepareStatement(sql3);
                    pstat.setInt(1, grantId);
                    pstat.setString(2, fileName);
                    pstat.setString(3, fileName.replace(" ", "~"));
                    pstat.setString(4, filePath);
                    pstat.executeUpdate();
                }


            }
        }

        //把多个deadline数据插入到数据库中
        if(grant.getDeadlineDates()!=null){
            //System.out.println(")))))  "+grant.getDeadlineContents().size());
            for(int i = 0; i < grant.getDeadlineDates().size(); i++){
                String timeNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                String deadlineDate = grant.getDeadlineDates().get(i);
                String deadlineContent = grant.getDeadlineContents().get(i);
                if(deadlineDate!=""||deadlineContent!=""){
                    String sql4 = "insert into Deadline(rgid, dtime, description, present)values(?,?,?,?)";
                    pstat = conn.prepareStatement(sql4);
                    pstat.setInt(1, grantId);
                    pstat.setString(2, deadlineDate);
                    pstat.setString(3, deadlineContent);
                    pstat.setString(4, timeNow);
                    pstat.executeUpdate();

                    String sql4_1 = "select * from Deadline where present=?";
                    pstat = conn.prepareStatement(sql4_1);
                    pstat.setString(1, timeNow);
                    ResultSet rs1 = pstat.executeQuery();
                    rs1.next();//就一条数据

                    int didOfInt = rs1.getInt("did");//获取did
                    int userIdOfInt = DBManager.getInstance().getUserIdByName(userName);//获取uid

                    String sql4_2 = "insert into UcreateD(did, uid)values(?,?)";//插入到数据库中
                    pstat = conn.prepareStatement(sql4_2);
                    pstat.setInt(1, didOfInt);
                    pstat.setInt(2, userIdOfInt);
                    pstat.executeUpdate();

                }
            }
        }

        this.flagForSendEmail = "Yes";
        if(this.flagForSendEmail.equals("Yes")){
            sendEmail(grantId, "sendAfterModify");
            this.flagForSendEmail = "No";
        }

        pstat.close();
        connPool.pushConnectionBackToPool(conn);

        return 1;
    }



    //取出要分页显示的元素放在grantList之中返回
    public ArrayList<GrantBean> grantShowRSO(int pageNow, int pageSize) throws Exception{

        //System.out.println(pageNow+"*"+pageSize);

        String latestDate = "";//最近deadline日期
        String schStr = "";//存储发表这个grant的教授所在的学院
        //String peopleCountOfString = "";//订阅人数统计,字符串类型
        int peopleCountOfInt = 0;//订阅人数统计,整数类型

        //查询用户类型，有的不需要显示deadline和file有的需要
        ActionContext context= ActionContext.getContext();
        Map session = (Map)context.getSession();
        String userType = session.get("userType").toString();
        String userName = session.get("userName").toString();//获取sesssion中的用户名，可以根据这个查询提交RG的教授所在的学院
        //System.out.println("UserName--"+userName);

        Connection conn = connPool.getConnection();
        Connection conn2 = connPool.getNewConnection();
        Connection conn3 = connPool.getNewConnection();
        ArrayList<GrantBean> grantList = new ArrayList<GrantBean>();
        String sql = "select * from ResearchGrant where status='fresh' limit ?,?";//只有没过期的grant才能被选择被显示
        pstat = conn.prepareStatement(sql);
        //noinspection JpaQueryApiInspection
        if(pageNow < 2)pageNow = 1;
        pstat.setInt(1, (pageNow-1)*pageSize);
        pstat.setInt(2, pageNow*pageSize);
        ResultSet rs = pstat.executeQuery();


        //int count = 1;//防止多次循环
        int rgid = 0;

        while(rs.next()){

            GrantBean g = new GrantBean();//简略的显示信息
            rgid = rs.getInt("rgid");
            g.setGrantId(rgid);
            g.setGrantTitle(rs.getString("title"));
            g.setGrantSeries(rs.getString("series"));
            g.setGrantWhen(rs.getString("present"));
            g.setGrantStatus(rs.getString("status"));

            String sql1 = "select uid from UsubscribeRG where rgid=?";
            pstat1 = conn.prepareStatement(sql1);
            pstat1.setInt(1, rgid);
            ResultSet rs1 = pstat1.executeQuery();

            while(rs1.next()){
                peopleCountOfInt++;
            }
            String peopleCount = String.valueOf(peopleCountOfInt);
            peopleCountOfInt = 0;//复原

            g.setGrantPeopleCount(peopleCount);

            pstat1.close();
            rs1.close();

            //查出id,以此来查出deadline和file
            if(userType.equals("RSO")) {
                try {

                    String sql2 = "select * from Deadline where rgid=? order by dtime ";
                    pstat2 = conn2.prepareStatement(sql2);
                    pstat2.setInt(1, rgid);
                    ResultSet rs2 = pstat2.executeQuery();
                    pstat2 = null;
                    if(rs2.next()) {
                        latestDate = rs2.getString("dtime");
                        //g.setHurryDeadline(latestDate);
                        //这种方法可以查出最近的deadline
                    }else{
                        latestDate = "Haven't Yet.";
                    }

                    rs2.close();

                    int userId = DBManager.getInstance().getUserIdByRGID(rgid);

                    String sql3 = "select name from UrelateS a, School b where a.uid = ? and a.sid = b.sid";//根据session提供的姓名来查询提交者所在的学院
                    pstat3 = conn3.prepareStatement(sql3);
                    pstat3.setInt(1, userId);
                    ResultSet rs3 = pstat3.executeQuery();
                    pstat3 = null;
                    ArrayList<String> sch = new ArrayList<String>();

                    /*查询发表这个research grant的学院*/
                    while (rs3.next()) {
                        String schName = rs3.getString("name");
                        sch.add(schName);
                    }
                    for(int i = 0; i < sch.size(); i++){
                        if(!(sch.get(i)).equals("None")){//用这个函数来判断字符串是否相等
                            schStr += sch.get(i)+" ";
                        }
                    }
                    rs3.close();
                    /*查询发表这个research grant的学院*/

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            if(userType.equals("SRO")) {
                try {

                    String sql2 = "select * from Deadline a, UcreateD b, User c where a.did=b.did and b.uid=c.uid and c.type='SRO' and rgid=? order by present desc";
                    pstat2 = conn2.prepareStatement(sql2);
                    pstat2.setInt(1, rgid);
                    ResultSet rs2 = pstat2.executeQuery();
                    pstat2 = null;
                    if(rs2.next()){
                        //SRO的deadline只能添加一次 所以选出present最新的，也就是最近修改的，就不用做修改功能了
                        latestDate = rs2.getString("dtime");
                    }else{
                        latestDate = "Haven't Yet.";
                    }

                    rs2.close();

                    int userId = DBManager.getInstance().getUserIdByRGID(rgid);

                    String sql3 = "select name from UrelateS a, School b where a.uid = ? and a.sid = b.sid";//根据session提供的姓名来查询提交者所在的学院
                    pstat3 = conn3.prepareStatement(sql3);
                    pstat3.setInt(1, userId);
                    ResultSet rs3 = pstat3.executeQuery();
                    ArrayList<String> sch = new ArrayList<String>();

                    /*查询发表这个research grant的学院*/
                    while (rs3.next()) {
                        String schName = rs3.getString("name");
                        sch.add(schName);
                    }
                    for(int i = 0; i < sch.size(); i++){
                        if(!(sch.get(i)).equals("None")){//用这个函数来判断字符串是否相等
                            schStr += sch.get(i)+" ";
                        }
                    }
                    rs3.close();
                    pstat3 = null;
                    /*查询发表这个research grant的学院*/

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }





            g.setHurryDeadline(latestDate);//注入最近截至日期
            g.setGrantSchool(schStr);//注入发表这个grant的教授的学院，已经组合成一个字符串
            schStr = "";

            grantList.add(g);//这步已经获取到分页之前的元素

        }

        rs.close();
        pstat.close();

        connPool.pushConnectionBackToPool(conn);
        connPool.pushConnectionBackToPool(conn2);
        connPool.pushConnectionBackToPool(conn3);

        return grantList;
    }

    //取出要分页显示的元素放在grantList之中返回
    public ArrayList<GrantBean> grantShowRSO2(int pageNow, int pageSize) throws Exception{

        ArrayList<GrantBean> grantList = new ArrayList<GrantBean>();

        try{

            String latestDate = "";//最近deadline日期
            String schStr = "";//存储发表这个grant的教授所在的学院
            //String peopleCountOfString = "";//订阅人数统计,字符串类型
            int peopleCountOfInt = 0;//订阅人数统计,整数类型

            //查询用户类型，有的不需要显示deadline和file有的需要
            ActionContext context= ActionContext.getContext();
            Map session = (Map)context.getSession();
            String userType = session.get("userType").toString();
            String userName = session.get("userName").toString();//获取sesssion中的用户名，可以根据这个查询提交RG的教授所在的学院
            //System.out.println("UserName--"+userName);

            Connection conn = connPool.getConnection();
            Connection conn2 = connPool.getNewConnection();
            Connection conn3 = connPool.getNewConnection();

            String sql = "select * from ResearchGrant where status='outdate' limit ?,? ";//只有没过期的grant才能被选择被显示
            pstat = conn.prepareStatement(sql);
            //noinspection JpaQueryApiInspection
            if(pageNow<2) pageNow = 1;
            pstat.setInt(1, (pageNow-1)*pageSize);
            pstat.setInt(2, pageNow*pageSize);
            ResultSet rs = pstat.executeQuery();
            //pstat.close();

            //int count = 1;//防止多次循环
            int rgid = 0;

            while(rs.next()){

                GrantBean g = new GrantBean();//简略的显示信息
                rgid = rs.getInt("rgid");
                g.setGrantId(rgid);
                g.setGrantTitle(rs.getString("title"));
                g.setGrantSeries(rs.getString("series"));
                g.setGrantWhen(rs.getString("present"));
                g.setGrantStatus(rs.getString("status"));

                String sql1 = "select uid from UsubscribeRG where rgid=?";
                pstat1 = conn.prepareStatement(sql1);
                pstat1.setInt(1, rgid);
                ResultSet rs1 = pstat1.executeQuery();
                while(rs1.next()){
                    peopleCountOfInt++;
                }
                String peopleCount = String.valueOf(peopleCountOfInt);
                peopleCountOfInt = 0;//复原

                g.setGrantPeopleCount(peopleCount);
                pstat1.close();

                //查出id,以此来查出deadline和file
                if(userType.equals("RSO")) {
                    try {

                        String sql2 = "select * from Deadline where rgid=? order by dtime desc ";
                        pstat2 = conn2.prepareStatement(sql2);
                        pstat2.setInt(1, rgid);
                        ResultSet rs2 = pstat2.executeQuery();
                        while (rs2.next()) {
                            latestDate = rs2.getString("dtime");
                            //g.setHurryDeadline(latestDate);
                            //这种方法可以查出最近的deadline
                        }

                        rs2.close();
                        pstat2.close();

                        int userId = DBManager.getInstance().getUserIdByRGID(rgid);

                        String sql3 = "select name from UrelateS a, School b where a.uid = ? and a.sid = b.sid";//根据session提供的姓名来查询提交者所在的学院
                        pstat3 = conn3.prepareStatement(sql3);
                        pstat3.setInt(1, userId);
                        ResultSet rs3 = pstat3.executeQuery();
                        ArrayList<String> sch = new ArrayList<String>();

                    /*查询发表这个research grant的学院*/
                        while (rs3.next()) {
                            String schName = rs3.getString("name");
                            sch.add(schName);
                        }
                        for(int i = 0; i < sch.size(); i++){
                            if(!(sch.get(i)).equals("None")){//用这个函数来判断字符串是否相等
                                schStr += sch.get(i)+" ";
                            }
                        }
                        rs3.close();
                        pstat3.close();

                    /*查询这个RSO为这个过期的RG所设置的markdown*/

                        //RSO的archive页面需要把MarkDown显示出来
                        String sql4 = "select * from MarkDown a, User b where a.uid=b.uid and b.type='RSO' and rgid=? order by present desc";
                        pstat4 = conn3.prepareStatement(sql4);
                        pstat4.setInt(1, rgid);
                        ResultSet rs4 = pstat4.executeQuery();
                        if(rs4.next()){
                            //SRO的mark只能添加一次 所以选出present最新的，也就是最近修改的，就不用做修改功能了
                            g.setMarkdown(rs4.getString("mark"));
                        }else{
                            g.setMarkdown("Haven't Yet.");
                        }

                        rs4.close();
                        pstat4.close();



                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if(userType.equals("SRO")){
                    //SRO的archive页面需要把SRO的deadline查询出来
                    //我们显示SRO2的时候采用hurryDeadline2来表示
                    try{

                        String sql2 = "select * from Deadline a, UcreateD b, User c where a.did=b.did and b.uid=c.uid and c.type='SRO' and rgid=? order by present desc";
                        pstat2 = conn2.prepareStatement(sql2);
                        pstat2.setInt(1, rgid);
                        ResultSet rs2 = pstat2.executeQuery();
                        if(rs2.next()){
                            //SRO的deadline只能添加一次 所以选出present最新的，也就是最近修改的，就不用做修改功能了
                            g.setHurryDeadline2(rs2.getString("dtime"));
                        }else{
                            g.setHurryDeadline2("Haven't Yet.");
                        }

                        rs2.close();
                        pstat2.close();

                        //SRO的archive页面需要把MarkDown显示出来
                        String sql3 = "select * from MarkDown a, User b where a.uid=b.uid and b.type='SRO' and rgid=? order by present desc";
                        pstat3 = conn3.prepareStatement(sql3);
                        pstat3.setInt(1, rgid);
                        ResultSet rs3 = pstat3.executeQuery();
                        if(rs3.next()){
                            //SRO的mark只能添加一次 所以选出present最新的，也就是最近修改的，就不用做修改功能了
                            g.setMarkdown2(rs3.getString("mark"));
                        }else{
                            g.setMarkdown2("Haven't Yet.");
                        }

                        rs3.close();
                        pstat3.close();

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }



                g.setHurryDeadline(latestDate);//注入最近截至日期
                g.setGrantSchool(schStr);//注入发表这个grant的教授的学院，已经组合成一个字符串
                schStr = "";

                grantList.add(g);//这步已经获取到分页之前的元素

            }

            pstat.close();

            rs.close();

            connPool.pushConnectionBackToPool(conn);
            connPool.pushConnectionBackToPool(conn2);
            connPool.pushConnectionBackToPool(conn3);

        }catch(Exception e){
            e.printStackTrace();
        }



        return grantList;
    }

    public List<Map<String,Object>> getSubscirbeData(String rgid)throws Exception{

        int rgidOfInt = Integer.parseInt(rgid);
        Connection conn = connPool.getConnection();

        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();

        try {

            /*查询每个学院订阅这个rg的人和所在的学院统计,用json做！！！*/
            //step1:现把学院选择出来并且去除None的学院

            String sql1 = "select * from School where name !='None'";
            pstat = conn.prepareStatement(sql1);
            ResultSet rs1 = pstat.executeQuery();

            while (rs1.next()) {//统计每个学院订阅的人数


                //SubscribeBean s = new SubscribeBean();
                String sql2 = "select count(*) from UsubscribeRG a, UrelateS b, School c WHERE a.uid=b.uid and b.sid=c.sid and c.name=? and a.rgid=?";
                String schName = rs1.getString("name");
                pstat = conn.prepareStatement(sql2);
                pstat.setString(1, schName);
                pstat.setInt(2, rgidOfInt);

                ResultSet rs2 = pstat.executeQuery();
                rs2.next();
                if(!(rs2.getString(1)).equals("0")) {
                    Map<String,Object> map = new HashMap<String,Object>();
                    map.put("name",schName);
                    map.put("number",rs2.getString(1));
                    list.add(map);
                }
                rs2.close();
                /*查询每个学院订阅这个rg的人和所在的学院统计*/
            }

            pstat.close();
            rs1.close();

        }catch(Exception e){
            e.printStackTrace();
        }

        conn.close();
        connPool.pushConnectionBackToPool(conn);
        return list;

    }





    public ArrayList<GrantBean> grantShowFM1(int pageNow, int pageSize) throws Exception {

        ArrayList<GrantBean> grantList = new ArrayList<GrantBean>();

        try {

            ActionContext context= ActionContext.getContext();
            Map session = (Map)context.getSession();
            String userName = session.get("userName").toString();//获取session中的用户名，可以根据这个查询提交RG的教授所在的学院
            int userId =  getUserIdByName(userName);//获取用户ID


            Connection conn = connPool.getNewConnection();
            String sql1 = "select * from ResearchGrant where status = 'fresh' limit ?,?";
            pstat = conn.prepareStatement(sql1);
            //noinspection JpaQueryApiInspection
            pstat.setInt(1, (pageNow-1)*pageSize);
            //noinspection JpaQueryApiInspection
            pstat.setInt(2, pageNow*pageSize);
            ResultSet rs1 = pstat.executeQuery();
            while(rs1.next()){
                GrantBean g = new GrantBean();//封装信息

                int rgid = rs1.getInt("rgid");
                String sql2 = "select * from UsubscribeRG where uid=? and rgid=?";
                pstat2 = conn.prepareStatement(sql2);
                pstat2.setInt(1, userId);
                //noinspection JpaQueryApiInspection
                pstat2.setInt(2, rgid);
                ResultSet rs2 = pstat2.executeQuery();
                if(rs2.next()){
                    g.setGrantChecked("disabled");
                }else{
                    g.setGrantChecked("abled");
                }

                rs2.close();

                //查出最近的RSO的deadline
                String hurryDeadlineByRSO = "";
                String hurryDeadlineBySRO = "";

                String sql3 = "select min(dtime) as dtime from Deadline a, UcreateD b, User c where a.did=b.did and b.uid=c.uid and c.type='RSO' and rgid=?";
                pstat = conn.prepareStatement(sql3);
                pstat.setInt(1, rgid);
                ResultSet rs3 = pstat.executeQuery();
                if(rs3.next()){
                    if(rs3.getString("dtime") == null){
                        hurryDeadlineByRSO = "Haven't Yet.";
                    }else{
                        hurryDeadlineByRSO = rs3.getString("dtime");
                    }
                }else{
                    hurryDeadlineByRSO = rs3.getString("dtime");
                }

                g.setHurryDeadline(hurryDeadlineByRSO);

                //查出最新更新的SRO的deadline
                String sql4 = "select * from Deadline a, UcreateD b, User c where a.did=b.did and b.uid=c.uid and c.type='SRO' and rgid=? order by present desc";
                pstat = conn.prepareStatement(sql4);
                pstat.setInt(1, rgid);
                ResultSet rs4 = pstat.executeQuery();
                if(rs4.next()){
                    hurryDeadlineBySRO = rs4.getString("dtime");
                }else{
                    hurryDeadlineBySRO = "Haven't Yet.";
                }

                g.setHurryDeadline2(hurryDeadlineBySRO);


                g.setGrantId(rs1.getInt("rgid"));
                g.setGrantTitle(rs1.getString("title"));
                g.setGrantSeries(rs1.getString("series"));
                grantList.add(g);
            }

            rs1.close();

            connPool.pushConnectionBackToPool(conn);

        } catch (Exception e) {
            e.printStackTrace();
        }



        return grantList;
    }

    public ArrayList<GrantBean> grantShowFM2() throws Exception {

        ActionContext context= ActionContext.getContext();
        Map session = (Map)context.getSession();
        String userName = session.get("userName").toString();//获取sesssion中的用户名，可以根据这个查询提交RG的教授所在的学院
        int userId =  getUserIdByName(userName);//获取用户ID


        ArrayList<GrantBean> grantList = new ArrayList<GrantBean>();
        Connection conn = connPool.getConnection();
        String sql1 = "select * from User a, ResearchGrant b, UsubscribeRG c where b.status = 'fresh' and a.uid = c.uid and b.rgid = c.rgid and c.uid = ?";
        pstat = conn.prepareStatement(sql1);
        pstat.setInt(1, userId);
        ResultSet rs1 = pstat.executeQuery();
        while(rs1.next()){
            GrantBean g = new GrantBean();//封装信息
            g.setGrantId(rs1.getInt("rgid"));
            g.setGrantTitle(rs1.getString("title"));
            g.setGrantSeries(rs1.getString("series"));

            int rgid = rs1.getInt("rgid");
            //查出最近的RSO的deadline
            String hurryDeadlineByRSO = "";
            String hurryDeadlineBySRO = "";

            String sql3 = "select min(dtime) as dtime from Deadline a, UcreateD b, User c where a.did=b.did and b.uid=c.uid and c.type='RSO' and rgid=?";
            pstat = conn.prepareStatement(sql3);
            pstat.setInt(1, rgid);
            ResultSet rs3 = pstat.executeQuery();
            if(rs3.next()){
                if(rs3.getString("dtime") == null){
                    hurryDeadlineByRSO = "Haven't Yet.";
                }else{
                    hurryDeadlineByRSO = rs3.getString("dtime");
                }
            }else{
                hurryDeadlineByRSO = "Haven't Yet.";
            }
            rs3.close();

            g.setHurryDeadline(hurryDeadlineByRSO);

            //查出最新更新的SRO的deadline
            String sql4 = "select * from Deadline a, UcreateD b, User c where a.did=b.did and b.uid=c.uid and c.type='SRO' and rgid=? order by present desc";
            pstat = conn.prepareStatement(sql4);
            pstat.setInt(1, rgid);
            ResultSet rs4 = pstat.executeQuery();
            if(rs4.next()){
                hurryDeadlineBySRO = rs4.getString("dtime");
            }else{
                hurryDeadlineBySRO = "Haven't Yet.";
            }
            rs4.close();

            g.setHurryDeadline2(hurryDeadlineBySRO);

            grantList.add(g);
        }

        rs1.close();

        connPool.pushConnectionBackToPool(conn);

        return grantList;
    }

    public int addToListByFM(String [] grantIdArr, String userName) throws Exception{// faculty member 把物品放在购物车

        int grantIdArrLenth = grantIdArr.length;

         //注意针对每个物品购物车只能放一次
        Connection conn = connPool.getConnection();

        for(int i = 0; i < grantIdArrLenth; i++){

            String sql1 = "select * from User where name=?";//现根据传来的唯一用户名获取用户的ID值
            pstat = conn.prepareStatement(sql1);
            pstat.setString(1, userName);
            ResultSet rs1 = pstat.executeQuery();
            rs1.next();//否则为空
            int userId = rs1.getInt(1);
            int grantIdOfInt = Integer.parseInt(grantIdArr[i]);

            String sql2 = "select * from UsubscribeRG where uid = ? and rgid = ?";
            pstat = conn.prepareStatement(sql2);
            pstat.setInt(1, userId);
            pstat.setInt(2, grantIdOfInt);
            ResultSet rs2 = pstat.executeQuery();
            if(rs2.next()){//证明已经订阅过了，无法再次订阅
                return -1;
            }

            String sql3 = "insert into UsubscribeRG(uid, rgid)values(?,?)";
            pstat = conn.prepareStatement(sql3);
            pstat.setInt(1, userId);
            pstat.setInt(2, grantIdOfInt);
            pstat.executeUpdate();

        }

        return 1;//插入成功
    }

    //查询grant的总数，用于分页
    public int grantCount()throws Exception{
        int i = 0;
        try {
            Connection conn = connPool.getConnection();
            String sql1 = "select count(*) from ResearchGrant where status='fresh' ";
            pstat = conn.prepareStatement(sql1);
            ResultSet rs1 = pstat.executeQuery();
            if(rs1 != null) {
                rs1.next();//游标指向第一行
                i = rs1.getInt(1);//获取总行数
            }
            rs1.close();
            connPool.pushConnectionBackToPool(conn);
            pstat = null;
        }catch(Exception e){
            e.printStackTrace();
        }
        return i;
    }

    public int grantCount2()throws Exception{
        int i = 0;
        Connection conn = connPool.getConnection();
        try{

            String sql1 = "select count(*) from ResearchGrant where status='outdate'";
            pstat8 = conn.prepareStatement(sql1);
            ResultSet rs1 = pstat8.executeQuery();
            if(rs1.next()) i = rs1.getInt(1);//获取总行数
            rs1.close();



        }catch(Exception e){
            e.printStackTrace();
        }

        pstat8.close();
        connPool.pushConnectionBackToPool(conn);
        return i;
    }

    //RSO对指定ID的grant进行删除
    public int grantDeleteByRSO(String[] grantIds)throws Exception{//SRO和这个一样

        Connection conn = connPool.getConnection();
        int lenth = grantIds.length;

        for(int i = 0; i < lenth; i++){
            int id = Integer.parseInt(grantIds[i]);//把string类型转换成整形
            String sql1 = "delete from ResearchGrant where rgid=?";
            pstat = conn.prepareStatement(sql1);
            pstat.setInt(1, id);
            pstat.executeUpdate();

            //除了删除数据库还要删文件夹
            String basePath = ServletActionContext.getServletContext().getRealPath("/");
            String dirPath = basePath + "upload/" + grantIds[i] + "/";//获取文件上传路径
            File dirFile = new File(dirPath);//文件夹

            // 路径为文件且不为空则进行删除
            if (dirFile.exists() && dirFile.isDirectory()) {
                File files[] = dirFile.listFiles(); //声明目录下所有的文件 files[];
                for (int k = 0; k < files.length; k++) { //遍历目录下所有的文件
                    files[k].delete();//把每个文件 用这个方法进行迭代
                }
                dirFile.delete();//必须把这个文件夹中的文件全部删除之后才能最后删除文件夹
            }

        }

        pstat.close();
        connPool.pushConnectionBackToPool(conn);

        return 1;
    }


    //FM对指定ID的grant进行删除
    public int grantDeleteByFM(int userId, String [] grantIds)throws Exception{

        Connection conn = connPool.getConnection();
        int lenth = grantIds.length;

        for(int i = 0; i < lenth; i++){
            int grantIdOfInt = Integer.parseInt(grantIds[i]);//把string类型转换成整形
            String sql1 = "delete from UsubscribeRG where uid=? and rgid=?";
            pstat = conn.prepareStatement(sql1);
            pstat.setInt(1, userId);
            pstat.setInt(2, grantIdOfInt);
            pstat.executeUpdate();
        }

        connPool.pushConnectionBackToPool(conn);
        pstat = null;

        return 1;
    }

    //修改界面的deadline删除功能
    public int deleteDeadline(String did)throws Exception{

        Connection conn = connPool.getConnection();
        int didOfInt = 0;

        try{
            didOfInt = Integer.parseInt(did);//把string类型转换成整形
            String sql1 = "delete from Deadline where did=?";
            pstat = conn.prepareStatement(sql1);
            pstat.setInt(1, didOfInt);
            pstat.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }

        connPool.pushConnectionBackToPool(conn);
        pstat = null;

        return 1;
    }

    //修改界面的file删除功能
    public int deleteFile(String fid, String grantIdOfStr)throws Exception{

        int fidOfInt = Integer.parseInt(fid);
        //int grantIdOfInt = (int)Integer.parseInt(grantIdOfStr);

        Connection conn = connPool.getConnection();

        int flagForDelete = 0;

        try{

            String sql1 = "select * from File where fid=?";
            pstat = conn.prepareStatement(sql1);
            pstat.setInt(1, fidOfInt);
            ResultSet rs1 = pstat.executeQuery();
            rs1.next();
            String filePath = rs1.getString("filePath");//需要到指定文件夹删除文件
            rs1.close();
            File file = new File(filePath);

            // 路径为文件且不为空则进行删除
            if (file.isFile() && file.exists()) {
                file.delete();
                flagForDelete = 1;
            }

            if(flagForDelete > 0){//文件删除成功开始删除数据库纪录

                String sql2 = "delete from File where fid=?";
                pstat2 = conn.prepareStatement(sql2);
                pstat2.setInt(1, fidOfInt);
                pstat2.executeUpdate();

            }

            pstat = null;
            pstat2 = null;

            }catch(Exception e){
            e.printStackTrace();
        }

        return 1;

    }

    public ArrayList<GrantBean> grantDetail(int grantId)throws Exception{

        ArrayList<GrantBean> grantList = new ArrayList<GrantBean>();

        try{

            String grantDeadlineIdOfDetail = "";
            String grantDeadlineOfDetail = "";
            String grantDeadlineDescriptionOfDetail = "";
            String grantFileIdOfDetail = "";
            String grantFilePathOfDetail = "";
            String grantFileNameOfDetail = "";
            String grantFileName2OfDetail = "";

            //Map request=(Map) ActionContext.getContext().get("request");//创建request对象

            Connection conn = connPool.getConnection();
            String sql1 = "select * from ResearchGrant where rgid=? and status = 'fresh'";

            pstat = conn.prepareStatement(sql1);
            pstat.setInt(1, grantId);
            ResultSet rs1 = pstat.executeQuery();

            String signStr = "    ";

            while(rs1 != null && rs1.next()){

                GrantBean g = new GrantBean();//封装信息
                g.setGrantId(rs1.getInt("rgid"));
                g.setGrantTitle(rs1.getString("title"));
                g.setGrantSeries(rs1.getString("series"));
                g.setGrantContact(rs1.getString("contact"));
                g.setGrantContent(rs1.getString("content"));

                String sql2 = "select * from Deadline where rgid=?";
                pstat2 = conn.prepareStatement(sql2);
                pstat2.setInt(1, grantId);
                ResultSet rs2 = pstat2.executeQuery();
                while (rs2.next()) {
                    grantDeadlineIdOfDetail += String.valueOf(rs2.getInt("did")) + signStr;
                    grantDeadlineOfDetail += rs2.getString("dtime") + signStr;
                    grantDeadlineDescriptionOfDetail += rs2.getString("description") + signStr;
                }

                g.setGrantDeadlineIdOfDetail(grantDeadlineIdOfDetail);
                g.setGrantDeadlineOfDetail(grantDeadlineOfDetail);
                g.setGrantDeadlineDescriptionOfDetail(grantDeadlineDescriptionOfDetail);

                rs2.close();

                String sql3 = "select * from File where rgid=?";
                pstat3 = conn.prepareStatement(sql3);
                pstat3.setInt(1, grantId);
                ResultSet rs3 = pstat3.executeQuery();
                while(rs3.next()){
                    grantFileIdOfDetail += String.valueOf(rs3.getInt("fid")) + signStr;
                    grantFileNameOfDetail += rs3.getString("fileName") + signStr;
                    grantFileName2OfDetail += rs3.getString("fileName2") + signStr;
                    grantFilePathOfDetail += rs3.getString("filePath") + signStr;
                }
                g.setGrantFileIdOfDetail(grantFileIdOfDetail);
                g.setGrantFileNameOfDetail(grantFileNameOfDetail);
                g.setGrantFileName2OfDetail(grantFileName2OfDetail);
                g.setGrantFilePathOfDetail(grantFilePathOfDetail);

                grantList.add(g);
                rs3.close();
            }

            rs1.close();

            connPool.pushConnectionBackToPool(conn);

        }catch(Exception e){

            e.printStackTrace();

        }



        return grantList;
    }

    //显示outdate的detail
    public ArrayList<GrantBean> grantDetail2(int grantId)throws Exception{

        ArrayList<GrantBean> grantList = new ArrayList<GrantBean>();

        try{

            String grantDeadlineIdOfDetail = "";
            String grantDeadlineOfDetail = "";
            String grantDeadlineDescriptionOfDetail = "";
            String grantFileIdOfDetail = "";
            String grantFilePathOfDetail = "";
            String grantFileNameOfDetail = "";
            String grantFileName2OfDetail = "";

            //Map request=(Map) ActionContext.getContext().get("request");//创建request对象

            Connection conn = connPool.getConnection();
            String sql1 = "select * from ResearchGrant where rgid=? and status = 'outdate'";

            pstat = conn.prepareStatement(sql1);
            pstat.setInt(1, grantId);
            ResultSet rs1 = pstat.executeQuery();
            pstat = null;

            String signStr = "    ";

            while(rs1 != null && rs1.next()){

                GrantBean g = new GrantBean();//封装信息
                g.setGrantId(rs1.getInt("rgid"));
                g.setGrantTitle(rs1.getString("title"));
                g.setGrantSeries(rs1.getString("series"));
                g.setGrantContact(rs1.getString("contact"));
                g.setGrantContent(rs1.getString("content"));

                String sql2 = "select * from Deadline where rgid=?";
                pstat2 = conn.prepareStatement(sql2);
                pstat2.setInt(1, grantId);
                ResultSet rs2 = pstat2.executeQuery();
                while (rs2.next()) {
                    grantDeadlineIdOfDetail += String.valueOf(rs2.getInt("did")) + signStr;
                    grantDeadlineOfDetail += rs2.getString("dtime") + signStr;
                    grantDeadlineDescriptionOfDetail += rs2.getString("description") + signStr;
                }

                g.setGrantDeadlineIdOfDetail(grantDeadlineIdOfDetail);
                g.setGrantDeadlineOfDetail(grantDeadlineOfDetail);
                g.setGrantDeadlineDescriptionOfDetail(grantDeadlineDescriptionOfDetail);

                rs2.close();

                String sql3 = "select * from File where rgid=?";
                pstat3 = conn.prepareStatement(sql3);
                pstat3.setInt(1, grantId);
                ResultSet rs3 = pstat3.executeQuery();
                while(rs3.next()){
                    grantFileIdOfDetail += String.valueOf(rs3.getInt("fid")) + signStr;
                    grantFileNameOfDetail += rs3.getString("fileName") + signStr;
                    grantFileName2OfDetail += rs3.getString("fileName2") + signStr;
                    grantFilePathOfDetail += rs3.getString("filePath") + signStr;
                }
                g.setGrantFileIdOfDetail(grantFileIdOfDetail);
                g.setGrantFileNameOfDetail(grantFileNameOfDetail);
                g.setGrantFileName2OfDetail(grantFileName2OfDetail);
                g.setGrantFilePathOfDetail(grantFilePathOfDetail);

                grantList.add(g);
                rs3.close();
            }

            rs1.close();

            connPool.pushConnectionBackToPool(conn);

        }catch(Exception e){
            e.printStackTrace();
        }



        return grantList;
    }


    //通过用户名来查找用户ID
    public int getUserIdByName(String userName)throws Exception{

        int userId = 0;
        Connection conn = connPool.getNewConnection();

        try{

            String sql1 = "select * from User where name=?";
            pstat9 = conn.prepareStatement(sql1);
            pstat9.setString(1, userName);
            ResultSet rs1 = pstat9.executeQuery();
            if(rs1.next()){
            //游标指向第一行
                userId = rs1.getInt(1);
            }
            rs1.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        pstat9.close();

        connPool.pushConnectionBackToPool(conn);


        return userId;//获取userID;
    }


    //通过学校名字找学校ID
    public int getSchoolIdByName(String schName)throws Exception{

        Connection conn = connPool.getConnection();
        String sql1 = "select * from School where name=?";
        pstat = conn.prepareStatement(sql1);
        pstat.setString(1, schName);
        ResultSet rs1 = pstat.executeQuery();
        rs1.next();//游标指向第一行
        int schId = rs1.getInt(1);
        System.out.println("School Id:  "+schId);
        //rs1.close();
        connPool.pushConnectionBackToPool(conn);
        return schId;//获取userID;
    }

    //通过RG来查找用户ID
    public int getUserIdByRGID(int rgid)throws Exception{

        int userId = 0;
        try{

            Connection conn = connPool.getConnection();
            String sql1 = "select * from UmanageRG where rgid=?";
            pstat5 = conn.prepareStatement(sql1);
            pstat5.setInt(1, rgid);
            ResultSet rs1 = pstat5.executeQuery();
            if(rs1.next()) {
                userId = rs1.getInt(2);
                //游标指向第一行
            }
            rs1.close();
            pstat5.close();
            connPool.pushConnectionBackToPool(conn);


        }catch(Exception e){
            e.printStackTrace();
        }

        return  userId;//获取userID;
    }


    public ArrayList<GrantBean> grantFilterResults(String filter1, String filter2, String filter3, String userType)throws Exception{

        ArrayList<GrantBean> grantList = new ArrayList<GrantBean>();

        String allCondtion = "All";
        Connection conn = connPool.getConnection();

        ActionContext context= ActionContext.getContext();
        Map session = (Map)context.getSession();
        String userName = session.get("userName").toString();
        int userIdOfInt = DBManager.getInstance().getUserIdByName(userName);



        try{

            String sql1="";//适用于不同种类的查询
            ResultSet rs1 = null;
            String flag = "";//检测是哪种搜索条件

            if(filter2.equals(allCondtion) && filter3.equals(allCondtion)){
                flag = "F1";
                //全搜
                sql1 = "select d.uid, a.rgid, series, title, MIN(dtime) as ltime from ResearchGrant a, Deadline b, UmanageRG c, UrelateS d, School e where a.rgid=b.rgid and a.rgid=c.rgid and a.status=? and c.uid=d.uid and d.sid=e.sid GROUP BY a.rgid";
                pstat = conn.prepareStatement(sql1);
                pstat.setString(1, filter1);
                rs1 = pstat.executeQuery();

            }else if( (!filter2.equals(allCondtion)) && filter3.equals(allCondtion)){
                //学院全搜
                flag = "F2";
                System.out.println("F2"+filter2);
                sql1 = "select d.uid, a.rgid, series, title, MIN(dtime) as ltime, SUBSTR(MIN(dtime), 6, 2) in(?)  as subltime from ResearchGrant a, Deadline b, UmanageRG c, UrelateS d, School e where a.rgid=b.rgid and a.rgid=c.rgid and a.status=? and c.uid= d.uid and d.sid = e.sid GROUP BY a.rgid";
                pstat = conn.prepareStatement(sql1);
                pstat.setString(1, filter2);
                pstat.setString(2, filter1);
                rs1 = pstat.executeQuery();

            }else if( filter2.equals(allCondtion) && !(filter3.equals(allCondtion))){
                //月份全搜
                flag = "F3";
                System.out.println("F3");
                sql1 = "select d.uid, a.rgid, series, title, MIN(dtime) as ltime from ResearchGrant a, Deadline b, UmanageRG c, UrelateS d, School e where a.rgid=b.rgid and a.rgid=c.rgid and a.status=? and c.uid= d.uid and d.sid = e.sid and e.name=? GROUP BY a.rgid";
                pstat = conn.prepareStatement(sql1);
                pstat.setString(1, filter1);
                pstat.setString(2, filter3);

                rs1 = pstat.executeQuery();

            }else{
                //单月份，单学院搜索
                flag = "F4";
                System.out.println("F4");
                sql1 = "select d.uid, a.rgid, series, title, MIN(dtime) as ltime, SUBSTR(MIN(dtime), 6, 2) in(?)  as subltime from ResearchGrant a, Deadline b, UmanageRG c, UrelateS d, School e where a.rgid=b.rgid and a.rgid=c.rgid and a.status=? and c.uid= d.uid and d.sid = e.sid and e.name=? GROUP BY a.rgid";
                pstat = conn.prepareStatement(sql1);
                pstat.setString(1, filter2);
                pstat.setString(2, filter1);
                pstat.setString(3, filter3);
                rs1 = pstat.executeQuery();
            }


           //剩下这部分查询都是相同的
            if(flag.equals("F1") || flag.equals("F3")){

                while(rs1!=null && rs1.next()){
                    GrantBean g = new GrantBean();
                    g.setGrantId(rs1.getInt("rgid"));
                    g.setGrantIdOfStr(String.valueOf(rs1.getInt("rgid")));
                    g.setGrantSeries(rs1.getString("series"));
                    g.setGrantTitle(rs1.getString("title"));
                    g.setHurryDeadline(rs1.getString("ltime"));//这个deadline针对FM1是错误的之后会覆盖掉

                    //选出发布者所在的学院
                    String schStr = DBManager.getInstance().getSchStrByUserId(rs1.getInt("uid"));
                    g.setGrantSchool(schStr);

                    //选出订阅这个RG的人数
                    String peopleCount = DBManager.getInstance().getSubscribeCountByRGID(rs1.getInt("rgid"));
                    g.setGrantPeopleCount(peopleCount);

                    //如果是SRO需要选出他自己的deadline, hurryDeadline2
                    //如果是FM1需要选出hurryDeadline和hurryDeadline2
                    if(userType.equals("SRO")){
                        String sql2 = "select * from Deadline a, UcreateD b, User c where a.did=b.did and b.uid=c.uid and c.type='SRO' and rgid=? order by present desc";
                        pstat2 = conn.prepareStatement(sql2);
                        pstat2.setInt(1, rs1.getInt("rgid"));
                        ResultSet rs2 = pstat2.executeQuery();
                        if(rs2.next()){
                            //SRO的deadline只能添加一次 所以选出present最新的，也就是最近修改的，就不用做修改功能了
                            g.setHurryDeadline2(rs2.getString("dtime"));
                        }else{
                            g.setHurryDeadline2("Haven't Yet.");
                        }
                        rs2.close();
                        pstat2 = null;
                    }

                    if(userType.equals("FM")){
                        //搞定hurryDeadline2
                        String sql2 = "select * from Deadline a, UcreateD b, User c where a.did=b.did and b.uid=c.uid and c.type='SRO' and rgid=? order by present desc";
                        pstat2 = conn.prepareStatement(sql2);
                        pstat2.setInt(1, rs1.getInt("rgid"));
                        ResultSet rs2 = pstat2.executeQuery();
                        if(rs2.next()){
                            //SRO的deadline只能添加一次 所以选出present最新的，也就是最近修改的，就不用做修改功能了
                            g.setHurryDeadline2(rs2.getString("dtime"));
                        }else{
                            g.setHurryDeadline2("Haven't Yet.");
                        }
                        rs2.close();
                        pstat2.close();

                        //重新搞定hurryDeadline1
                        //从新搞定deadline1
                        String sql2_2 = "select min(dtime) as ltime from Deadline a, UcreateD b, User c where a.did=b.did and b.uid=c.uid and c.type='RSO' and rgid=?";
                        pstat2 = conn.prepareStatement(sql2_2);
                        pstat2.setInt(1, rs1.getInt("rgid"));
                        ResultSet rs2_2 = pstat2.executeQuery();
                        if(rs2_2.next()){
                            if(rs2_2.getString("ltime") == null){
                                g.setHurryDeadline("Haven't Yet.");
                            }else{
                                g.setHurryDeadline(rs2_2.getString("ltime"));
                            }
                        }else{
                            g.setHurryDeadline("Haven't Yet.");
                        }
                        rs2_2.close();
                        pstat2.close();

                        //只针对FM的check字段设置，订阅按钮是否实效
                        String sql3 = "select * from UsubscribeRG where uid=? and rgid=?";
                        pstat3 = conn.prepareStatement(sql3);
                        pstat3.setInt(1, userIdOfInt);
                        //noinspection JpaQueryApiInspection
                        pstat3.setInt(2, rs1.getInt("rgid"));
                        ResultSet rs3 = pstat3.executeQuery();
                        if(rs3.next()){
                            g.setGrantChecked("disabled");
                        }else{
                            g.setGrantChecked("abled");
                        }
                        rs3.close();
                        pstat3.close();

                    }

                    grantList.add(g);
                }


                rs1.close();

                connPool.pushConnectionBackToPool(conn);

            }else{//要按日期选

                while(rs1!=null && rs1.next()){

                    if(!rs1.getString("subltime").equals("0")){

                        GrantBean g = new GrantBean();
                        g.setGrantId(rs1.getInt("rgid"));
                        g.setGrantIdOfStr(String.valueOf(rs1.getInt("rgid")));
                        g.setGrantSeries(rs1.getString("series"));
                        g.setGrantTitle(rs1.getString("title"));
                        g.setHurryDeadline(rs1.getString("ltime"));



                        //选出发布者所在的学院
                        String schStr = DBManager.getInstance().getSchStrByUserId(rs1.getInt("uid"));
                        g.setGrantSchool(schStr);
                        schStr = "";

                        //选出订阅这个RG的人数
                        String peopleCount = DBManager.getInstance().getSubscribeCountByRGID(rs1.getInt("rgid"));
                        g.setGrantPeopleCount(peopleCount);
                        //System.out.println("HAHAHA "+peopleCount);

                        if(userType.equals("SRO")){
                            String sql2 = "select * from Deadline a, UcreateD b, User c where a.did=b.did and b.uid=c.uid and c.type='SRO' and rgid=? order by present desc";
                            pstat2 = conn.prepareStatement(sql2);
                            pstat2.setInt(1, rs1.getInt("rgid"));
                            ResultSet rs2 = pstat2.executeQuery();
                            if(rs2.next()){
                                //SRO的deadline只能添加一次 所以选出present最新的，也就是最近修改的，就不用做修改功能了
                                if(rs2.getString("dtime") == null){
                                    g.setHurryDeadline2("Haven't Yet.");
                                }else{
                                    g.setHurryDeadline2(rs2.getString("dtime"));
                                }
                            }else{
                                g.setHurryDeadline2("Haven't Yet.");
                            }
                            rs2.close();
                            pstat2.close();

                        }

                        if(userType.equals("FM")){
                            //搞定hurryDeadline2
                            String sql2 = "select * from Deadline a, UcreateD b, User c where a.did=b.did and b.uid=c.uid and c.type='SRO' and rgid=? order by present desc";
                            pstat2 = conn.prepareStatement(sql2);
                            pstat2.setInt(1, rs1.getInt("rgid"));
                            ResultSet rs2 = pstat2.executeQuery();
                            if(rs2.next()){
                                //SRO的deadline只能添加一次 所以选出present最新的，也就是最近修改的，就不用做修改功能了
                                if(rs2.getString("dtime") == null){
                                    g.setHurryDeadline2("Haven't Yet.");
                                }else{
                                    g.setHurryDeadline2(rs2.getString("dtime"));
                                }
                            }else{
                                g.setHurryDeadline2("Haven't Yet.");
                            }
                            rs2.close();
                            pstat2 = null;

                            //从新搞定deadline1
                            String sql2_2 = "select min(dtime) as ltime from Deadline a, UcreateD b, User c where a.did=b.did and b.uid=c.uid and c.type='RSO' and rgid=?";
                            pstat2 = conn.prepareStatement(sql2_2);
                            pstat2.setInt(1, rs1.getInt("rgid"));
                            ResultSet rs2_2 = pstat2.executeQuery();
                            if(rs2_2.next()){
                                if(rs2_2.getString("ltime") == null){
                                    g.setHurryDeadline("Haven't Yet.");
                                }else{
                                    g.setHurryDeadline(rs2_2.getString("ltime"));
                                }
                            }else{
                                g.setHurryDeadline("Haven't Yet.");
                            }
                            rs2_2.close();
                            pstat2.close();

                            //只针对FM的check字段设置，订阅按钮是否实效
                            String sql3 = "select * from UsubscribeRG where uid=? and rgid=?";
                            pstat3 = conn.prepareStatement(sql3);
                            pstat3.setInt(1, userIdOfInt);
                            //noinspection JpaQueryApiInspection
                            pstat3.setInt(2, rs1.getInt("rgid"));
                            ResultSet rs3 = pstat3.executeQuery();
                            if(rs3.next()){
                                g.setGrantChecked("disabled");
                            }else{
                                g.setGrantChecked("abled");
                            }
                            rs3.close();
                            pstat3.close();

                        }

                        grantList.add(g);

                    }
                }

                rs1.close();

            }

            pstat = null;
            connPool.pushConnectionBackToPool(conn);

        }catch(Exception e){
            e.printStackTrace();
        }

        return grantList;
    }

    public String getSchStrByUserId(int uid) throws Exception{

        Connection conn = connPool.getConnection();
        String schStr = "";

        try{

            String sql1 = "select * from UrelateS a, School b where a.sid=b.sid and a.uid=?";
            pstat = conn.prepareStatement(sql1);
            pstat.setInt(1, uid);
            ResultSet rs1 = pstat.executeQuery();
            while(rs1.next()){
                if(!rs1.getString("name").equals("None")){
                    schStr += rs1.getString("name") + "    ";
                }
            }
            rs1.close();
            pstat = null;
            connPool.pushConnectionBackToPool(conn);

        }catch(Exception e){
            e.printStackTrace();
        }

        return schStr;
    }

    public String getSubscribeCountByRGID(int rgid) throws Exception{

        String peopleCount = "";
        Connection conn = connPool.getConnection();

        try{
            String sql1 = "select count(*) as peopleCount from UsubscribeRG where rgid=?";
            pstat = conn.prepareStatement(sql1);
            pstat.setInt(1, rgid);
            ResultSet rs1 = pstat.executeQuery();
            if(rs1 != null){
                rs1.next();
                peopleCount = rs1.getString("peopleCount");
            }

            rs1.close();
            connPool.pushConnectionBackToPool(conn);
            pstat = null;

        }catch(Exception e){
            e.printStackTrace();
        }

        return peopleCount;
    }

    public int addOneDeadlineBySRO(String grantId, String deadline, String description) throws Exception{

        //System.out.println("Enter this function!");

        int result = 0;
        Connection conn = connPool.getConnection();
        int grantIdOfInt = Integer.parseInt(grantId);

        ActionContext context= ActionContext.getContext();
        Map session = (Map)context.getSession();
        String userName = session.get("userName").toString();//获取sesssion中的用户名，可以根据这个查询用户的ID
        int userIdOfInt = DBManager.getInstance().getUserIdByName(userName);

        String timeNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());


        try{
            String sql1 = "insert into Deadline(dtime, description, rgid, present)values(?,?,?,?)";
            pstat = conn.prepareStatement(sql1);
            pstat.setString(1, deadline);
            pstat.setString(2, description);
            pstat.setInt(3, grantIdOfInt);
            pstat.setString(4, timeNow);
            pstat.executeUpdate();
            pstat = null;

            String sql2 = "select * from Deadline where present=?";
            pstat = conn.prepareStatement(sql2);
            pstat.setString(1, timeNow);
            ResultSet rs1 = pstat.executeQuery();
            rs1.next();
            int didOfInt = rs1.getInt("did");
            rs1.close();
            pstat = null;

            String sql3 = "insert into UcreateD(did, uid) values(?,?)";
            pstat = conn.prepareStatement(sql3);
            pstat.setInt(1, didOfInt);
            pstat.setInt(2, userIdOfInt);
            pstat.executeUpdate();

            this.flagForSendEmail = "Yes";
            if(this.flagForSendEmail.equals("Yes")){
                sendEmail(grantIdOfInt, "sendAfterModify");
                this.flagForSendEmail = "No";
            }

            pstat.close();
            connPool.pushConnectionBackToPool(conn);

            result = 1;

        }catch(Exception e){
            result = -1;
            e.printStackTrace();
        }

        return result;
    }

    public int addMarkDownForOutdate(int userIdOfInt, int rgidOfInt, String markDown)throws Exception{

        int result = 0;
        String timeNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

        try{

            Connection conn = connPool.getConnection();
            String sql1 = "insert into MarkDown(uid, rgid, mark, present)values(?,?,?,?)";
            pstat = conn.prepareStatement(sql1);
            pstat.setInt(1, userIdOfInt);
            pstat.setInt(2, rgidOfInt);
            pstat.setString(3, markDown);
            pstat.setString(4, timeNow);
            pstat.executeUpdate();

            pstat.close();
            connPool.pushConnectionBackToPool(conn);
            result = 1;
        }catch(Exception e){
            result = -1;
            e.printStackTrace();
        }

        return result;
    }

    public int grantCountForFullText(String userType, String searchType, String condition, String status)throws Exception{
        int i = 0;
        try {
            Connection conn = connPool.getConnection();
            String sql1 = "";
            if(userType.equals("FM")){
                sql1 = "select count(*) as count1 from ResearchGrant where status = 'fresh' and match(title, series, content) against(?)";

            }else{
                sql1 = "select count(*) as count1 from ResearchGrant where status = ? and match(title, series, content) against(?)";

            }
            pstat5 = conn.prepareStatement(sql1);
            //noinspection JpaQueryApiInspection
            if(userType.equals("FM")){
                pstat5.setString(1, condition);
            }else{
                pstat5.setString(1, status);
                //noinspection JpaQueryApiInspection
                pstat5.setString(2, condition);
            }
            ResultSet rs1 = pstat5.executeQuery();
            if(rs1.next()) {
                i = Integer.parseInt(rs1.getString("count1"));//获取总行数
            }else{
                i = 0;
            }
            rs1.close();
            pstat5.close();
            connPool.pushConnectionBackToPool(conn);

        }catch(Exception e){
            e.printStackTrace();
        }
        return i;
    }


    public List<Map<String,Object>> getSearchData(String userType, String searchType, int pageNow, int pageSize, String condition, String status)throws Exception{


        Connection conn = connPool.getConnection();
        Connection conn1 = connPool.getNewConnection();

        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();//用于返回数据

        try {

            ActionContext context= ActionContext.getContext();
            Map session = (Map)context.getSession();
            String userName = session.get("userName").toString();//获取session中的用户名，可以根据这个查询提交RG的教授所在的学院
            int userId =  DBManager.getInstance().getUserIdByName(userName);//获取用户ID


            if(userType.equals("FM")){

                ResultSet rs1 = null;

                String sql1 = "";

                if(searchType.equals("showCondition")){

                    sql1 = "select * from ResearchGrant where status = 'fresh' and match(title, series, content) against(?) limit ?,?";
                    pstat = conn.prepareStatement(sql1);
                    sql1 = "";
                    //noinspection JpaQueryApiInspection
                    pstat.setString(1, condition);
                    //noinspection JpaQueryApiInspection
                    pstat.setInt(2, (pageNow-1)*pageSize);
                    //noinspection JpaQueryApiInspection
                    pstat.setInt(3, pageNow*pageSize);
                    rs1 = pstat.executeQuery();
                    pstat = null;
                }



                while(rs1.next()){

                    Map<String,Object> map = new HashMap<String,Object>();//封装信息

                    int rgid = rs1.getInt("rgid");
                    String sql2 = "select * from UsubscribeRG where uid=? and rgid=?";
                    pstat2 = conn.prepareStatement(sql2);
                    //noinspection JpaQueryApiInspection
                    pstat2.setInt(1, userId);
                    //noinspection JpaQueryApiInspection
                    pstat2.setInt(2, rgid);
                    ResultSet rs2 = pstat2.executeQuery();
                    if(rs2.next()){
                        map.put("grantChecked","disabled");
                    }else{
                        map.put("grantChecked", "abled");
                    }

                    rs2.close();
                    pstat2 = null;

                    //查出最近的RSO的deadline

                    String sql3 = "select min(a.dtime) as dtime from Deadline a, UcreateD b, User c where a.did=b.did and b.uid=c.uid and c.type='RSO' and a.rgid=?";
                    pstat3 = conn.prepareStatement(sql3);
                    pstat3.setInt(1, rgid);
                    ResultSet rs3 = pstat3.executeQuery();
                    if(rs3.next()){
                        if(rs3.getString("dtime")!=null){
                            map.put("hurryDeadlineByRSO", rs3.getString("dtime"));
                        }else{
                            map.put("hurryDeadlineByRSO", "Haven't Yet.");
                        }
                    }else{
                        map.put("hurryDeadlineByRSO", "Haven't Yet.");
                    }
                    rs3.close();
                    pstat3 = null;

                    //查出最新更新的SRO的deadline
                    String sql4 = "select * from Deadline a, UcreateD b, User c where a.did=b.did and b.uid=c.uid and c.type='SRO' and a.rgid=? order by present desc";
                    pstat4 = conn.prepareStatement(sql4);
                    pstat4.setInt(1, rgid);
                    ResultSet rs4 = pstat4.executeQuery();
                    if(rs4.next()){
                        if(rs4.getString("dtime")!=null){
                            map.put("hurryDeadlineBySRO", rs4.getString("dtime"));
                        }else{
                            map.put("hurryDeadlineBySRO", "Haven't Yet.");
                        }

                    }else{
                        map.put("hurryDeadlineBySRO", "Haven't Yet.");
                    }
                    rs4.close();
                    pstat4 = null;

                    map.put("grantId", rs1.getInt("rgid"));
                    map.put("grantTitle", rs1.getString("title"));
                    map.put("grantSeries", rs1.getString("series"));

                    list.add(map);
                }

                rs1.close();
            }

            if(userType.equals("RSO")){

                String sql1  = "select * from ResearchGrant where status=? and match(title, series, content) against(?) limit ?,?";
                pstat = conn1.prepareStatement(sql1);
                pstat.setString(1, status);
                //noinspection JpaQueryApiInspection
                pstat.setString(2, condition);
                //noinspection JpaQueryApiInspection
                pstat.setInt(3, (pageNow - 1) * pageSize);
                //noinspection JpaQueryApiInspection
                pstat.setInt(4, pageNow*pageSize);
                ResultSet rs1 = pstat.executeQuery();

                while(rs1.next()){

                    Map<String,Object> map2 = new HashMap<String,Object>();//封装信息

                    map2.put("grantId", rs1.getInt("rgid"));
                    map2.put("grantTitle", rs1.getString("title"));
                    map2.put("grantSeries", rs1.getString("series"));

                    //订阅人数
                    String sql2 = "select count(uid) as count2 from UsubscribeRG where rgid=?";
                    pstat2 = conn1.prepareStatement(sql2);
                    pstat2.setInt(1, rs1.getInt("rgid"));
                    ResultSet rs2 = pstat2.executeQuery();
                    if(rs2.next()){
                        map2.put("grantPeopleCount", rs2.getString("count2"));
                    }else{
                        map2.put("grantPeopleCount", "0");
                    }
                    rs2.close();
                    pstat2.close();

                    //查询最近的Deadline
                    String sql3 = "select min(dtime) as dtime from Deadline where rgid=?";
                    pstat3 = conn1.prepareStatement(sql3);
                    pstat3.setInt(1, rs1.getInt("rgid"));
                    ResultSet rs3 = pstat3.executeQuery();
                    if(rs3.next()){
                        if(rs3.getString("dtime") == null){
                            map2.put("hurryDeadline", "Haven't Yet.");
                        }else{
                            map2.put("hurryDeadline", rs3.getString("dtime"));
                        }
                    }else{
                        map2.put("hurryDeadline", "Haven't Yet.");
                    }

                    rs3.close();
                    pstat3.close();

                    //查询发布者所属的学院
                    String sql4 = "select name from UrelateS a, School b where a.uid=? and a.sid=b.sid";
                    pstat4 = conn1.prepareStatement(sql4);
                    pstat4.setInt(1, userId);
                    ResultSet rs4 = pstat4.executeQuery();
                    String schStr = "";
                    while(rs4.next()){
                        if(!rs4.getString("name").equals("None")){
                            schStr += rs4.getString("name") + " ";
                        }
                    }
                    rs4.close();
                    pstat4.close();
                    map2.put("schStr", schStr);

                    //查询最新更新的RSO设置的markDown
                    String sql5 = "select mark from MarkDown where uid=? and rgid=? order by present desc limit 0,1";
                    pstat5 = conn1.prepareStatement(sql5);
                    pstat5.setInt(1, userId);
                    pstat5.setInt(2, rs1.getInt("rgid"));
                    ResultSet rs5 = pstat5.executeQuery();
                    if(rs5.next()){
                        if(rs5.getString("mark") != null){
                            map2.put("grantMarkDownByRSO", rs5.getString("mark"));
                        }else{
                            map2.put("grantMarkDownByRSO", "Haven't Yet.");
                        }
                    }else{
                        map2.put("grantMarkDownByRSO", "Haven't Yet.");
                    }
                    rs5.close();
                    pstat5.close();

                    list.add(map2);

                }

                rs1.close();
                pstat.close();
            }

            if(userType.equals("SRO")){

                String sql1  = "select * from ResearchGrant where status=? and match(title, series, content) against(?) limit ?,?";
                pstat = conn.prepareStatement(sql1);
                pstat.setString(1, status);
                //noinspection JpaQueryApiInspection
                pstat.setString(2, condition);
                //noinspection JpaQueryApiInspection
                pstat.setInt(3, (pageNow - 1) * pageSize);
                //noinspection JpaQueryApiInspection
                pstat.setInt(4, pageNow*pageSize);
                ResultSet rs1 = pstat.executeQuery();

                while(rs1.next()){
                    Map<String,Object> map3 = new HashMap<String,Object>();//封装信息

                    map3.put("grantId", rs1.getInt("rgid"));
                    map3.put("grantTitle", rs1.getString("title"));
                    map3.put("grantSeries", rs1.getString("series"));

                    //查询SRO属于的学院都有谁订阅了该RG的总人数
                    String sql2 = "select c.name as schName from User a, UrelateS b, School c where a.uid=b.uid and b.sid=c.sid and a.uid=?";
                    pstat2 = conn.prepareStatement(sql2);
                    pstat2.setInt(1, userId);
                    ResultSet rs2 = pstat2.executeQuery();
                    ArrayList<String> schList = new ArrayList<String>();
                    while(rs2.next()){
                        if(!rs2.getString("schName").equals("None")){
                            schList.add(rs2.getString("schName"));
                        }
                    }

                    pstat2.close();
                    rs2.close();

                    int peopleCountOfInt = 0;
                    for(int i = 0; i < schList.size(); i++){
                        String sch = schList.get(i);
                        String sql3 = "select count(*) as peopleCount from UsubscribeRG a, UrelateS b, School c, User d where a.uid=b.uid and b.sid=c.sid and a.uid=d.uid and a.rgid=? and c.name=?";
                        pstat3 = conn.prepareStatement(sql3);
                        pstat3.setInt(1, rs1.getInt("rgid"));
                        pstat3.setString(2, sch);
                        ResultSet rs3 = pstat3.executeQuery();
                        String peopleCountOfStr = "";
                        if(rs3.next()){
                            peopleCountOfStr = rs3.getString("peopleCount");
                        }else{
                            peopleCountOfStr = "0";
                        }
                        peopleCountOfInt += Integer.parseInt(peopleCountOfStr);
                        pstat3.close();
                        rs3.close();
                    }
                    map3.put("grantPeopleCount", String.valueOf(peopleCountOfInt));


                    //查询SRO为这个RG所设置的最新的deadline
                    String sql4 = "select a.dtime as hurryDeadline from Deadline a, UcreateD b, User c where a.did=b.did and b.uid=c.uid and c.type='SRO' and rgid=? order by a.present desc limit 0,1";
                    pstat4 = conn.prepareStatement(sql4);
                    pstat4.setInt(1, rs1.getInt("rgid"));
                    ResultSet rs4 = pstat4.executeQuery();
                    if(rs4.next()){
                        if(rs4.getString("hurryDeadline") == null){
                            map3.put("hurryDeadline", "Haven't Yet.");
                        }else{
                            map3.put("hurryDeadline", rs4.getString("hurryDeadline"));
                        }
                    }else{
                        map3.put("hurryDeadline", "Haven't Yet.");
                    }

                    rs4.close();
                    pstat4.close();

                    //查询最新更新的RSO设置的markDown
                    String sql5 = "select mark from MarkDown where uid=? and rgid=? order by present desc limit 0,1";
                    pstat5 = conn.prepareStatement(sql5);
                    pstat5.setInt(1, userId);
                    pstat5.setInt(2, rs1.getInt("rgid"));
                    ResultSet rs5 = pstat5.executeQuery();
                    if(rs5.next()){
                        if(rs5.getString("mark") != null){
                            map3.put("grantMarkDownBySRO", rs5.getString("mark"));
                        }else{
                            map3.put("grantMarkDownBySRO", "Haven't Yet.");
                        }
                    }else{
                        map3.put("grantMarkDownBySRO", "Haven't Yet.");
                    }
                    rs5.close();
                    pstat5.close();

                    list.add(map3);

                }

                rs1.close();
                pstat.close();

            }


        }catch(Exception e){
            e.printStackTrace();
        }



        connPool.pushConnectionBackToPool(conn);
        connPool.pushConnectionBackToPool(conn1);
        return list;

    }


    public List<Map<String,Object>> getSubscirbeDataBySRO(int rgid)throws Exception{


        Connection conn = connPool.getConnection();
        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();

        ActionContext context= ActionContext.getContext();
        Map session = (Map)context.getSession();
        String userName = session.get("userName").toString();//获取session中的用户名，可以根据这个查询提交RG的教授所在的学院
        int userId =  getUserIdByName(userName);//获取用户ID

        try {

            //step1:先把发表这个rgid的人所属的学院选出来，除去None的学院
            String sql1 = "select c.name as schName from User a, UrelateS b, School c where a.uid=b.uid and b.sid=c.sid and c.name != 'None' and a.uid=?";
            pstat = conn.prepareStatement(sql1);
            pstat.setInt(1, userId);
            ResultSet rs1 = pstat.executeQuery();
            while( rs1.next()){
                //针对每个学院查阅订阅的人数
                String schName = rs1.getString("schName");
                String sql2="select b.name as userName from UsubscribeRG a, User b, UrelateS c, School d where a.uid=b.uid and b.uid=c.uid and c.sid=d.sid and a.rgid=? and d.name=?";
                pstat2 = conn.prepareStatement(sql2);
                pstat2.setInt(1, rgid);
                pstat2.setString(2, schName);
                ResultSet rs2 = pstat2.executeQuery();
                String namesForSRO = "";
                while(rs2.next()){
                    namesForSRO += rs2.getString("userName") + " ";
                }

                Map<String,Object> map = new HashMap<String,Object>();
                map.put("schName", schName);
                map.put("namesForSRO", namesForSRO);
                list.add(map);

                rs2.close();
                pstat2.close();
            }

            pstat.close();
            rs1.close();

        }catch(Exception e){
            e.printStackTrace();
        }

        conn.close();
        connPool.pushConnectionBackToPool(conn);
        return list;

    }


    public List<Map<String,Object>> getDeadlineDataByFM(int rgid, int userId)throws Exception {

        Connection conn = connPool.getNewConnection();
        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();

        try{

            String deadline1 = "";
            String deadline2 = "";
            String deadline3 = "";

            String sql1 = "select * from DeadlineByFM where rgid=? and uid=?";
            pstat = conn.prepareStatement(sql1);
            pstat.setInt(1, rgid);
            pstat.setInt(2, userId);
            ResultSet rs1 = pstat.executeQuery();

            while(rs1.next()){
                deadline1 = rs1.getString("deadline1");
                deadline2 = rs1.getString("deadline2");
                deadline3 = rs1.getString("deadline3");
            }

            Map<String,Object> map = new HashMap<String,Object>();
            map.put("deadline1", deadline1);
            map.put("deadline2", deadline2);
            map.put("deadline3", deadline3);
            list.add(map);

            pstat.close();
            rs1.close();

        }catch(Exception e){
            e.printStackTrace();
        }

        conn.close();
        connPool.pushConnectionBackToPool(conn);
        return list;
    }

    public int setDeadlineDataByFM(int rgid, int userId, String deadline1, String deadline2, String deadline3){
        int result = 0;
        Connection conn = connPool.getConnection();

        try{
            String sql1 = "select count(*) as countNum from DeadlineByFM where rgid=? and uid=?";
            pstat = conn.prepareStatement(sql1);
            pstat.setInt(1, rgid);
            pstat.setInt(2, userId);
            ResultSet rs1 = pstat.executeQuery();
            String flag = "";

            if(rs1.next()){
                if(rs1.getString("countNum").equals("0")){
                    flag = "No";
                }else{
                    flag = "Yes";
                }
            }

            if(flag.equals("No")){//没有就插入
                String sql2 = "insert into DeadlineByFM(uid, rgid, deadline1, deadline2, deadline3)values(?,?,?,?,?)";
                pstat2 = conn.prepareStatement(sql2);
                pstat2.setInt(1, userId);
                pstat2.setInt(2, rgid);
                pstat2.setString(3, deadline1);
                pstat2.setString(4, deadline2);
                pstat2.setString(5, deadline3);
                pstat2.executeUpdate();
                pstat2.close();
                result = 1;
            }else{//有就更新原来的
                String sql3 = "update DeadlineByFM set deadline1=?, deadline2=?, deadline3=? where uid=? and rgid=?";
                pstat3 = conn.prepareStatement(sql3);
                pstat3.setString(1, deadline1);
                pstat3.setString(2, deadline2);
                pstat3.setString(3, deadline3);
                pstat3.setInt(4, userId);
                pstat3.setInt(5, rgid);
                pstat3.executeUpdate();
                pstat3.close();
                result = 1;
            }

            pstat.close();
            connPool.pushConnectionBackToPool(conn);

        }catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public String queryDeadlineOfSubscribe() throws Exception{//检查当前时间和订阅RG中RSO和SRO的deadline, 在前一个星期提醒

        String result = "";
        Connection conn = connPool.getNewConnection();
        String emailOfNeedSend = "";
        String deadlineOfRSO = "";
        String deadlineOfSRO = "";


        //获取当前时间前一周的时间
        Format f = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        int lastWeek = 7 - dayOfWeek + 1;
        c.set(Calendar.DAY_OF_MONTH, -lastWeek);
        String myTime = f.format(c.getTime());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try{

            String sql1 = "select * from User a, UsubscribeRG b where a.uid=b.uid";//选出邮箱和RG
            pstat = conn.prepareStatement(sql1);
            ResultSet rs1 = pstat.executeQuery();
            while(rs1.next()){
                int rgid = rs1.getInt("rgid");
                emailOfNeedSend = rs1.getString("email");

                GrantBean g = new GrantBean();
                g.setGrantTitle(rs1.getString("title"));
                g.setGrantSeries(rs1.getString("series"));


                //根据这个rgid选出最近的RSO的Deadline
                String hurryDeadlineOfRSO = "";
                String sql2 = "select min(d.dtime) as dtime from User a, UmanageRG b, UcreateD c, Deadline d where a.uid=b.uid and a.type='RSO' and a.uid=c.uid and c.did=d.did and b.rgid=? and d.dtime !='' ";
                pstat = conn.prepareStatement(sql2);
                ResultSet rs2 = pstat.executeQuery();
                if(rs2.next()){
                    hurryDeadlineOfRSO = rs2.getString("dtime");
                }

                rs2.close();

                String hurryDeadlineOfSRO = "";
                String sql3 = "select dtime from Deadline a, UcreateD b, User c where a.did=b.did and b.uid=c.uid and c.type='SRO' and rgid=? order by present desc limit 0,1";
                pstat = conn.prepareStatement(sql3);
                ResultSet rs3 = pstat.executeQuery();
                if(rs3.next()){
                    hurryDeadlineOfSRO = rs3.getString("dtime");
                }

                rs3.close();

                if(hurryDeadlineOfRSO != null){
                    //和当前时间的前一周比较
                    //间隔天数
                    int days1 = (int) (sdf.parse(myTime).getTime()-sdf.parse(hurryDeadlineOfRSO).getTime()/(24*60*60*1000));
                    if(days1 == 7){
                        this.flagForSendEmail = "Yes";
                        if(this.flagForSendEmail.equals("Yes")){
                            sendEmail2(emailOfNeedSend, hurryDeadlineOfRSO, "sendBeforeRSO", g);
                            this.flagForSendEmail = "No";
                        }
                    }

                }

                if(hurryDeadlineOfSRO != null){
                    //和当前时间的前一周比较
                    int days2 = (int) (sdf.parse(myTime).getTime()-sdf.parse(hurryDeadlineOfSRO).getTime()/(24*60*60*1000));
                    if(days2 == 7){
                        this.flagForSendEmail = "Yes";
                        if(this.flagForSendEmail.equals("Yes")){
                            sendEmail2(emailOfNeedSend, hurryDeadlineOfSRO, "sendBeforeSRO", g);
                            this.flagForSendEmail = "No";
                        }
                    }
                }
            }

            rs1.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        pstat.close();
        connPool.pushConnectionBackToPool(conn);
        return "success";
    }

    public String queryThreeDeadlineOfFM() throws Exception{

        Connection conn = connPool.getNewConnection();
        String timeNow = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

        try{

            String sql1 = "select * from DeadlineByFM a, ResearchGrant b, UsubscribeRG c, User d where a.rgid=b.rgid and b.rgid=c.rgid and c.uid=d.uid";
            pstat = conn.prepareStatement(sql1);
            ResultSet rs1 = pstat.executeQuery();
            while(rs1.next()){

                String email = rs1.getString("email");
                String deadline1 = rs1.getString("deadline1");
                String deadline2 = rs1.getString("deadline2");
                String deadline3 = rs1.getString("deadline3");

                GrantBean g = new GrantBean();
                g.setGrantSeries("series");
                g.setGrantTitle(rs1.getString("title"));

                if(deadline1.equals(timeNow)){

                    this.flagForSendEmail = "Yes";
                    if(this.flagForSendEmail.equals("Yes")){
                        sendEmail2(email, deadline1, "sendWhenFM", g);
                        this.flagForSendEmail = "No";
                    }


                }

                if(deadline2.equals(timeNow)){

                    this.flagForSendEmail = "Yes";
                    if(this.flagForSendEmail.equals("Yes")){
                        sendEmail2(email, deadline2, "sendWhenFM", g);
                        this.flagForSendEmail = "No";
                    }

                }

                if(deadline3.equals(timeNow)){

                    this.flagForSendEmail = "Yes";
                    if(this.flagForSendEmail.equals("Yes")){
                        sendEmail2(email, deadline3, "sendWhenFM", g);
                        this.flagForSendEmail = "No";
                    }

                }

            }

            rs1.close();


        }catch(Exception e){
            e.printStackTrace();
        }

        pstat.close();

        connPool.pushConnectionBackToPool(conn);
        return "success";

    }


    public List<Map<String,Object>> getChartData()throws Exception{

        Connection conn = connPool.getConnection();

        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();

        try {

            /*查询每个学院订阅这个rg的人和所在的学院统计,用json做！！！*/
            //step1:现把学院选择出来并且去除None的学院

            String sql1 = "select * from School where name !='None'";
            pstat = conn.prepareStatement(sql1);
            ResultSet rs1 = pstat.executeQuery();

            while (rs1.next()) {//统计每个学院订阅的人数


                //SubscribeBean s = new SubscribeBean();
                String sql2 = "select count(*) from ResearchGrant a, UsubscribeRG b, UrelateS c, School d WHERE a.rgid=b.rgid and a.status='fresh' and b.uid=c.uid and c.sid=d.sid and d.name=?";
                String schName = rs1.getString("name");
                pstat = conn.prepareStatement(sql2);
                pstat.setString(1, schName);

                ResultSet rs2 = pstat.executeQuery();
                rs2.next();

                Map<String,Object> map = new HashMap<String,Object>();
                map.put("name",schName);
                map.put("number",rs2.getString(1));
                map.put("type", "fresh");
                list.add(map);

                rs2.close();

                String sql3 = "select count(*) from ResearchGrant a, UsubscribeRG b, UrelateS c, School d WHERE a.rgid=b.rgid and a.status='outdate' and b.uid=c.uid and c.sid=d.sid and d.name=?";
                pstat = conn.prepareStatement(sql3);
                pstat.setString(1, schName);

                ResultSet rs3 = pstat.executeQuery();
                rs3.next();

                Map<String,Object> map2 = new HashMap<String,Object>();
                map2.put("name",schName);
                map2.put("number",rs3.getString(1));
                map2.put("type", "outdate");
                list.add(map2);

                rs3.close();
                /*查询每个学院订情况*/
            }

            pstat.close();
            rs1.close();

        }catch(Exception e){
            e.printStackTrace();
        }

        conn.close();
        connPool.pushConnectionBackToPool(conn);
        return list;

    }


}
