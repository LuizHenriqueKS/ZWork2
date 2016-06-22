/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.zul.zwork2.test.basic.converter;

import br.zul.zwork2.converter.ZConverter;
import br.zul.zwork2.test.ZSimpleTest;

/**
 *
 * @author Luiz Henrique
 */
public class ZConverterTest extends ZSimpleTest<Float> {

    @Override
    public String convertResultToString(Float result) {
        return result.toString();
    }

    @Override
    public Float getResult() {
        return (Float) new ZTestConverterManager().convert("12", Float.class);
    }

    @Override
    public Float getExpectedResult() {
        return 12f;
    }

    @Override
    public String getTestName() {
        return "Se ZConverterManager está convertendo certo";
    }
    
}
