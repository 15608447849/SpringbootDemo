package com.bottle.jdbc;

import com.bottle.jdbc.define.DataBaseType;
import com.bottle.jdbc.imp.JDBCConnection;
import com.bottle.jdbc.imp.JDBCSessionFacade;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class JDBC {
    private static final Logger logger = LoggerFactory.getLogger(JDBC.class);

    /* 数据库类型@数据库名 <-> 数据库连接池对象 */
    private final static Map<String , JDBCConnection> databasePoolMap = new HashMap<>();

    public static void loadDatabase(Properties... properties) {
        for (Properties property : properties) {
            try {
                JDBCConnection pool = loadDatabase(property);
            } catch (Exception e) {
                JDBCRecode.error("loadDatabase 错误",e);
            }
        }
    }

    public static JDBCConnection loadDatabase(Properties property) {
        try {
            JDBCConnection pool = new JDBCConnection();
            pool.initialize(property);

            String  databaseTypeStr = pool.getDataBaseType().name();
            String databaseName = pool.getDataBaseName();
            String k = databaseTypeStr+"@"+databaseName;

            JDBCConnection _pool = databasePoolMap.get(k);
            if (_pool!=null) {
                pool.closeSessionAll();
                throw  new JDBCException("相同KEY("+k+")已存在数据库连接池: "+ _pool);
            }
            databasePoolMap.put(k,pool);

            return pool;
        } catch (Exception e) {
            throw new JDBCException("无法加载JDBC配置文件: "+ e);
        }
    }

    /* 获取数据库池对象 */
    private static JDBCConnection getDataBasePool(DataBaseType dataBaseType, String databaseName){
        String k = dataBaseType.name() +"@"+databaseName;
        JDBCConnection pool =  databasePoolMap.get(k);
        if (pool == null) throw new JDBCException("【异常】找不到指定的数据库 '"+k+"'");
        return pool;
    }

    /* 检测连接是否有效 */
    private static JDBCSessionFacade checkDBConnection(JDBCSessionFacade facade) {
        if (facade.checkDBConnectionValid()) return facade;
        //此连接池无效
        logger.debug("【异常】数据库连接不可用 " + facade.getManager());
        return null;
    }

    public static JDBCSessionFacade getFacade(DataBaseType dataBaseType, String databaseName) {
        JDBCConnection pool = getDataBasePool(dataBaseType,databaseName);
        return checkDBConnection( new JDBCSessionFacade(pool) );
    }

    public static JDBCSessionFacade getFacadeFirst() {
        for (String k : databasePoolMap.keySet()) {
            JDBCConnection pool = databasePoolMap.get(k);
            return checkDBConnection( new JDBCSessionFacade(pool) );
        }
      return null;
    }

}
