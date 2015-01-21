package Bean;

/**
 * Created by el1ven on 19/9/14.
 */
public class UserBean {//实体类user

    private String userType;//用户类型
    private String userName;//用户名
    private String userPassword;//用户密码
    private String userEmail;//用户邮箱

    private String userSchool1;//用户所属的学院
    private String userSchool2;//用户所属的学院2
    private String userSchool3;//用户所属的学院3

    private String userRemember;//是否记住用户设置session


    //相应的get,set函数
    public void setUserType(String userType){this.userType = userType;}
    public String getUserType(){return this.userType;}

    public void setUserName(String userName){this.userName = userName;}
    public String getUserName(){return this.userName;}

    public void setUserPassword(String userPassword){this.userPassword = userPassword;}
    public String getUserPassword(){return this.userPassword;}

    public void setUserEmail(String userEmail){this.userEmail = userEmail;}
    public String getUserEmail(){return this.userEmail;}

    public void setUserSchool1(String userSchool1){this.userSchool1 = userSchool1;}
    public String getUserSchool1(){return this.userSchool1;}

    public void setUserSchool2(String userSchool2){this.userSchool2 = userSchool2;}
    public String getUserSchool2(){return this.userSchool2;}

    public void setUserSchool3(String userSchool3){this.userSchool3 = userSchool3;}
    public String getUserSchool3(){return this.userSchool3;}

    public void setUserRemember(String userRemember){this.userRemember = userRemember;}
    public String getUserRemember(){return this.userRemember;}
}
