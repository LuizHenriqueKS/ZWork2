package br.zul.zwork2.inject;

import java.lang.reflect.Field;

/**
 *
 * @author Luiz Henrique
 * @param <T>
 */
public interface ZInjectInterface<T> {

    void setValueField(T object,Field objectField,Object fieldValue) throws IllegalArgumentException,IllegalAccessException;
    
}
