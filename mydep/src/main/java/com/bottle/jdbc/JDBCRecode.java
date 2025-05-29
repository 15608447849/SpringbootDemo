package com.bottle.jdbc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JDBCRecode {
    private static final Logger logger = LoggerFactory.getLogger(JDBCRecode.class);
    private static final ThreadLocal<Throwable> errors = new ThreadLocal<>();

    public static Throwable currentThreadJdbcError(){
        return errors.get();
    }


    public static void writeSlowQuery(String sql,long connTime,long exeTime,long readTime){

        String _message = String.format("%s\n\tconnect time: %d ms, execute time: %d ms, read time: %d ms",sql,connTime,exeTime,readTime);
        logger.debug(_message);
    }

    public static void error(String desc,Throwable e){
        errors.set(e);
        desc = desc == null ? "":desc ;
        logger.error(desc,e);
    }


}
