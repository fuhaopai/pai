package com.pai.base.api.response;

import java.io.Serializable;

import com.pai.base.api.constants.ResponseCode;
import com.pai.base.api.doc.annotation.AutoDocField;

/**
 * 响应结果
 *
 * @param <T>
 */
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @AutoDocField("返回编码，100-成功")
    private String code;

    @AutoDocField("返回结果消息")
    private String msg;

    @AutoDocField("返回结果")
    private T data;

    public boolean isSuccess() {
    	return ResponseCode.OK.getCode().equals(getCode());
    }
    
    public BaseResponse() {
    }

    public BaseResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResponse(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 构建成功响应
     *
     * @param data 返回数据对象
     * @return 构建成功响应
     */
    public static <T> BaseResponse<T> buildSuccess(T data) {

        return new BaseResponse<>(ResponseCode.OK.getCode(), ResponseCode.OK.getDesc(), data);
    }
    
    public static <T> BaseResponse<T> buildSuccess() {
    	
    	return new BaseResponse<>(ResponseCode.OK.getCode(), ResponseCode.OK.getDesc());
    }
    
    public static <T> BaseResponse<T> buildFailure(String code, String msg) {
    	
    	return new BaseResponse<>(code, msg);
    }
    
    public static <T> BaseResponse<T> buildFailure(String code, String msg, T data) {
    	
    	return new BaseResponse<>(code, msg, data);
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
