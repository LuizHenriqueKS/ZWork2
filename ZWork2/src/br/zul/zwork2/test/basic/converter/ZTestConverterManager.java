/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.zul.zwork2.test.basic.converter;

import br.zul.zwork2.converter.ZConverter;
import br.zul.zwork2.converter.ZConverterManagerAbstract;
import br.zul.zwork2.reflection.ZClass;
import br.zul.zwork2.reflection.ZPackage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Luiz Henrique
 */
public class ZTestConverterManager extends ZConverterManagerAbstract{

    @Override
    public Collection<ZConverter> loadConverters(Class<?> type1, Class<?> type2) {
        //OBTEM O PACOTE DESSA CLASSE
        ZPackage pack = new ZPackage(getClass(), getClass().getPackage());
        //LISTA TODAS AS CLASSES DE CONVERSOR DESSE PACOTE
        List<ZClass> converterClassList = pack.listClasses(true, ZConverter.class, true);
        //PREPARA A LISTA DE CONVERSORES
        List<ZConverter> converterList = new ArrayList<>();
        //ALIMENTA A LISTA DE CONVERSORES
        for (ZClass zClass:converterClassList){
            converterList.add((ZConverter)zClass.newInstance());
        }
        //RETORNA A LISTA DE CONVERSORES
        return converterList;
    }
    
}
