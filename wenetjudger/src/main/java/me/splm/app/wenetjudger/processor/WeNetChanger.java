package me.splm.app.wenetjudger.processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import me.splm.app.wenetjudger.helper.NetType;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface WeNetChanger {
    boolean value() default false;
}
