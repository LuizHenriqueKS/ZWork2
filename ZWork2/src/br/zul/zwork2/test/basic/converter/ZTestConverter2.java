package br.zul.zwork2.test.basic.converter;

import br.zul.zwork2.converter.ZConversionObject;
import br.zul.zwork2.converter.ZConverter;

/**
 *
 * @author Luiz Henrique
 */
public class ZTestConverter2 implements ZConverter<Float,String> {

    @Override
    public Class<Float> getType1Class() {
        return Float.class;
    }

    @Override
    public Class<String> getType2Class() {
        return String.class;
    }

    @Override
    public String type1ToType2(ZConversionObject conversionObject,Float type1) {
        return String.valueOf(type1);
    }

    @Override
    public Float type2ToType1(ZConversionObject conversionObject,String type2) {
        return Float.parseFloat(type2);
    }
    
}
