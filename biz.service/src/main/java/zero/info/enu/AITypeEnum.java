package zero.info.enu;

public enum AITypeEnum {
    ADMIN(1, "待定"),
    ;

    private Integer code;
    private String desc;

    AITypeEnum(Integer code, String desc) {
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
