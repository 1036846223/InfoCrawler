package zero.info.enu;


public enum InfoSearchOperateTypeEnum {
    UNKNOWN(-1, "unknown", "未知"),
    SEARCH(0, "search", "仅搜索并返回"),
    SEARCH_SAVE(1, "searchAndSave", "搜索返回并保存至txt文件"),
    ;

    private Integer code;
    //装填英文-后续做映射
    private String type;
    private String des;

    InfoSearchOperateTypeEnum(Integer code, String type, String des) {
        this.code = code;
        this.type = type;
        this.des = des;
    }

    public Integer getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public String getDes() {
        return des;
    }

    public static InfoSearchOperateTypeEnum fromCode(int code) {
        for (InfoSearchOperateTypeEnum value : InfoSearchOperateTypeEnum.values()) {
            if (value.code == code) {
                return value;
            }
        }
        return InfoSearchOperateTypeEnum.UNKNOWN;
    }
}
