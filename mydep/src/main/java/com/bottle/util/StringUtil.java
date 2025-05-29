package com.bottle.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class StringUtil {

    /* 错误输出 */
    public static String printExceptInfo(Throwable ex){
        Writer writer = new StringWriter();
        try(PrintWriter printWriter = new PrintWriter(writer)){
            ex.printStackTrace(printWriter);
            Throwable cause = ex.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
        }
        return writer.toString();
    }



}
