package com.demo.beans;


import com.bottle.util.GoogleGsonUtil;

public class RespResult {

    interface CODE{
      int ERROR = -3;
      int INTERCEPT = -2;
      int FAIL = -1;
      int SUCCESS = 0;
   }

   private int code = CODE.FAIL;

   private String message;

   private Object data;

   // 分页信息
   private Object pageInfo;


   public RespResult success(String message,Object data){
      this.code = CODE.SUCCESS;
      this.message = message;
      this.data = data;
      return this;
   }

   public RespResult success(String message){
      return success(message,null);
   }

   public RespResult success(Object data){
      return success(null,data);
   }


   public RespResult fail(String message,Object data){
      this.code = CODE.FAIL;
      this.message = message;
      this.data = data;
      return this;
   }

   public RespResult fail(String message){
      return fail(message,null);
   }

   //拦截
   public RespResult intercept(String cause){
      this.code = CODE.INTERCEPT;
      this.message = cause;
      return this;
   }

   // 错误
   public RespResult error(String error) {
      this.code = CODE.ERROR;
      this.message = error;
      return this;
   }


   /* 设置查询后的分页信息 */
   public RespResult pageInfo(Object pageInfo) {
     this.pageInfo = pageInfo;
     return this;
   }


   public int getCode() {
      return code;
   }

   public Object getData() {
      return data;
   }

   public String getMessage() {
      return message;
   }

   public Object getPageInfo() {
      return pageInfo;
   }

   @Override
   public String toString() {
      return GoogleGsonUtil.javaBeanToJson(this);
   }
}
