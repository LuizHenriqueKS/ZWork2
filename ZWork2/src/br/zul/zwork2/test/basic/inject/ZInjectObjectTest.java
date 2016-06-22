/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.zul.zwork2.test.basic.inject;

import java.util.Random;

/**
 *
 * @author Luiz Henrique
 */
public class ZInjectObjectTest {
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================

    private final int randNumber;

    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZInjectObjectTest() {
        this.randNumber = new Random().nextInt();
    }

    //==========================================================================
    //MÉTODO SOBRESCRITO
    //==========================================================================
    @Override
    public String toString() {
        return "TestObject[" + randNumber + "]";
    }
}
