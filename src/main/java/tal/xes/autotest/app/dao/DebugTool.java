package tal.xes.autotest.app.dao;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;
/**
 * @author zyz
 * @date 2020.05.10
 * @descirption debug工具所需接口类
 *
 * */
@Mapper
@Component("DebugTool")
public interface DebugTool  {
    /**
     * 查询所有这个来源的题目
     * @return Integer
     */
    public List<Object> searchBySource(@NotNull String source);
    /**
     * 查询所有这个来源的题目
     * @return Integer
     */
    public List<Object> searchByUrl(@NotNull String url);
    /**
     * 查询表中不超过5个数据
     * @return Object
     */
    public Object searchBySourceAndNums(@NotNull String source, Integer num);

//    public Object search();
    /**
     * 插入
     * @return Integer
     */
    public void insert();
    /**
     * 删除
     * @param
     * @return Integer
     */
    public void delete();


}
