package com.eelve.limiting.resilience4j.exception;

/**
 * @author zhaozhilue
 */
public class ProgramException extends  BaseException{
    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public ProgramException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ProgramException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public ProgramException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public ProgramException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }
}
