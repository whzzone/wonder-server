package com.example.securitytest.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel("响应对象")
@Data
public class Result<T> {
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
        Integer code = HttpCode.SUCCESS;
        return new Result<>(code, "操作成功", null);
    }

    public static <T> Result<T> ok(String msg) {
        Integer code = HttpCode.SUCCESS;
        return new Result<>(code, msg, null);
    }

    public static <T> Result<T> ok(String msg, T data) {
        Integer code = HttpCode.SUCCESS;
        return new Result<>(code, msg, data);
    }

    public static <T> Result<T> error(String msg) {
        Integer code = HttpCode.ERROR;

        return new Result<>(code, msg, null);
    }

    public static <T> Result<T> error(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }

    public static <T> Result<T> error(String msg, T obj) {
        Integer code = HttpCode.ERROR;

        return new Result<>(code, msg, obj);
    }
}
