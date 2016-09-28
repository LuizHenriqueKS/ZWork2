package br.zul.zwork2.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author luiz.silva
 * @param <Key>
 */
public class ZKeyTree<Key> {
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private final Key key;
    private final Map<Key,ZKeyTree<Key>> map;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZKeyTree(Key key){
        this.key = key;
        this.map = new HashMap<>();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public void putChild(ZKeyTree<Key> keyTree){
        map.put(keyTree.key, keyTree);
    }
    
    public ZKeyTree putChild(Key key){
        ZKeyTree<Key> result = new ZKeyTree<>(key);
        map.put(key,result);
        return result;
    }
    
    public boolean removeChild(Key key){
        if (map.containsKey(key)){
            map.remove(key);
            return true;
        } else {
            return false;
        }
    }
    
    public boolean removeChild(ZKeyTree<Key> keyTree){
        if (map.containsKey(keyTree.key)){
            map.remove(keyTree.key);
            return true;
        } else {
            return false;
        }
    }
    
    public ZKeyTree getChild(Key key){
        return map.get(key);
    }
    
    public ZKeyTree getChild(Integer index){
        return map.get(getKey(index));
    }
    
    public Key getKey(Integer index){
        return new ArrayList<>(map.keySet()).get(index);
    }
    
    public Integer indexOf(Key key){
        return new ArrayList<>(map.keySet()).indexOf(key);
    }
    
    public int countChildren(){
        return map.values().size();
    }
    
    public ArrayList<ZKeyTree<Key>> listChildren(){
        return new ArrayList<>(map.values());
    }
    
    public List<Key> listKeys(){
        return new ArrayList<>(map.keySet());
    }
    
    //==========================================================================
    //GETTERS SETTERS
    //==========================================================================
    public Key getKey() {
        return key;
    }
    
}
