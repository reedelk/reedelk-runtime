package com.reedelk.module.descriptor.analyzer.property;

import com.reedelk.module.descriptor.analyzer.commons.ScannerUtils;
import com.reedelk.module.descriptor.analyzer.component.ComponentAnalyzerContext;
import com.reedelk.module.descriptor.model.ComponentPropertyDescriptor;
import com.reedelk.runtime.api.annotation.DefaultValue;
import io.github.classgraph.FieldInfo;

public class PropertyDefaultAnalyzer implements FieldInfoAnalyzer {

    @Override
    public void handle(FieldInfo fieldInfo, ComponentPropertyDescriptor.Builder builder, ComponentAnalyzerContext context) {
        String defaultValue = ScannerUtils.annotationValueOrDefaultFrom(fieldInfo, DefaultValue.class, null);
        builder.defaultValue(defaultValue);
    }
}
