package com.bottle.jdbc;

import com.bottle.jdbc.define.RowName;
import java.lang.reflect.*;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: leeping
 * @Date: 2019/8/17 15:54
 */
public class JDBCUtil {

    private JDBCUtil(){}

    public static String param2String(Object[] param){
        if (param == null || param.length == 0) return "";
        return Arrays.toString(param);
    }

    public static String param2String(List<Object[]> params){
        StringBuilder sb = new StringBuilder();
        for (Object[] arr : params){
            sb.append(param2String(arr)).append(",");
        }
        if (!sb.isEmpty()) sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    public static void filterParam(Object[] params) {
        if (params == null) return;
        for (int i = 0 ; i<params.length ; i++){
            if (params[i] instanceof  String){
                params[i] = params[i].toString().trim();
            }
        }
    }

    public static void filterParam(List<Object[]> paramsList){
        if (paramsList == null) return;
        for (Object[] params : paramsList){
            filterParam(params);
        }
    }

    /* 创建类 */
    public static <T> T createObject(Class<T> beanClass) throws Exception {
        if (beanClass.getName().contains("$")){
            return getObjectFromInnerClass(beanClass);
        }
        return beanClass.newInstance();
    }

    /* 创建内部类 */
    @SuppressWarnings("unchecked")
    private static  <T> T getObjectFromInnerClass(Class<T> beanClass) throws Exception {
        Constructor<?>[] carr = beanClass.getDeclaredConstructors();

        for (int i=carr.length-1; i>=0 ; i--){
            try {
                carr[i].setAccessible(true);
                return (T) carr[i].newInstance();
            } catch (Exception ignored) { }
        }
        throw new IllegalAccessException("Class '"+beanClass+"' newInstance fail.");
    }

    /* 赋值 */
    public static <T> void classAssignment(Class<?> clazz, T bean, ResultSet rs) {
        Field[] fields = clazz.getDeclaredFields();
        //遍历属性
        for(Field field : fields){
            try {
                String name = field.getName();//获取属性的名字
                RowName rowName = field.getAnnotation(RowName.class);
                if (rowName!=null){
                    name = rowName.value();
                }
                if (name == null || name.isEmpty()) continue;
//                            ("属性名: "+ name);
                Type type = field.getGenericType();
                String typeName = type.getTypeName();

                if (typeName.lastIndexOf(".")>0){
                    typeName = typeName.substring(typeName.lastIndexOf(".")+1);
                }
                typeName = "get" + typeName.substring(0, 1).toUpperCase() + typeName.substring(1);
//                            ("取值方法: "+ typeName);
                Method method = rs.getClass().getMethod(typeName,String.class);//得到方法对象
                Object value = method.invoke(rs,name);
//                            ("属性值: "+ value);
                if (value == null) continue;
                field.setAccessible(true);
                field.set(bean, value);
            } catch (Exception e) {
//                            ("赋值错误: " + e);
            }
        }
    }


    @SuppressWarnings("unchecked")
    public static <T extends PreparedStatement> T prepareStatement(Connection conn, String sql,boolean isCallable) throws SQLException {
        return (T) (!isCallable ? conn.prepareStatement(sql) : conn.prepareCall(sql));
    }

    public static void setParameters(PreparedStatement pst, Object... params) throws SQLException {
        setInputParameters(pst,1,params);
    }

    public static  void setInputParameters(PreparedStatement pst, int startPos, Object... params) throws SQLException {
        if (params!=null){
            for(int i = 0; i < params.length; ++i) {
                Object arg = params[i];
                if (arg == null) {
                    pst.setNull(i + startPos, Types.NULL);
                } else {
                    pst.setObject(i + startPos, arg);
                }
            }
        }
    }

    /* 注册输出参数 */
    public static void registerOutputParameters(CallableStatement cst, int startPos, int[] types) throws SQLException {
        for(int i = 0; i < types.length; ++i) {
            int t = types[i];
            cst.registerOutParameter(i + startPos, t);
        }
    }

    public static String completeSQL(String sql , Object[] params)   {
        if (params!=null){
            for (Object value : params) {
                sql = sql.replaceFirst("\\?", String.valueOf(value));
            }
        }
        return sql;
    }

}
