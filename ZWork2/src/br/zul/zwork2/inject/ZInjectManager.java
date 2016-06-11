package br.zul.zwork2.inject;

import br.zul.zwork2.annotation.ZInject;
import br.zul.zwork2.reflection.ZClass;
import br.zul.zwork2.util.ZFilter;
import java.lang.reflect.Field;
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
    public void injectInstances(Object obj,ZInjectInterface zInjectInterface){
        
        //INICIA UM ZCLASS PARA FACILITAR A BUSCA DOS CAMPOS
        ZClass c = new ZClass(obj.getClass());
        
        //LISTA OS CAMPOS COM A ANOTAÇÃO ZInject
        Collection<Field> fields = c.listFields(ZInject.class);
        
        //PERCORRE A LISTA DE CAMPOS
        for (Field f:fields){
            //OBTEM O VALOR PARA O CAMPO
            Object fieldValue = getInstance(f.getType());
            
            //SETA O VALOR AO CAMPO CORRESPONDENTE
            zInjectInterface.setValueField(obj, f, fieldValue);
        }
            
    }
    
     public void injectInstances(ZInjectInterface zInjectInterface){
        
        injectInstances(zInjectInterface, zInjectInterface);
            
    }
    
    public Object newInstance(final Class objectClass){
        
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
                
                if (!Modifier.isStatic(value.getModifiers())){//VERIFICA SE NÃO É UM MÉTODO ESTÁTICO
                    return false;
                }
                
                switch (value.getParameterCount()){ //VERIFICA A QUANTIDADE DE PARAMETROS
                    case 0: //SE TIVER 0
                        return true; //É ACEITO
                    case 1: //SE TIVER 1 PARAMETRO
                        return objectClass.isAssignableFrom(value.getParameterTypes()[0]);//VERIFICA SE O PARAMETRO É DE UM CLASSE FILHA OU IGUAL A ELA
                    default:
                        return false; //QUALQUER OUTRA QUANTIDADE NÃO É ACEITA
                }
                
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
        
        //VERIFICA SE POSSUI UM MÉTODO GETINSTANCE
        if (!methodList.isEmpty()){
            //SE POSSUI, OBTEM O PRIMEIRO MÉTODO DA LISTA
            Method methodGetInstance = methodList.get(0);
            
            //VERIFICA SE POSSUI UMA PARAMETRO
            if (methodGetInstance.getParameterCount()==1){
                //POSSUI PARAMETRO, PASSA O CLASSE QUE QUER INSTANCIAR
                result = c.invokeMethod(methodGetInstance, objectClass);
                
            } else {
                //SE NÃO POSSUI, EXECUTA O MÉTODO SEM PARAMETROS
                result = c.invokeMethod(methodGetInstance);
            }
            
        } else {
            
            //SE NÃO ENCONTROU NENHUM MÉTODO getInstance NO PADRÃO CERTO, INSTANCIA DE FORMA NORMAL
            result = c.newInstance();
            
        }
        
        //SALVA A INSTANCIA
        setInstance(objectClass, result);
        
        //E RETORNA A INSTANCIA
        return result;
        
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
    
    public static void injectIn(Object obj,ZInjectInterface zInjectInterface){
        getInstance().injectInstances(obj,zInjectInterface);
    }
    
    public static void injectIn(ZInjectInterface zInjectInterface){
        getInstance().injectInstances(zInjectInterface);
    }
    
}
