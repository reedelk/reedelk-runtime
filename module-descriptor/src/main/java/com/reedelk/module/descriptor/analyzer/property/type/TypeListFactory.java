package com.reedelk.module.descriptor.analyzer.property.type;

import com.reedelk.module.descriptor.analyzer.component.ComponentAnalyzerContext;
import com.reedelk.module.descriptor.model.TypeDescriptor;
import com.reedelk.module.descriptor.model.TypeListDescriptor;
import com.reedelk.runtime.api.annotation.TabGroup;
import com.reedelk.runtime.api.commons.PlatformTypes;
import io.github.classgraph.ClassRefTypeSignature;
import io.github.classgraph.FieldInfo;
import io.github.classgraph.TypeArgument;

import java.util.List;

import static com.reedelk.module.descriptor.analyzer.commons.ScannerUtils.*;

public class TypeListFactory implements TypeDescriptorFactory {

    @Override
    public boolean test(String fullyQualifiedClassName, FieldInfo fieldInfo, ComponentAnalyzerContext context) {
        return clazzByFullyQualifiedName(fullyQualifiedClassName)
                .map(clazz -> PlatformTypes.isSupported(fullyQualifiedClassName) &&
                        isList(clazz)).orElse(false);
    }

    @Override
    public TypeDescriptor create(String fullyQualifiedClassName, FieldInfo fieldInfo, ComponentAnalyzerContext context) {
        String tabGroup = annotationValueOrDefaultFrom(fieldInfo, TabGroup.class, null);

        // We must find out the value type of the List.
        // The Value type could be a primitive type or a custom object type.

        ClassRefTypeSignature classRefTypeSignature = (ClassRefTypeSignature) fieldInfo.getTypeSignature();
        List<TypeArgument> typeArguments = classRefTypeSignature.getTypeArguments();

        TypeArgument typeArgument = typeArguments.get(0);
        String valueTypeFullyQualifiedName = typeArgument.toString();
        TypeDescriptorFactory factory = TypeDescriptorFactoryProvider.from(valueTypeFullyQualifiedName, fieldInfo, context);
        TypeDescriptor valueType = factory.create(valueTypeFullyQualifiedName, fieldInfo, context);

        TypeListDescriptor descriptor = new TypeListDescriptor();
        descriptor.setValueType(valueType);
        descriptor.setTabGroup(tabGroup);
        return descriptor;
    }
}