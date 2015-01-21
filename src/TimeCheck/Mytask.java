package TimeCheck;

import DataBase.DBManager;

import javax.servlet.ServletContext;
import java.util.Calendar;
import java.util.TimerTask;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by el1ven on 27/12/14.
 */
public class Mytask extends TimerTask {//检测邮件是否过期的task

    private static final int START = 0;//开始时时间为每天凌晨
    private static boolean isRunning = false;//检测是否执行
    private ServletContext context = null;

    public Mytask(ServletContext context){//拷贝构造函数
        this.context = context;
    }

    public void run(){

        Calendar cal = Calendar.getInstance();
        if(!isRunning){
            if(START == cal.get(Calendar.HOUR_OF_DAY)){
                isRunning = true;
                context.log("开始执行任务");

                //检查research grant是否过期,如果过期修改status字段为outdate

                //首先获取当前时间
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String currentTime =  formatter.format(new Date());

                //检查更新

                String result = "";

                try {
                    result = DBManager.getInstance().queryForLastestDeadline(currentTime);//检查更新
                    context.log(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                isRunning = false;
            }
        }else{
            context.log("上一次的任务还没执行结束");
        }
    }


}
