package br.zul.zwork2.reflection;

import br.zul.zwork2.util.ZFilter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luiz Henrique
 */
public class ZPackage {

    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private String path;
    
    //==========================================================================
    //MÉTODOS CONTRUTORES
    //==========================================================================
    public ZPackage(Package p){
        this.path = p.toString();
    }
    
    /**
     * 
     * @param p Caminho do pacote
     */
    public ZPackage(String p){
        this.path = p;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICAS
    //==========================================================================
    public String[] split(){
        return this.path.split(".");
    }
    
    public Package toPackage(){
        return Package.getPackage(path);
    }
    
    /**
     * 
     * @param subresources Lista os recursos dos subpackages também.
     * @return 
     */
    public List<ZResource> listResources(boolean subresources){
        return new ArrayList<>();
    }
    
    public List<ZResource> listResources(boolean subresources,ZFilter<Integer,ZResource> filter){
        return filter.filter(listResources(subresources));
    }
    
    public List<ZClass> 
    
    
}