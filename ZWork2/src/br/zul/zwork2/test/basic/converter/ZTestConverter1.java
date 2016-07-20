package br.zul.zwork2.test.basic.converter;

import br.zul.zwork2.converter.ZConversionObject;
import br.zul.zwork2.converter.ZConverter;

/**
 *
 * @author Luiz Henrique
 */
public class ZTestConverter1 implements ZConverter<Integer,String>{

    @Override
    public Class<Integer> getType1Class() {
        return Integer.class;
    }

    @Override
    public Class<String> getType2Class() {
        return String.class;
    }

    @Override
    public String type1ToType2(ZConversionObject conversionObject,Integer type1) {
        return String.valueOf(type1);
    }

    @Override
    public Integer type2ToType1(ZConversionObject conversionObject,String type2) {
        return Integer.parseInt(type2);
    }
    
}
