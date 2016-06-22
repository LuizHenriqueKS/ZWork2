package br.zul.zwork2.util;

import br.zul.zwork2.inject.ZInjectInterface;
import br.zul.zwork2.inject.ZInjectManager;
import java.lang.reflect.Field;

/**
 *
 * @author Luiz Henrique
 */
public class ZObject implements ZInjectInterface<ZObject>{

    public ZObject(){
        ZInjectManager.injectIn(this);
    }

    @Override
    public void setValueField(ZObject object, Field objectField, Object fieldValue) throws IllegalArgumentException, IllegalAccessException {
        objectField.set(object, fieldValue);
    }
    
    
}
