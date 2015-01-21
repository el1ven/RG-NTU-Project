package Action;

import Bean.GrantBean;
import com.opensymphony.xwork2.ActionSupport;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.*;

/**
 * Created by el1ven on 29/12/14.
 */
public class EmailAction extends ActionSupport implements Runnable {

    private String from;
    private String password;
    private String to;
    private String subject;
    private String body;

    private String info;//判断执行哪种操作，邮件内容如何定义

    private ArrayList<String> uidOfNeedSend;
    private ArrayList<String> emailOfNeedSend;

    private GrantBean grantPosted;


    private String oneEmail;
    private String hurryDeadline;


    static Properties properties = new Properties();
    static{
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
    }

    /*public String execute()throws Exception{
        String flag = "success";
        try{

            Session session = Session.getDefaultInstance(
                    properties, new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(from, password);
                        }
                    }
            );

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);

        }catch(Exception e){
            flag = "fail";
            e.printStackTrace();

        }

        return flag;
    }*/

    public EmailAction(){}//没有这个初始化函数会报错，不知道为什么

    public EmailAction(ArrayList<String> emailOfNeedSend,GrantBean grantPosted, String info){
        this.emailOfNeedSend = emailOfNeedSend;
        this.grantPosted = grantPosted;
        this.info = info;
        //接收传的参数并在run中使用
    }

    public EmailAction(String oneEmail, String hurryDeadline, String info, GrantBean grantPosted){
        this.oneEmail = oneEmail;
        this.hurryDeadline = hurryDeadline;
        this.info = info;
        this.grantPosted = grantPosted;
        //接收传的参数并在run中使用
    }

    public void setUidOfNeedSend(ArrayList<String> uidOfNeedSend){this.uidOfNeedSend = uidOfNeedSend;}
    public ArrayList<String> getUidOfNeedSend(){return this.uidOfNeedSend;}

    public void setEmailOfNeedSend(ArrayList<String> emailOfNeedSend){this.emailOfNeedSend = emailOfNeedSend;}
    public ArrayList<String> getEmailOfNeedSend(){return this.emailOfNeedSend;}

    public void setGrantPosted(GrantBean grantPosted){this.grantPosted = grantPosted;}
    public GrantBean getGrantPosted(){return this.grantPosted;}

    public void setFrom(String from) {this.from = from;}
    public String getFrom() {return from;}

    public void setPassword(String password) {this.password = password;}
    public String getPassword() {return password;}

    public void setTo(String to) {this.to = to;}
    public String getTo() {return to;}

    public void setSubject(String subject) {this.subject = subject;}
    public String getSubject() {return subject;}

    public void setBody(String body) {this.body = body;}
    public String getBody() {return body;}

    public static void setProperties(Properties properties) {EmailAction.properties = properties;}
    public static Properties getProperties() {return properties;}

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getOneEmail() {
        return oneEmail;
    }

    public void setOneEmail(String oneEmail) {
        this.oneEmail = oneEmail;
    }

    public String getHurryDeadline() {
        return hurryDeadline;
    }

    public void setHurryDeadline(String hurryDeadline) {
        this.hurryDeadline = hurryDeadline;
    }

    @Override
    public void run() {

       System.out.println("HAHA1");
        String flag = "";

        try{

            this.from = "tangGPA@gmail.com";
            this.password = "19920731asdasd";

            if(this.info.equals("sendWhenFM")) {

                this.subject = "Alert From the RG Deadline You have Subscribed";
                this.body += this.hurryDeadline + "\n";
                this.body += "GrantSeries: "+this.grantPosted.getGrantSeries() +"\n" + "GrantTitle: "+this.grantPosted.getGrantTitle() +"\n";
                this.to = "node.ntu@gmail.com";


            }else if(this.info.equals("sendBeforeRSO")){

                this.subject = "Alert Before RSO Latest Deadline";
                this.body = "GrantSeries: "+this.grantPosted.getGrantSeries() +"\n" + "GrantTitle: "+this.grantPosted.getGrantTitle() +"\n";
                this.to = "node.ntu@gmail.com";

            }else if(this.info.equals("sendBeforeSRO")){

                this.subject = "Alert Before SRO Latest Deadline";
                this.body = "GrantSeries: "+this.grantPosted.getGrantSeries() +"\n" + "GrantTitle: "+this.grantPosted.getGrantTitle() +"\n";
                this.to = "node.ntu@gmail.com";

            }else{

                int sendCount = emailOfNeedSend.size();

                for(int i = 0; i < sendCount; i++){

                    if(this.info.equals("sendAfterCreate")){
                        System.out.println("HAHA2");
                        this.subject = "New Grant!!!";
                    }
                    if(this.info.equals("sendAfterModify")){
                        this.subject = "The subscribed ResearchGrant has changed by now!";
                    }

                    this.body = "GrantSeries: "+this.grantPosted.getGrantSeries() +"\n" + "GrantTitle: "+this.grantPosted.getGrantTitle() +"\n";
                    this.to = "node.ntu@gmail.com";

            }



                Session session = Session.getDefaultInstance(
                        properties, new Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(from, password);
                            }
                        }
                );

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject(subject);
                message.setText(body);
                this.body = "";
                Transport.send(message);
                flag = "success";
                System.out.println("FLAG    "+flag);
                System.out.println("HAHA3");

            }


        }catch(Exception e){
            flag = "fail";
            System.out.println("FLAG    "+flag);
            e.printStackTrace();
        }


    }
}
