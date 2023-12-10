package zero.info.enu;

public enum HttpResponseCodeEnum {

    UNKNOWN(-1, "未知"),
    SUCCESS(0, "成功"),
    SERVER_ERROR(2, "服务异常"),
    PARAM_ERROR(3, "入参错误"),
    NO_LOGIN(4, "未登录"),
    ;

    private int code;
    private String msg;

    HttpResponseCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public static HttpResponseCodeEnum fromCode(int code) {
        for (HttpResponseCodeEnum value : HttpResponseCodeEnum.values()) {
            if (value.code == code) return value;
        }
        return HttpResponseCodeEnum.UNKNOWN;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
