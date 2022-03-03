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

package ru.ilyshkafox.jolt;

import com.bazaarvoice.jolt.ContextualTransform;
import com.bazaarvoice.jolt.SpecDriven;
import com.bazaarvoice.jolt.common.Optional;
import com.bazaarvoice.jolt.common.tree.MatchedElement;
import com.bazaarvoice.jolt.common.tree.WalkedPath;
import com.bazaarvoice.jolt.modifier.OpMode;
import com.bazaarvoice.jolt.modifier.TemplatrSpecBuilder;
import com.bazaarvoice.jolt.modifier.function.Function;
import com.bazaarvoice.jolt.modifier.spec.ModifierCompositeSpec;
import ru.ilyshkafox.jolt.functions.EscapeJsonFunction;
import ru.ilyshkafox.jolt.functions.UnescapeJsonFunction;

import java.util.HashMap;
import java.util.Map;

public class Module implements SpecDriven, ContextualTransform {
    private final ModifierCompositeSpec rootSpec;

    public Module(Object spec) {
        Map<String, Function> functionMap = Map.of(
                "escapeJson", new EscapeJsonFunction(),
                "unescapeJson", new UnescapeJsonFunction()
        );

        TemplatrSpecBuilder templatrSpecBuilder = new TemplatrSpecBuilder(OpMode.OVERWRITR, functionMap);
        rootSpec = new ModifierCompositeSpec(ROOT_KEY, (Map<String, Object>) spec, OpMode.OVERWRITR,
                templatrSpecBuilder);
    }

    @Override
    public Object transform(Object input, Map<String, Object> context) {
        // Source code implementation copied here
        Map<String, Object> contextWrapper = new HashMap<>();
        contextWrapper.put(ROOT_KEY, context);

        MatchedElement rootLpe = new MatchedElement(ROOT_KEY);
        WalkedPath walkedPath = new WalkedPath();
        walkedPath.add(input, rootLpe);

        rootSpec.apply(ROOT_KEY, Optional.of(input), walkedPath, null, contextWrapper);
        return input;
    }
}
