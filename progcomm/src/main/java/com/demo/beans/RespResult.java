package com.demo.beans;

public class RespResult {
    interface CODE{
      int ERROR = -3;
      int INTERCEPT = -2;
      int FAIL = -1;
      int SUCCESS = 0;
   }

   private int code = CODE.FAIL;

   private String message;

   private String text;

   private Object data;

   private String error;

   // 分页信息
   private Object pageInfo;

   // 拓展对象
   private Object expand;

   //是否成功
   public boolean isSuccess(){
      return code == CODE.SUCCESS;
   }

   //是否拦截
   public boolean isIntercept(){
      return code == CODE.INTERCEPT;
   }

   public RespResult success(String message,Object data){
      this.code = CODE.SUCCESS;
      this.message = message;
      this.data = data;
      return this;
   }

   public RespResult success(Object data){
      this.code = CODE.SUCCESS;
      this.data = data;
      return this;
   }

   public RespResult fail(String message){
      return fail(message,null);
   }

   public RespResult fail(String message,Object data){
      this.code = CODE.FAIL;
      this.message = message;
      this.data = data;
      return this;
   }



   public RespResult setMessage(String message){
      this.message = message;
      return this;
   }

   public RespResult setData(Object data){
      this.data = data;
      return this;
   }

   public RespResult setExpand(Object expand){
      this.expand = expand;
      return this;
   }

   //拦截
   public RespResult intercept(String cause){
      this.code = CODE.INTERCEPT;
      this.message = cause;
      return this;
   }

   //拦截
   public RespResult intercept(int code , String cause){
      this.code = code;
      this.message = cause;
      return this;
   }

   //错误
   public RespResult error(String msg,String error) {
      this.code = CODE.ERROR;
      this.message = msg;
      this.error = error;
      return this;
   }

   /* 设置查询后的分页信息 */
   public RespResult setPageInfo(Object page) {
     this.pageInfo = page;
      return this;
   }

   public Object getData() {
      return data;
   }

   public RespResult setText(Object text) {
      this.code = CODE.SUCCESS;
      this.text = String.valueOf(text);
      return this;
   }

   public int getCode() {
      return code;
   }

   public RespResult setCode(int code) {
      this.code = code;
      return this;
   }

   public String getMessage() {
      return message;
   }

   public String getError() {
      return error;
   }

   public RespResult setError(String error) {
      this.error = error;
      return this;
   }

   public Object getPageInfo() {
      return pageInfo;
   }

   public Object getExpand() {
      return expand;
   }

}
