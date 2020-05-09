package tal.xes.autotest.app.dto;
import lombok.Data;

@Data
public class RequestDTO {
    /**
     * 主键ID
     * */
    private Long id;
    /**
     * 姓名
     * */
    private String name;
    /**
     * 电话好吗
     * */
    private String number;
    /**
     * 白名单状态，false关闭，true开启
     * */
    private Boolean status;
    /**
     * 页码
     * */
    private Integer page;
    /**
     * 个数
     * */
    private Integer size;

}
