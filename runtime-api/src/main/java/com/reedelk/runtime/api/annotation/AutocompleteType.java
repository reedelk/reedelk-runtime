package com.reedelk.runtime.api.annotation;

import com.reedelk.runtime.api.commons.StringUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface AutocompleteType {

    String USE_DEFAULT_TYPE = "###USE_DEFAULT_TYPE###";

    String value() default USE_DEFAULT_TYPE;

    // Identify a property Accessible from the Root suggestion
    // tree. e.g a type providing utilities or a builder.
    boolean global() default false;

    String description() default StringUtils.EMPTY;
}
