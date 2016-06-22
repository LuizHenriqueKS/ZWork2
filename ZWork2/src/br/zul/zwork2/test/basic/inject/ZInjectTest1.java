/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.zul.zwork2.test.basic.inject;

import br.zul.zwork2.annotation.ZInject;
import br.zul.zwork2.inject.ZInjectInterface;
import br.zul.zwork2.inject.ZInjectManager;
import br.zul.zwork2.test.ZSimpleTest;
import java.lang.reflect.Field;

/**
 *
 * @author Luiz Henrique
 */
public class ZInjectTest1 extends ZSimpleTest<ZInjectObjectTest> implements ZInjectInterface<ZInjectTest1>{
    
    //==========================================================================
    //INJEÇÕES
    //==========================================================================
    @ZInject
    protected ZInjectObjectTest injected1;
    
    @ZInject
    protected ZInjectObjectTest injected2;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZInjectTest1(){
        init();
    }

    //==========================================================================
    //MÉTODOS DE CONSTRUÇÃO
    //==========================================================================
    private void init(){
        ZInjectManager.injectIn(this);
    }
    
    //==========================================================================
    //MÉTODOS SOBRESCRITOS
    //==========================================================================
    @Override
    public String convertResultToString(ZInjectObjectTest result) {
        return result.toString();
    }

    @Override
    public ZInjectObjectTest getResult() {
        return injected1;
    }

    @Override
    public ZInjectObjectTest getExpectedResult() {
        return injected2;
    }

    @Override
    public String getTestName() {
        return "Injeções";
    }

    @Override
    public void setValueField(ZInjectTest1 object, Field objectField, Object fieldValue) throws IllegalArgumentException, IllegalAccessException {
         objectField.set(object, fieldValue);
    }
    
}
