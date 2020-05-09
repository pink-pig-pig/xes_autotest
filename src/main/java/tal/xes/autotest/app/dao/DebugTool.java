package tal.xes.autotest.app.dao;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface DebugTool  {

    public List<Object> searchBySource(@NotNull String source);

    public Object searchBySourceAndNums(@NotNull String source, @NotNull Integer num);

//    public Object search();

    public void insert();

    public void delete();


}
