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
        Map<String, Function> functionsMap = Map.of(
                "escapeJson", new EscapeJsonFunction(),
                "unescapeJson", new UnescapeJsonFunction()
        );

        TemplatrSpecBuilder templatrSpecBuilder = new TemplatrSpecBuilder(OpMode.OVERWRITR, functionsMap);
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
