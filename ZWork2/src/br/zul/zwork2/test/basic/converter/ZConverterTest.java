/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.zul.zwork2.test.basic.converter;

import br.zul.zwork2.annotation.ZInject;
import br.zul.zwork2.converter.ZConverterManager;
import br.zul.zwork2.inject.ZInjectInterface;
import br.zul.zwork2.inject.ZInjectManager;
import br.zul.zwork2.test.ZSimpleTest;
import java.lang.reflect.Field;

/**
 *
 * @author Luiz Henrique
 */
public class ZConverterTest extends ZSimpleTest<Float> implements ZInjectInterface {

    @ZInject
    ZConverterManager converterManager;
    
    public ZConverterTest(){
        init();
    }
    
    private void init(){
        ZInjectManager.injectIn(this);
    }
    
    @Override
    public String convertResultToString(Float result) {
        return result.toString();
    }

    @Override
    public Float getResult() {
        converterManager.addLoader(new ZTestConverterLoader());
        return (Float) converterManager.convert("12", Float.class);
    }

    @Override
    public Float getExpectedResult() {
        return 12f;
    }

    @Override
    public String getTestName() {
        return "Se ZConverterManager está convertendo certo";
    }

    @Override
    public void setValueField(Object object, Field objectField, Object fieldValue) throws IllegalArgumentException, IllegalAccessException {
        objectField.set(object, fieldValue);
    }
    
}
