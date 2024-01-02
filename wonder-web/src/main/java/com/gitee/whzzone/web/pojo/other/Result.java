package com.gitee.whzzone.web.pojo.other;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author Create by whz at 2023/7/6
 */
@ApiModel("响应对象")
public class Result<T> implements Serializable {

    public static final Integer SUCCESS = 200; // 请求成功
    public static final Integer ERROR = 500; // 请求异常
    public static final Integer UNAUTHORIZED = 401; // 未认证
    public static final Integer NO_DADA = 405; // 无数据

    @ApiModelProperty("响应状态码")
    private Integer code;

    @ApiModelProperty("提示信息")
    private String msg;

    @ApiModelProperty("响应数据")
    private T data;

    public Result() {}

    public Result(Integer code, String msg, T data) {
        this.msg = msg;
        this.data = data;
        this.code = code;
    }

    public static <T> Result<T> ok() {
        return new Result<>(SUCCESS, "操作成功", null);
    }

    public static <T> Result<T> ok(String msg) {
        return new Result<>(SUCCESS, msg, null);
    }

    public static <T> Result<T> ok(String msg, T data) {
        return new Result<>(SUCCESS, msg, data);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(SUCCESS, "操作成功", data);
    }

    public static <T> Result<T> error(String msg) {

        return new Result<>(ERROR, msg, null);
    }

    public static <T> Result<T> error(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }

    public static <T> Result<T> error(String msg, T obj) {
        return new Result<>(ERROR, msg, obj);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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
}
