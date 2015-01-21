package Action;

import DataBase.*;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.tools.doclets.internal.toolkit.util.DocFinder;
import org.apache.struts2.ServletActionContext;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

/*
 * Created by el1ven on 22/12/14.
 */
public class DownloadAction extends ActionSupport {
    private String fileName;
    private String contentType;//此属性与struts.xml的下载文件属性相对应

    public void setFileName(String fileName){this.fileName = fileName;}
    public String getFileName(){return this.fileName;}

    public void setContentType(String contentType){this.contentType = contentType;}
    public String getContentType(){return this.contentType;}

    //返回一个输入流，作为一个客户端来说是一个输入流，但对于服务器端是一个 输出流
    public InputStream getTargetFile() throws Exception{

        //获取文件路径,是获取上传者的文件夹
        String basePath = ServletActionContext.getServletContext().getRealPath("/");
        this.fileName = (ServletActionContext.getRequest().getParameter("fileName")).replace("~"," ");
        String fileIdOfUploadPerson = ServletActionContext.getRequest().getParameter("grantId");

        //获取格式
        String fileType=this.fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());

        //让多种多样的文件都可以下载 contentType直接对应struts.xml配置文件中的下载文件类型属性

        if (fileType.equals("xls")) {
            this.contentType = "application/vnd.ms-excel";

        } else if (fileType.equals("doc") || fileType.equals("docx")) {
            this.contentType = "application/vnd.ms-word";

        } else if (fileType.equals("pdf")) {
            this.contentType = "application/pdf";

        } else if (fileType.equals("txt")) {
            this.contentType = "text/plain";

        } else if (fileType.equals("jpg")) {
            this.contentType = "image/jpeg";

        } else {
            this.contentType = "text/plain";
        }

        //int fileIdOfInt = Integer.parseInt(fileIdOfUploadPerson);
        //int userIdOfUploadPerson = DBManager.getInstance().getUserIdByRGID(fileIdOfInt);

        String finalPath = basePath + "upload/" + fileIdOfUploadPerson + "/" + this.fileName;//获取文件上传路径

        String finalPathOfDealWith =  new String(finalPath.getBytes(), "ISO8859-1");//文件名是中文的相关处理

        //System.out.println(finalPath);

        //InputStream is = ServletActionContext.getServletContext().getResourceAsStream(finalPathOfDealWith);
        System.out.println(finalPathOfDealWith);
        InputStream is = new FileInputStream(finalPathOfDealWith);

        return is;
    }

    public String execute() throws Exception {
        return "success";
    }
}
