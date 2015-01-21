package Bean; /**
 * Created by el1ven on 12/12/14.
 */
import Bean.GrantBean;

import java.util.*;
public class PageBean {//分页类
    public int totalPages = 0;//总页数

    private int currentPage = 1;//当前页数第一页
    private int numPerPage = 10;//每页的数据个数
    private int totalDataCount = 0;//总的数据个数
    private int pageStart = 0;//每页显示数据的起始数
    private int pageEnd = 0;//每页显示数据的终止数
    private boolean hasNextPage = false;//是否有下一页
    private boolean hasPreviousPage = false;//是否有上一页

    private ArrayList<GrantBean> arrayList;//存储数据

    public PageBean(){}//构造函数

    public PageBean(ArrayList arrayList){
        this.arrayList = arrayList;
        totalDataCount = this.arrayList.size();
        currentPage = 1;
        hasPreviousPage = false;

        if(totalDataCount%numPerPage == 0){
            totalPages = totalDataCount/numPerPage;//求出总页数
        }else{
            totalPages = totalDataCount/numPerPage + 1;
        }

        if(currentPage >= totalPages){
            hasNextPage = false;
        }else{
            hasNextPage = true;
        }

        if(totalDataCount < numPerPage){//总数据量不足够一页
            pageStart = 0;
            pageEnd = totalDataCount;
        }else{
            pageStart = 0;
            pageEnd = numPerPage;
        }
    }


    public String getCurrentPage() {
        return String.valueOf(currentPage);//获得字符串类型的当前页数
    }

    public void setCurrentPage(int currentPage){
        this.currentPage = currentPage;
    }

    public int getNumPerPage(){
        return this.numPerPage;
    }

    public void setNumPerPage(int numPerPage){
        this.numPerPage = numPerPage;
    }

    public int getPageStart(){
        return this.pageStart;
    }

    public int getPageEnd(){
        return this.pageEnd;
    }

    public String getTotalPages(){
        return String.valueOf(totalPages);//返回字符串类型的总页数
    }

    public String getTotalDataCount(){
        return String.valueOf(totalDataCount);//返回字符串类型的总数据个数
    }

    public boolean isHasNextPage(){
        return this.hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage){
        this.hasNextPage = hasNextPage;
    }

    public boolean isHasPreviousPage(){
        return this.hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage){
        this.hasPreviousPage = hasPreviousPage;
    }

    public ArrayList<GrantBean> getNextPage(){
        currentPage++;

        if(currentPage>1){
            hasPreviousPage = true;
        }else{
            hasPreviousPage = false;
        }

        if(currentPage >= totalPages){
            hasNextPage = false;
        }else{
            hasNextPage = true;
        }

        return getData();//设定完参数之后再次获取数据

    }

    public ArrayList<GrantBean> getPreviousPage(){
        currentPage--;

        if(currentPage == 0){
            currentPage = 1;
        }

        if(currentPage > totalPages){
            hasNextPage = false;
        }else{
            hasNextPage = true;
        }

        if(currentPage > 1){
            hasPreviousPage = true;
        }else{
            hasPreviousPage = false;
        }

        return getData();//设定完参数之后再次获取数据

    }

    public ArrayList<GrantBean> getData(){
        if(currentPage*numPerPage < totalDataCount){//判断是否为最后一页
            pageEnd = currentPage * numPerPage;
            pageStart = pageEnd - numPerPage;
        }else{
            pageEnd = totalDataCount;
            pageStart = (currentPage-1)*numPerPage;
        }

        ArrayList<GrantBean> DataPerPage = new ArrayList<GrantBean>();
        int count = pageEnd - pageStart + 1;//每页的数据数目
        for(int i = 0; i < count; i++){
            DataPerPage.add(arrayList.get(i));//从总的数据中取出本页需要的数据
        }

        return DataPerPage;
    }

}
