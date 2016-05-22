package br.zul.zwork2.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Luiz Henrique
 * @param <K> Classe da chave
 * @param <V> Classe do valor
 */
public abstract class ZFilter<K,V> {
    
    public abstract boolean filter(K key,V value);
    
    public List<V> filter(Collection<V> list){
        List<V> result = new ArrayList<>();
        Integer key = 0;
        for (V v:list){
            if (filter((K)key,v)){
                result.add(v);
            }
            key++;
        }
        return result;
    }
    
    public List<V> filter(V[] array){
        return filter(Arrays.asList(array));
    }
    
    public List<V> filter(Map<K,V> map){
        List<V> result = new ArrayList<>();
        for (K key:map.keySet()){
            V value = map.get(key);
            if (filter(key,value)){
                result.add(value);
            }
        }
        return result;
    }
    
}
