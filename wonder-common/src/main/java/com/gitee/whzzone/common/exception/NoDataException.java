package com.gitee.whzzone.common.exception;

/**
 * @author Create by whz at 2023/8/3
 */
public class NoDataException extends RuntimeException
{

    public NoDataException(){
        super();
    }

    public NoDataException(String msg){
        super(msg);
    }
}
