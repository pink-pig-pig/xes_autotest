package tal.xes.autotest.app.enums;

public enum HttpCode {
    SUCCESS_CODE(0, "数据请求成功"),
    FAIL_CODE(-1, "数据请求失败");
    private Integer code;
    private String message;

    HttpCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }



}
