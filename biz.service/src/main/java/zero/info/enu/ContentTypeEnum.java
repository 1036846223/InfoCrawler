package zero.info.enu;

public enum ContentTypeEnum {
    AI(1, "ai行业"),
    WEB3(2, "web3行业"),
    ;

    private Integer code;
    private String desc;

    ContentTypeEnum(Integer code, String desc) {
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