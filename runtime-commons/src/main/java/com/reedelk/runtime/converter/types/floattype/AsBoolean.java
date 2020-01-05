package com.reedelk.runtime.converter.types.floattype;

import com.reedelk.runtime.converter.types.ValueConverter;

class AsBoolean implements ValueConverter<Float,Boolean> {

    @Override
    public Boolean from(Float value) {
        return value == 1f;
    }
}
