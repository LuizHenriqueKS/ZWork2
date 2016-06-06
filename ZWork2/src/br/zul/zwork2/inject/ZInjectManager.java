package br.zul.zwork2.inject;

import br.zul.zwork2.reflection.ZClass;
import br.zul.zwork2.util.ZFilter;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Luiz Henrique
 */
public class ZInjectManager {
    
    //==========================================================================
    //VÁRIVAVEIS PRIVADAS ESTÁTICAS
    //==========================================================================
    public static ZInjectManager instance;
    
    //==========================================================================
    //VÁRIVAVEIS PRIVADAS
    //==========================================================================
    private Map<Class,Object> instanceMap;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZInjectManager(){
        instanceMap = new HashMap<>();
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public void injectInstances(Object obj){
        
    }
    
    public Object newInstance(Class objectClass){
        
        //PREPARA PARA OBTER A INSTANCE
        Object result;
        
        //INICIA UM ZCLASS
        ZClass c = new ZClass(objectClass);
        
        //OBTEM OS MÉTODOS GETINSTANCE ESTÁTICOS DA CLASSE
        Collection<Method> methodCollection = c.listMethods(new ZFilter<Integer,Method>(){
            @Override
            public boolean filter(Integer key, Method value) {
                
                if (!value.getName().equals("getInstance")){ //SE O MÉTODO NÃO SE CHAMAR getInstance ENTÃO CAI FORA
                    return false;
                }
                
                return Modifier.isStatic(value.getModifiers()); //VERIFICA SE É UM MÉTODO ESTÁTICO
                
            }
        });
        
        //CONVERTE A COLEÇÃO EM LISTA
        List<Method> methodList = new ArrayList<>(methodCollection);
        
        //ORDENA A LISTA PELOS METODOS COM MAIS PARAMETROS
        Collections.sort(methodList, new Comparator<Method>(){
            @Override
            public int compare(Method t, Method t1) {
                return -(t.getParameterCount()-t1.getParameterCount());
            }
        });
        
        //OBTEM O MÉTODO 
        
    }
    
    public Object getInstance(Class objectClass){
        //VERIFICA SE JÁ NÃO TEM UM INSTANCIA DESSA CLASSE
        if (instanceMap.containsKey(objectClass)){
            //SE JÁ TEM, RETORNA A QUE JÁ TEM
            return instanceMap.get(objectClass);
        } else {
            //SE NÃO TIVER, INSTANCIA UMA NOVA E RETORNA ELA
            return newInstance(objectClass);
        }
        
    }
    public void setInstance(Class objectClass,Object object){
        instanceMap.put(objectClass,object);
    }
        
    //==========================================================================
    //MÉTODOS ESTÁTICOS
    //==========================================================================
    public static ZInjectManager getInstance(){
        
        if (instance==null){
            instance = new ZInjectManager();
        }
        
        return instance;
    }
    public static void setInstance(ZInjectManager manager){
       instance = manager;
    }
    
    public static void injectIn(Object obj){
        getInstance().injectInstances(obj);
    }
    
}
