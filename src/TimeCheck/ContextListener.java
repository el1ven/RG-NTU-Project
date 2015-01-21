package TimeCheck;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Timer;

/**
 * Created by el1ven on 27/12/14.
 */
public class ContextListener implements ServletContextListener {

    private Timer timer =null; //定时器1定时检查ResearchGrant是否过期
    private Timer timer2 = null; //定时器2给订阅RG的人在RSO和SRO定的日期的前一周发邮件
    private Timer timer3 = null;//FM自己设定的3个日期

    @Override
    public void contextInitialized(ServletContextEvent event) {

        timer = new Timer(true);
        event.getServletContext().log("定时器1已经启动");
        timer.schedule(new Mytask(event.getServletContext()), 0, 60*60*1000);
        event.getServletContext().log("1已经添加任务调度表");

        timer2 = new Timer(true);
        event.getServletContext().log("2定时器已经启动");
        timer2.schedule(new Mytask2(event.getServletContext()), 0, 60*60*1000);
        event.getServletContext().log("2已经添加任务调度表");

        timer3 = new Timer(true);
        event.getServletContext().log("3定时器已经启动");
        timer3.schedule(new Mytask3(event.getServletContext()), 0, 60*60*1000);
        event.getServletContext().log("3已经添加任务调度表");

    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {

        timer.cancel();
        event.getServletContext().log("定时器1销毁");

        timer2.cancel();
        event.getServletContext().log("定时器2销毁");

        timer3.cancel();
        event.getServletContext().log("定时器3销毁");

    }
}
