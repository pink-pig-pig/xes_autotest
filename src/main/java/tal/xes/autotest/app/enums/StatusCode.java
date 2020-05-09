package tal.xes.autotest.app.enums;

import java.util.HashMap;
import java.util.Map;
/**
 * @author zyz
 * @date 2020.05.09
 * @descirption 常见枚举类
 *
 * */
//@Slf4j
public enum StatusCode {
    FAILED("400","失败"),
    SUCCESS("200","成功");


    private String code;
    private String msg;

    private static Map<String,StatusCode> valueMap = new HashMap<>();

    static {
        for (StatusCode e : StatusCode.values()) {
            valueMap.put(e.getCode(),e);
        }
    }

    StatusCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
//
    public String getCode() {
        return code;
    }

    public static StatusCode getEnum(String code) {
        if (valueMap.containsKey(code)) {
            return valueMap.get(code);
        }
        return null;
    }
}
