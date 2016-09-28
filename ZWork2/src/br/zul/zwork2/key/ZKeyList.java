package br.zul.zwork2.key;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author luiz.silva
 * @param <Key>
 */
public class ZKeyList<Key> {
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private List<Key> list;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZKeyList(){
        this.list = new ArrayList<>();
    }
    
    public ZKeyList(Collection<Key> keyCollection){
        this.list = new ArrayList<>(keyCollection);
    }
    
    public ZKeyList(ZKeyList<Key> keyList){
        this.list = new ArrayList<>(keyList.list);
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public int addKey(Key key){
        list.add(key);
        return list.size();
    }    
    
    public int size(){
        return list.size();
    }
    
    public void setKey(int index, Key key){
        list.set(index, key);
    }
    
    public void removeKey(int index){
        list.remove((int)index);
    }
    
    public void removeKey(Key key){
        list.remove(key);
    }
    
    public boolean hasKey(Key key){
        return list.contains(key);
    }
    
    public Key getKey(int index){
        return list.get(index);
    }
    
    public ZKeyList copy(){
        return new ZKeyList(this);
    }
    
    public boolean isEmpty(){
        return list.isEmpty();
    }
    
    public List<Key> listKeys(){
        return list;
    }
    
    public int getFirstIndex(){
        return 0;
    }
    
    public int getLastIndex(){
        return size()-1;
    }
    
    public Key getLastKey(){
        return getKey(getLastIndex());
    }
    
    public Key getFirstKey(){
        return getKey(getFirstIndex());
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.list);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ZKeyList<?> other = (ZKeyList<?>) obj;
        if (!Objects.equals(this.list, other.list)) {
            return false;
        }
        return true;
    }
    
}
