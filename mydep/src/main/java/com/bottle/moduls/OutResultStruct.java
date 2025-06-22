package com.bottle.moduls;


import com.bottle.util.GsonUtil;

import java.lang.reflect.Type;

import static com.bottle.util.GsonUtil.jsonToJavaBean;


public final class OutResultStruct {

    interface CODE{
      int ERROR = -3;
      int INTERCEPT = -2;
      int FAIL = -1;
      int SUCCESS = 0;
   }

   /** 状态码 */
   private int code = CODE.FAIL;
   /** 提示信息 */
   private String message;
   /** 数据对象 */
   private Object data;
   /** 分页信息 默认关联
    * @see com.bottle.jdbc.define.Page
    * */
   private Object pageInfo;

   /* 成功 */
   public OutResultStruct success(String message, Object data){
      this.code = CODE.SUCCESS;
      this.message = message;
      this.data = data;
      return this;
   }
   /* 成功 */
   public OutResultStruct success(String message){
      return success(message,null);
   }
   /* 成功 */
   public OutResultStruct success(Object data){
      return success(null,data);
   }

   /* 失败 */
   public OutResultStruct fail(String message, Object data){
      this.code = CODE.FAIL;
      this.message = message;
      this.data = data;
      return this;
   }
   /* 失败 */
   public OutResultStruct fail(String message){
      return fail(message,null);
   }
   /* 失败 */
   public OutResultStruct fail(Object data){
      return fail(null,data);
   }

   /* 拦截 */
   public OutResultStruct intercept(String cause){
      this.code = CODE.INTERCEPT;
      this.message = cause;
      return this;
   }

   /* 错误 */
   public OutResultStruct error(String error) {
      this.code = CODE.ERROR;
      this.message = error;
      return this;
   }

   /* 设置查询后的分页信息 */
   public OutResultStruct pageInfo(Object pageInfo) {
     this.pageInfo = pageInfo;
     return this;
   }

    @Override
   public String toString() {
      return GsonUtil.javaBeanToJson(this);
   }

   public <T> T getData(Class<T> cls) {
      String json = GsonUtil.javaBeanToJson(data);
      if (json == null) return null;
      return GsonUtil.jsonToJavaBean(json,cls);
   }

   public <T> T getData(Type type) {
      String json = GsonUtil.javaBeanToJson(data);
      if (json == null) return null;
      return GsonUtil.jsonToJavaBean(json,type);
   }

}
