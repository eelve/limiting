package com.eelve.limiting.guava.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.http.HttpStatus;


import java.io.Serializable;

/**
 * 定义Json响应数据
 *
 * @param <T>
 */
@Data
public class JsonResult<T> implements Serializable {
    private Integer code;
    private String msg;
    private T data;

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

    /**
     * 成功
     *
     * @return
     */
    public static JsonResult ok() {
        JsonResult result = new JsonResult();
        result.setCode(0);
        return result;
    }

    /**
     * 成功
     *
     * @return
     */
    public static JsonResult ok(String msg) {
        JsonResult result = new JsonResult();
        result.setCode(0);
        result.setMsg(msg);
        return result;
    }

    /**
     * 失败
     *
     * @param msg
     * @return
     */
    public static JsonResult error(String msg) {
        JsonResult result = new JsonResult();
        result.setCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        result.setMsg(msg);
        return result;
    }

    /**
     * 失败
     *
     * @param code
     * @param msg
     * @return
     */
    public static JsonResult error(Integer code, String msg) {
        JsonResult result = new JsonResult();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    /**
     * 失败
     *
     * @return
     */
    public static JsonResult error() {
        JsonResult result = new JsonResult();
        result.setCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        result.setMsg("熔断错误");
        return result;
    }

    /**
     * 添加返回的数据
     *
     * @param data
     * @return
     */
    public JsonResult<T> put(T data) {
        this.data = data;
        return this;
    }

    /**
     * 是否正常
     *
     * @return
     */
    @JsonIgnore
    public boolean isOk() {
        return this.code.intValue() == 0;
    }
    @JsonIgnore
    public boolean isError() {
        return this.code.intValue() != 0;
    }
}
