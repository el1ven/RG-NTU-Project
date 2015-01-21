package Bean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by el1ven on 27/10/14.
 */
public class GrantBean {//实体类research grant
    //几个个基本属性
    private int grantId;//增删改查会用到的
    private String grantTitle;
    private String grantSeries;
    private String grantContact;
    private String grantContent;
    private String grantStatus = "fresh";//是否过期
    private String grantWhen;//grant上传时间
    private String grantSchool;//提交这个grant research的教授所在的学院，可以有多个，这里组成一个字符串
    private String grantPeopleCount;//订阅这个grant的人数

    //下面这些属性主要用于点击标题显示细节的部分
    private String grantDeadlineOfDetail;
    private String grantDeadlineDescriptionOfDetail;
    private String grantFilePathOfDetail;
    private String grantFileNameOfDetail;
    private String grantFileName2OfDetail;//空格的转义


    //为了modify页面删除指定id的deadline我们需要先获取每个RG的deadline的id
    private String grantDeadlineIdOfDetail;
    //为了modify页面删除指定id的file我们需要先获取每个RG的file的id
    private String grantFileIdOfDetail;

    //用于FM1界面，如果已经添加到购物车，那么这个checkbox就变成不可选的
    private String grantChecked;

    //用于RSO页面显示各院订阅人数统计
    private String subscribePeopleCountForRSO;


    //上传文件部分，命名有规则前缀必须一样，XXX, XXXFileNames, XXXContentTypes;
    private List<File> upload;//上传的多个文件
    private List<String> uploadFileNames;//上传文件的名字集合
    private List<String> uploadContentTypes;//上传文件类型集合

    //截止日期部分，每个deadline包括一个date和description
    private List<String> deadlineDates;//截止日期集合
    private List<String> deadlineContents;//截止日期的内容集合
    private String hurryDeadline;//最近日期的deadline, RSO
    private String hurryDeadline2;//最近日期的deadline, SRO


    //为了formModify修改页面，修改的内容根据这个id更新数据库
    public String grantIdOfStr;

    //为了RSO和SRO的archive功能
    private String markdown;
    private String markdown2;

    //属性相应的get,set函数
    public void setGrantId(int grantId){this.grantId= grantId;}
    public int getGrantId(){return this.grantId;}

    public void setGrantIdOfStr(String grantIdOfStr){ this.grantIdOfStr = grantIdOfStr;}
    public String getGrantIdOfStr(){return this.grantIdOfStr;}

    public void setGrantTitle(String grantTitle){this.grantTitle= grantTitle;}
    public String getGrantTitle(){return this.grantTitle;}

    public void setGrantSeries(String grantSeries){this.grantSeries= grantSeries;}
    public String getGrantSeries(){return this.grantSeries;}

    public void setGrantContact(String grantContact){this.grantContact= grantContact;}
    public String getGrantContact(){return this.grantContact;}

    public void setGrantContent(String grantContent){this.grantContent= grantContent;}
    public String getGrantContent(){return this.grantContent;}

    public void setGrantStatus(String grantStatus){this.grantStatus= grantStatus;}
    public String getGrantStatus(){return this.grantStatus;}

    public void setGrantWhen(String grantWhen){this.grantWhen= grantWhen;}
    public String getGrantWhen(){return this.grantWhen;}

    public void setGrantSchool(String grantSchool){this.grantSchool= grantSchool;}
    public String getGrantSchool(){return this.grantSchool;}

    public void setGrantPeopleCount(String grantPeopleCount){this.grantPeopleCount = grantPeopleCount;}
    public String getGrantPeopleCount(){return this.grantPeopleCount;}

    public void setUpload (List<File> upload){this.upload = upload;}
    public List<File> getUpload (){return this.upload;}

    public void setUploadFileName (List<String> uploadFileNames){this.uploadFileNames = uploadFileNames;}
    public List<String> getUploadFileName(){return this.uploadFileNames;}

    public void setUploadContentType(List<String> uploadContentTypes){this.uploadContentTypes = uploadContentTypes;}
    public List<String> getUploadContentType(){return this.uploadContentTypes;}

    public void setDeadlineDates(List<String> deadlineDates){this.deadlineDates = deadlineDates;}
    public List<String> getDeadlineDates(){return this.deadlineDates;}

    public void setDeadlineContents(List<String> deadlineContents){this.deadlineContents = deadlineContents;}
    public List<String> getDeadlineContents(){return this.deadlineContents;}

    public void setHurryDeadline(String hurryDeadline){this.hurryDeadline= hurryDeadline;}
    public String getHurryDeadline(){return this.hurryDeadline;}

    public void setHurryDeadline2(String hurryDeadline2){this.hurryDeadline2 = hurryDeadline2;}
    public String getHurryDeadline2(){return this.hurryDeadline2;}

    public void setGrantDeadlineIdOfDetail(String grantDeadlineIdOfDetail){this.grantDeadlineIdOfDetail = grantDeadlineIdOfDetail;}
    public String getGrantDeadlineIdOfDetail(){return this.grantDeadlineIdOfDetail;}

    public void setGrantDeadlineOfDetail(String grantDeadlineOfDetail){this.grantDeadlineOfDetail = grantDeadlineOfDetail;}
    public String getGrantDeadlineOfDetail(){return this.grantDeadlineOfDetail;}

    public void setGrantDeadlineDescriptionOfDetail(String grantDeadlineDescriptionOfDetail){this.grantDeadlineDescriptionOfDetail = grantDeadlineDescriptionOfDetail;}
    public String getGrantDeadlineDescriptionOfDetail(){return this.grantDeadlineDescriptionOfDetail;}

    public void setGrantFileIdOfDetail(String grantFileIdOfDetail){this.grantFileIdOfDetail = grantFileIdOfDetail;}
    public String getGrantFileIdOfDetail(){return this.grantFileIdOfDetail;}

    public void setGrantFilePathOfDetail(String grantFilePathOfDetail){this.grantFilePathOfDetail = grantFilePathOfDetail;}
    public String getGrantFilePathOfDetail(){return this.grantFilePathOfDetail;}

    public void setGrantFileNameOfDetail(String grantFileNameOfDetail){this.grantFileNameOfDetail = grantFileNameOfDetail;}
    public String getGrantFileNameOfDetail(){return this.grantFileNameOfDetail;}

    public void setGrantFileName2OfDetail(String grantFileName2OfDetail){this.grantFileName2OfDetail = grantFileName2OfDetail;}
    public String getGrantFileName2OfDetail(){return this.grantFileName2OfDetail;}

    public void setGrantChecked(String grantChecked){this.grantChecked = grantChecked;}
    public String getGrantChecked(){return this.grantChecked;}

    public void setSubscribePeopleCountForRSO(String subscribePeopleCountForRSO){this.subscribePeopleCountForRSO = subscribePeopleCountForRSO;}
    public String getSubscribePeopleCountForRSO(){return this.subscribePeopleCountForRSO;}

    public void setMarkdown(String markdown){this.markdown = markdown;}
    public String getMarkdown(){return this.markdown;}

    public void setMarkdown2(String markdown2){this.markdown2 = markdown2;}
    public String getMarkdown2(){return this.markdown2;}

}
