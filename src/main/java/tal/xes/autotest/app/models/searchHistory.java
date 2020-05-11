package tal.xes.autotest.app.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author zyz
 * @date 2020.05.09
 * @descirption 来源表，存储每个来源对应的表名以及对应的样式格式
 *
 * */
@Entity
@Table
public class searchHistory {
    /**
     * ID
     * */
    @Id
    @GeneratedValue
    private Long ID;
    /**
     * 图片的url，oss或者本地
     * */
    private String url;
    /**
     *  题目的来源
     * */
    private String source;
    /**
     *  题目ID列表，按照顺序存储
     * */
    private List<String> questionIds;
    /**
     *  内容：题库，答案以及解析
     * */
    private String contens;
    /**
     * 本次查询出现次数最多的来源
     * */
    private String maxOfSource;
    /**
     * 本次查询没有出现的题目来源
     * */
    private Integer nullOfSource;
    /**
     * 标注按钮，这道题目是否应该注意，debugFlag
     * */
    private Boolean isDebug;

//    /**
//     * 标记的题目，list，可为空
//     * */
//    private List<String> debugContent;
    /**
     * 创建时间
     * */
    private Date createtime;
    /**
     * 更新时间
     * */
    private Date updatetime;
}
