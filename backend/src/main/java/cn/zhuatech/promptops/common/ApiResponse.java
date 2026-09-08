/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.promptops.common;
import java.time.Instant;
public record ApiResponse<T>(boolean success,String message,T data,Instant timestamp){
 public static <T> ApiResponse<T> ok(String message,T data){return new ApiResponse<>(true,message,data,Instant.now());}
}
