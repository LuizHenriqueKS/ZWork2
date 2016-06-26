package br.zul.zwork2.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Luiz Henrique
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ZAttribute {
    Class join() default void.class;
    String format() default "";
    String type() default "";
    boolean nullable() default false;
    boolean key() default true;
    boolean unique() default false;
    int length() default 0;
}
