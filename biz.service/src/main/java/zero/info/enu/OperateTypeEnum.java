package zero.info.enu;

public enum OperateTypeEnum {
    ADD(1, "新增"),
    UPDATE(2, "修改"),
    DEL(3, "删除"),
    ;

    private Integer code;
    private String desc;

    OperateTypeEnum(Integer code, String desc) {
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
