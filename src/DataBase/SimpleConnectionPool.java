package DataBase; /**
 * Created by el1ven on 23/9/14.
 */
import com.mysql.jdbc.jdbc2.optional.ConnectionWrapper;

import java.sql.*;
import java.lang.reflect.*;
import java.util.*;
import java.io.*;
public class SimpleConnectionPool {

    private static LinkedList notUsedConnection = new LinkedList();//没有使用的数据库连接集合
    private static HashSet usedConnection = new HashSet();//使用过的数据库连接集合

    private static String urlForSql = "jdbc:mysql://localhost:3306/ntuPro";//数据库连接信息
    private static String userForSql = "root";
    private static String passwordForSql = "asdasd";

    private int connectNum;//刚开始的连接数目

    static private long lastClearClosedConnection = System.currentTimeMillis();//清除关闭连接的系统时间

    public static long checkClosedConnectionTime = 4 * 60 * 60 *1000;//4小时


    public SimpleConnectionPool(){//初始化函数
        Driver driver = null;//load mysql driver
        try{
            driver = (Driver)Class.forName("com.mysql.jdbc.Driver").newInstance();
            DriverManager.registerDriver(driver);//register the driver
            connectNum = 20;//初始化连接数目
            createPool(connectNum);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void createPool(int count){//初始化创建数据库连接池
        for(int i = 0; i < count; i++){
            Connection conn = getNewConnection();
            notUsedConnection.addLast(conn);
        }
    }

    public static synchronized Connection getConnection(){
        clearClosedConnection();//每次都要清除不用的连接
        while(notUsedConnection.size() > 0){
            Connection conn =(Connection)notUsedConnection.removeFirst();
            usedConnection.add(conn);
            return conn;
        }

        int newCount = getIncreasingConnectionCount();
        LinkedList list = new LinkedList();
        Connection conn  = null;
        for(int i = 0; i < newCount; i++){
            if(conn != null)list.add(conn);
        }
        if(list.size() == 0)return null;

        conn = (Connection) list.removeFirst();
        usedConnection.add(conn);
        notUsedConnection.addAll(list);

        list.clear();
        return conn;
    }

    public static Connection getNewConnection(){
        try{
            Connection conn = DriverManager.getConnection(urlForSql, userForSql, passwordForSql);
            return conn;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    static synchronized void pushConnectionBackToPool(Connection conn){
        boolean exist = usedConnection.remove(conn);
        if(exist)notUsedConnection.addLast(conn);
    }

    public static int close(){
        int count = 0;

        Iterator it = notUsedConnection.iterator();
        while(it.hasNext()){
            try {
                ((Connection)it.next()).close();
                count++;//关闭计数
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        notUsedConnection.clear();

        it = usedConnection.iterator();
        while(it.hasNext()){
            try {
                Connection conn = (Connection)it.next();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        usedConnection.clear();

        return count;
    }

    private static void clearClosedConnection() {
        long time = System.currentTimeMillis();

        if (time < lastClearClosedConnection) return;//如果用户改了系统时间，那么直接返回
        if (time - lastClearClosedConnection < checkClosedConnectionTime) return;

        lastClearClosedConnection = time;

        //begin check
        Iterator it = notUsedConnection.iterator();
        while (it.hasNext()) {
            Connection conn = (Connection) it.next();
            try {
                if (conn.isClosed()) it.remove();
            } catch (SQLException e) {
                e.printStackTrace();
                it.remove();
            }

        }

        //make connection pool size smaller if too big
        int decrease = getDecreasingConnectionCount();
        if (notUsedConnection.size() < decrease) return;
        while (decrease-- > 0) {
            Connection conn = (Connection) notUsedConnection.removeFirst();
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    //get increasing connection count
    public static int getIncreasingConnectionCount(){
        int count = 1;
        int current  = getConnectionCount();
        count = current/4 ;
        if(count < 1) count = 1;
        return count;
    }

    //get decreasing connection count
    public static int getDecreasingConnectionCount(){
        int count = 0;
        int current = getConnectionCount();
        if(current < 10)return 0;
        return current/3;
    }

    public static synchronized int getConnectionCount(){
        return notUsedConnection.size() + usedConnection.size();
    }

    public String getUrlForSql(){
        return urlForSql;
    }

    public void setUrlForSql (String urlForSql){
        this.urlForSql = urlForSql;
    }

    public String getUserForSql(){
        return userForSql;
    }

    public void setUserForSql(String userForSql){
        this.userForSql = userForSql;
    }

    public String getPasswordForSql(){
        return passwordForSql;
    }

    public void setPasswordForSql(String passwordForSql){
        this.passwordForSql = passwordForSql;
    }

    public int getConnectNum(){return connectNum;}

    public void setConnectNum(int connectNum){this.connectNum = connectNum;}

}
