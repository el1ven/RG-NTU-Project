package Action;

import DataBase.DBManager;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by el1ven on 2/1/15.
 */
public class AddDeadlineBySROAction extends ActionSupport {

    private String grantId;
    private String deadline;
    private String description;

    public String execute() throws Exception{
        int result = DBManager.getInstance().addOneDeadlineBySRO(this.grantId, this.deadline, this.description);
        if(result > 0){
            return "success";
        }else{
            return "fail";
        }
    }

    public void setGrantId(String grantId){this.grantId = grantId;}
    public String getGrantId(){return this.grantId;}

    public void setDeadline(String deadline){this.deadline = deadline;}
    public String getDeadline(){return this.deadline;}

    public void setDescription(String description){this.description = description;}
    public String getDescription(){return this.description;}

}
