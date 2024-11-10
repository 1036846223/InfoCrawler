package zero.info.enu;

public enum ContentTypeEnum {
    UN_KNOWN(0, "未知"),
    AI(1, "ai行业"),
    WEB3(2, "web3行业"),
    ;

    private Integer code;
    private String desc;

    ContentTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ContentTypeEnum convertWebSiteParseTypeEnum(WebSiteParseTypeEnum parseTypeEnum) {
        if (parseTypeEnum == WebSiteParseTypeEnum.AI_BASE_COM) {
            return ContentTypeEnum.AI;
        }
        return ContentTypeEnum.UN_KNOWN;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
