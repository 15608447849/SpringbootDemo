package com.bottle.jdbc;

public class JDBCException extends RuntimeException{


    public JDBCException(String desc) {
        super(desc);
    }

    public JDBCException(Throwable e) {
        super(e);
    }

    public JDBCException(String desc, Throwable e) {
        super(desc, e);
        JDBCRecode.error(desc,e);
    }


}
