package ru.ilyshkafox.jolt.functions;

import com.bazaarvoice.jolt.common.Optional;
import com.bazaarvoice.jolt.modifier.function.Function.BaseFunction;
import ru.ilyshkafox.jolt.utils.JsonUtil;

import java.util.List;

public class EscapeJsonFunction extends BaseFunction<String> {

    @Override
    protected Optional<Object> applyList(final List<Object> input) {
        return Optional.of(JsonUtil.escapeJson(input));
    }


    @Override
    protected Optional<String> applySingle(Object arg) {
        return Optional.of(JsonUtil.escapeJson(arg));
    }

}
