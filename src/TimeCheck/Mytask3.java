package TimeCheck;

import DataBase.DBManager;

import javax.servlet.ServletContext;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

/**
 * Created by el1ven on 29/12/14.
 */
public class Mytask3 extends TimerTask {//检测FM为每个RG定制的3个日期，提醒他

    private static final int START = 0;//开始时时间为每天凌晨
    private static boolean isRunning = false;//检测是否执行
    private ServletContext context = null;

    public Mytask3(ServletContext context){//拷贝构造函数
        this.context = context;
    }

    public void run(){

        Calendar cal = Calendar.getInstance();
        if(!isRunning){
            if(START == cal.get(Calendar.HOUR_OF_DAY)){
                isRunning = true;
                context.log("开始执行任务");

                String result = "";

                try {
                    result = DBManager.getInstance().queryThreeDeadlineOfFM();//检查RSO和SRO的前一周的deadline
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
