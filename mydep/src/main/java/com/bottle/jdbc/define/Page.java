package com.bottle.jdbc.define;
import com.bottle.jdbc.JDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: leeping
 * @Date: 2019/8/5 10:05
 */
public class Page{
    private static final Logger logger = LoggerFactory.getLogger(Page.class);

    private static final String NO_QUERY_PAGE = "NO_QUERY_PAGE";
    private int pageIndex;
    private int pageSize;
    private int totalItems;
    private String selectTotalSql;

    private void initParam(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public Page(int pageIndex, int pageSize) {
       initParam(pageIndex,pageSize);
        logger.debug(Thread.currentThread() + " 分页 Page : pageIndex=" + pageIndex+" pageSize="+pageSize );
    }

    /**
    * int[]{负数,负数} 查询分页总条数,不执行实际SQL
    * int[]{0,0} 仅执行实际SQL
    * int[]{正数,正数} 分页查询
    * */
    public Page(int[] array) {
        int pageIndex,pageSize;
        if (array == null || array.length < 2) {
            pageIndex = 0;
            pageSize  = 0;
        } else {
            pageIndex = array[0];
            pageSize = array[1];
        }
        initParam(pageIndex,pageSize);
    }

    public Page(){
        initParam(0,0);
    }

    /* 检查是否查询真实SQL */
    public static boolean checkQuery(Page page) {
        logger.debug("checkQuery : " + (page==null || (page.pageIndex>=0 && page.pageSize>=0) ));
        return page==null || (page.pageIndex>=0 && page.pageSize>=0);
    }

    /* 数据库 分页 */
    public static String executeDatabasePaging(OptionI optionI, String sql, Object[] params, Page page) {
        if (page!=null && ( (page.pageIndex>0 && page.pageSize>0) || (page.pageIndex<0 && page.pageSize<0) )  ) {
            sql = page.executePagingReturnSql(optionI,sql,params);
        }
        return sql;
    }

    /* 内存分页 */
    public static <T extends Object> List<T> executeMemoryPaging(Page page, List<T> result) {
        if (page!=null){
            return page.executeResult(result);
        }
        return result;
    }

    /* 设置查询总条数sql */
    public Page setSelectTotalSql(String selectTotalSql) {
        this.selectTotalSql = selectTotalSql;
        return this;
    }

    public Page setNotQuerySelectTotal(){
        this.selectTotalSql = NO_QUERY_PAGE;
        return this;
    }

    /* 数据库分页 */
    private String executePagingReturnSql(OptionI optionI, String sql, Object[] params){

        if (pageIndex == 0 && pageSize == 0 ) return sql; //不分页

        int lastIndex = sql.lastIndexOf(";");
        if (lastIndex>0) sql = sql.substring(0,lastIndex);

        //查询全部条数SQL
        String _selectTotalSql = "SELECT COUNT(0) FROM ( " + sql + " ) TEMP";
        if ( selectTotalSql!=null){
            _selectTotalSql = selectTotalSql;
            selectTotalSql = null;
        }

        logger.debug(Thread.currentThread()+ " 分页 SQL : " + _selectTotalSql);
        if (!_selectTotalSql.equals(NO_QUERY_PAGE)){
            long ts = System.currentTimeMillis();
            List<Object[]> selectTotalResult = optionI.query(_selectTotalSql, params,null);

            if (selectTotalResult.size() != 1){
                throw new JDBCException("【数据库分页异常】\n\t" + _selectTotalSql+"\n\t"+ Arrays.toString(params));
            }

            // 总数
            this.totalItems = Integer.parseInt(String.valueOf(selectTotalResult.get(0)[0]));
            logger.debug(Thread.currentThread()+ " 分页 总数 : " +  totalItems +" 用时: "+ (System.currentTimeMillis() - ts));
        }

        if (pageIndex>0 && pageSize>0){
            int index = (pageIndex - 1) * pageSize ;
            int number = pageSize;
            sql =  sql + " LIMIT "+ index+ "," + number;
        }
        logger.debug(Thread.currentThread()+" 分页 实际查询SQL : " + sql);

        return sql;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    /* 内存分页 */
    private  <T extends Object> List<T> executeResult(List<T> result) {
        totalItems = result.size();
        int start = (pageIndex - 1) * pageSize;
        if (start<0) start = 0;
        if (start>totalItems) return result;
        if ( pageSize <= 0 ) return result;
        int end = start + pageSize;
        if (end>=totalItems) end = totalItems;
        return result.subList(start,end);
    }

}
