package ru.ilyshkafox.jolt.functions;

import com.bazaarvoice.jolt.common.Optional;
import com.bazaarvoice.jolt.exception.SpecException;
import com.bazaarvoice.jolt.modifier.function.Function.BaseFunction;
import ru.ilyshkafox.jolt.utils.JsonUtil;

import java.util.List;

public class UnescapeJsonFunction extends BaseFunction<Object> {
    @Override
    protected Optional<Object> applyList(List<Object> input) {
        throw new SpecException("unescapeJson not support List. Expected: " + String.class.getName() + ".");
    }

    @Override
    protected Optional<Object> applySingle(Object arg) {
        if (!(arg instanceof String) && !(arg instanceof Number) && !(arg instanceof Boolean)) {
            throw new SpecException("unescapeJson not support " + arg.getClass().getName() + ". Expected: " + String.class.getName() + " json format.");
        }
        return Optional.of(JsonUtil.unescapeJson(String.valueOf(arg)));
    }
}
