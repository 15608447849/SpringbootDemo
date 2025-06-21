package com.bottle.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Properties;

public final class ObjectUtil {
    private static final Logger logger = LoggerFactory.getLogger(ObjectUtil.class);

    /**
     * 转换基本类型为包装类型
     */
    private static Class<?> baseTypeConvertWrapType(Class<?> clazz) {
        if (clazz == byte.class) {
            return Byte.class;
        }
        if (clazz == short.class) {
            return Short.class;
        }
        if (clazz == int.class) {
            return Integer.class;
        }
        if (clazz == long.class) {
            return Long.class;
        }
        if (clazz == boolean.class) {
            return Boolean.class;
        }
        if (clazz == float.class) {
            return Float.class;
        }
        if (clazz == double.class) {
            return Double.class;
        }
        if (clazz == char.class) {
            return Character.class;
        }
        return clazz;
    }

    /**
     * 转换对象为执行类类型
     */
    public static Object convertStringToBaseType(Object val, Class<?> cls) {
        try {

            // 目标类型与指定类型一样
            if (val.getClass() == cls) return val;

            if (cls == String.class) return String.valueOf(val);

            if (cls == BigDecimal.class) return new BigDecimal(String.valueOf(val));

            if (cls == BigInteger.class) return new BigInteger(String.valueOf(val));

            String className = cls.getSimpleName();
            if (className.equals("Integer")) className = "int";
            className = className.substring(0, 1).toUpperCase() + className.substring(1);
            String methodName = "parse" + className;

            String v = String.valueOf(val);
            Method method = baseTypeConvertWrapType(cls).getMethod(methodName, String.class);
            return method.invoke(null, v);
        } catch (Exception e) {
            logger.error("类型转换错误,val=" + val + ",cls=" + cls, e);
        }
        return null;
    }



        /**
         * 将对象的属性复制到Properties中
         */
        public static Properties objectToProperties(Object o) {
            Properties props = new Properties();
            if (o == null) return props;
            // 获取所有公共方法
            Method[] methods = o.getClass().getMethods();
            for (Method method : methods) {
                // 检查是否是getter方法
                if ( method.getName().startsWith("get")
                        && method.getParameterCount() == 0
                        && !void.class.equals( method.getReturnType()) ) {
                    try {
                        // 调用getter方法获取值
                        Object value = method.invoke(o);
                        if (value != null) {
                            // 将方法名转换为属性名
                            String propName = method.getName().replaceFirst("get", "");
                            propName = Character.toLowerCase(propName.charAt(0)) + propName.substring(1);

                            // 将值放入Properties
                            props.setProperty(propName,String.valueOf(value));
                        }
                    } catch (Exception e) {
                        // 处理反射异常
                        logger.error("accessing property: " + method.getName(),e);
                    }
                }
            }
            return props;
        }


}
