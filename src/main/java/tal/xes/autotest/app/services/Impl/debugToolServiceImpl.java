package tal.xes.autotest.app.services.Impl;

import tal.xes.autotest.app.services.DebugToolService;

import javax.validation.constraints.NotNull;
import java.util.List;

public class debugToolServiceImpl implements DebugToolService {
    @Override
    public List<Object> searchBySource(@NotNull String source) {
        return null;
    }

    @Override
    public Object searchBySourceAndNums(@NotNull String source, @NotNull Integer num) {
        return null;
    }

    @Override
    public void insert() {

    }

    @Override
    public void delete() {

    }

    @Override
    public List<Object> searchByUrl(@NotNull String url) {
        return null;
    }
}
