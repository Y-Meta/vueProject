package com.plmm.webmvc.ennu;

/**
 * 操作类型枚举类
 */
public enum OptType {

    OPT_SAVE(3,"保存"),
    OPT_DELETE(4,"删除"),
    OPT_UPDATE(5,"更新"),
    OPT_DETAIL(6,"查看")
    ;
    private int code;
    private String type;

    OptType(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
