package br.zul.zwork2.converter;

import java.util.Collection;

/**
 *
 * @author Luiz Henrique
 */
public interface ZConverterLoader {
    
    public Collection<ZConverter> loadConverters(Class<?> type1,Class<?> type2);
    
}
