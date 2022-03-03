/*
 * Copyright 2022 Moslov Ilya <ilyshkafox@mail.ru>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
