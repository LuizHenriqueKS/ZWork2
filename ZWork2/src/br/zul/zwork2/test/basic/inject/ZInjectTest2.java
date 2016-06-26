/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.zul.zwork2.test.basic.inject;

import br.zul.zwork2.annotation.ZInject;
import br.zul.zwork2.annotation.ZSingleton;
import br.zul.zwork2.inject.ZInjectInterface;
import br.zul.zwork2.inject.ZInjectManager;
import br.zul.zwork2.test.ZSimpleTest;
import br.zul.zwork2.test.ZTestManager;
import java.lang.reflect.Field;

/**
 *
 * @author Luiz Henrique
 */
@ZSingleton
public class ZInjectTest2 extends ZSimpleTest<ZInjectObjectTest> implements ZInjectInterface{
    
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
    public ZInjectTest2(){
        init();
    }
    
    //==========================================================================
    //MÉTODOS DE CONSTRUÇÃO
    //==========================================================================
    private void init(){
        ZInjectManager.injectIn(this);
        injected2 = new ZInjectObjectTest();
        ZInjectManager.getInstance().setInstance(ZInjectObjectTest.class, injected2);
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
    public void setValueField(Object object, Field objectField, Object fieldValue) throws IllegalArgumentException, IllegalAccessException {
         objectField.set(object, fieldValue);
    }
    
}
