package zero.info.response;

import lombok.Data;
import zero.info.enu.HttpResponseCodeEnum;

import java.io.Serializable;

@Data
public class HttpResponse<T> implements Serializable {

    private Integer code;
    private String msg;
    private T Data;
    private long total;

    public static <T> HttpResponse<T> success(T data) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setCode(HttpResponseCodeEnum.SUCCESS.getCode());
        httpResponse.setData(data);
        return httpResponse;
    }

    public static <T> HttpResponse<T> success() {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setCode(HttpResponseCodeEnum.SUCCESS.getCode());
        httpResponse.setData("success");
        return httpResponse;
    }

    public static <T> HttpResponse<T> paramError() {
        return error("paramError");
    }

    public static <T> HttpResponse<T> error(int code, String msg) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setCode(code);
        httpResponse.setMsg(msg);
        return httpResponse;
    }

    public static <T> HttpResponse<T> error(String msg) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setCode(HttpResponseCodeEnum.SERVER_ERROR.getCode());
        httpResponse.setMsg(msg);
        return httpResponse;
    }

    public static <T> HttpResponse<T> error(T data, int code, String msg) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setCode(code);
        httpResponse.setMsg(msg);
        httpResponse.setData(data);
        return httpResponse;
    }

    public static <T> HttpResponse<T> success(T data, String msg) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setCode(HttpResponseCodeEnum.SUCCESS.getCode());
        httpResponse.setMsg(msg);
        httpResponse.setData(data);
        return httpResponse;
    }

    public static <T> HttpResponse<T> success(T data, long total) {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setCode(HttpResponseCodeEnum.SUCCESS.getCode());
        httpResponse.setTotal(total);
        httpResponse.setData(data);
        return httpResponse;
    }


    public static <T> HttpResponse<T> systemError() {
        HttpResponse<T> httpResponse = new HttpResponse();
        httpResponse.setCode(HttpResponseCodeEnum.SERVER_ERROR.getCode());
        httpResponse.setMsg(HttpResponseCodeEnum.SERVER_ERROR.getMsg());
        return httpResponse;
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
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
