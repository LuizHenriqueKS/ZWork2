/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.zul.zwork2.converter;

/**
 *
 * @author Luiz Henrique
 * @param <Type1>
 * @param <Type2>
 */
public interface ZConverter<Type1,Type2> {
    
    public Class<Type1> getType1Class();
    public Class<Type2> getType2Class();
    public Type2 type1ToType2(Type1 type1);
    public Type1 type2ToType1(Type2 type2);
    
}
