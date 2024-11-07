package zero.info.enu;

public enum StatusEnum {
    VALID(1, "有效"),
    UN_VALID(0, "无效");

    private Integer code;
    private String desc;

    StatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
