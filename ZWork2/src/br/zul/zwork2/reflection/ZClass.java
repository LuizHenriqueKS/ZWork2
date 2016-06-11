package br.zul.zwork2.reflection;

import br.zul.zwork2.io.ZFile;
import br.zul.zwork2.io.ZPath;
import br.zul.zwork2.log.ZLogLevel;
import br.zul.zwork2.log.ZLogger;
import br.zul.zwork2.string.ZString;
import br.zul.zwork2.util.ZFilter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luiz Henrique
 * @param <T>
 */
public class ZClass<T> {

    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private Class<T> objectClass;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZClass(Class<T> objectClass){
        this.objectClass = objectClass;
    }
    
    public ZClass(ZPath path){
        try {
            this.objectClass = (Class<T>)Class.forName(path.format(ZPath.ZPathPattern.PACKAGE));
        } catch (ClassNotFoundException ex) {
            ZLogger log = new ZLogger(getClass(),"ZClass(ZPath path)");
            log.error.println(ex, "Classe '%s' não encontrada!", path.format(ZPath.ZPathPattern.PACKAGE));
        }
    }
    
    public ZClass(String classPackage){
        try {
            this.objectClass = (Class<T>)Class.forName(classPackage);
        } catch (ClassNotFoundException ex) {  
            ZLogger log = new ZLogger(getClass(),"ZClass(String classPackage)");
            log.error.println(ex, "Classe '%s' não encontrada!", classPackage);
        }
    }
    
    public ZClass(ZFile file){
        
        String _package = file.getPath().format(ZPath.ZPathPattern.PACKAGE);
        
        if (_package.toLowerCase().contains(".class")){        
            _package = _package.substring(0,_package.length()-".class".length());
        }
        
        try {
            this.objectClass = (Class<T>)Class.forName(_package);
        } catch (ClassNotFoundException ex) {
            ZLogger log = new ZLogger(getClass(),"ZClass(String classPackage)");
            log.error.println(ex, "Classe '%s' não encontrada!", _package);
        }
        
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS PARA CAMPOS
    ///==========================================================================
    public Collection<Field> listFields(){
        return listFields(null,null);
    }
    
    public Collection<Field> listFields(ZFilter<String,Field> filter){
        return listFields(filter,null);
    }
    
    public Collection<Field> listFields(Class<? extends Annotation> annotation){
        return listFields(null,annotation);
    }
    
    public Collection<Field> listFields(ZFilter<String,Field> filter,Class<? extends Annotation> annotation){
        //PREPARA A LISTA DE RESULTADO
        Set<Field> set = new HashSet<>();
        
        //ADICIONA TODOS OS CAMPOS, TANDOS DAS CLASSES MÃES QUANTO DAS CLASSES FILHAS
        set.addAll(Arrays.asList(objectClass.getDeclaredFields()));
        set.addAll(Arrays.asList(objectClass.getFields()));
        
        //CONVERTE DE SET PARA MAP
        Map<String,Field> map = new HashMap<>();
        for (Field f:set){
            map.put(f.getName(), f); //ADICIONA NO MAP, OS CAMPOS (A CHAVE É O NOME DO CAMPO)
        }       
        
        //VERIFICA SE A ANOTAÇÃO FOI INFORMADA
        if (annotation!=null){
            
            //SE FOI, DEVE REMOVER DA LISTA AQUELES CAMPOS QUE NÃO TEM TAL ANOTAÇÃO
            
            //OBTEM OS NOMES DOS CAMPOS
            List<String> keys = new ArrayList<>(map.keySet());
            
            //PERCORRE OS CAMPOS PARA VERIFICAR A ANOTAÇÃO
            for (String key:keys){
                //OBTEM O CAMPO
                Field value = map.get(key);
                
                //VERIFICA SE O CAMPO NÃO TEM A ANOTAÇÃO
                if (!value.isAnnotationPresent(annotation)){
                    //SE NÃO TEM, REMOVE O CAMPO DO MAP
                    map.remove(key);
                }
            }
            
        }
        
        //VERIFICA SE FOI INFORMADO O FILTRO
        if (filter!=null){
        
            //FILTRA E JÁ RETORNA O RESULTADO
            return filter.filter(map);
        
        }
        
        //RETORNA O RESULTADO
        return map.values();
    }
    
    public Field getField(final ZString fieldName){
        
        //LOGGER
        ZLogger log = new ZLogger(getClass(),"getField(final ZString fieldName)");
        
        //OBTEM OS CAMPOS COM ESSE NOME
        Collection<Field> collection = listFields(new ZFilter<String,Field>(){
            @Override
            public boolean filter(String key, Field value) {
                return fieldName.equals(key);
            }            
        });
        
        //CONVERTE A COLEÇÃO EM LISTA
        List<Field> list = new ArrayList<>(collection);
        
        //FAZ VALIDAÇÕES
        if (list.size()>1){
            throw log.error.prepareException("Foi encontrado mais de um campo com o nome '%s' na classe '%s'.", fieldName,objectClass.getName());
        } else if (list.isEmpty()){
            throw log.error.prepareException("Não foi encontrado um campo com o nome '%s' na classe '%s'.", fieldName,objectClass.getName());
        } else {
            return list.get(0);
        }
        
    }
    
    public Field getField(String fieldName){
        
        //LOGGER
        ZLogger log = new ZLogger(getClass(),"getField(String fieldName)");
        
        //TENTA OBTER O CAMPO
        try {
            return objectClass.getField(fieldName);
        } catch (NoSuchFieldException ex) {
            throw log.error.prepareException(ex, "O campo '%s' não foi encontrado na classe '%s'!", fieldName,objectClass.getName());
        } catch (SecurityException ex) {
            throw log.error.prepareException(ex, "Problema em tentar obter o campo '%s' na classe '%s'." ,fieldName,objectClass.getName());
        }
        
    }
    
    public Object getFieldValue(T object,Field field){
        
        //LOGGER
        ZLogger log = new ZLogger(getClass(),"getFieldValue(T object,Field field)");
        
        //TENTA OBTER O VALOR DO CAMPO
        try {
            return field.get(object);
        } catch (IllegalArgumentException ex) {
            
            throw log.error.prepareException(ex,"A classe '%s' não possui o campo '%s'!",objectClass.getName(),field.getName());
            
        } catch (IllegalAccessException ex) {
            
            StringBuilder msg = new StringBuilder();
            msg.append("Acessado negado ao campo '");
            msg.append(field.getName());
            msg.append("' da classe '");
            msg.append(objectClass.getName());
            msg.append("'! Talvez o campo seja PRIVATE ou PROTECTED.");
            
            throw log.error.prepareException(ex,msg.toString());
            
        }
        
    }
    
    public Object getFieldValue(T object,String fieldName){
        return getFieldValue(object,getField(fieldName));
    }
    
    public Object getFieldValue(T object,ZString fieldName){
        return getFieldValue(object, getField(fieldName));
    }
    
    public void setFieldValue(T object,Field field,Object value){
        
         //LOGGER
        ZLogger log = new ZLogger(getClass(),"setFieldValue(T object,Field field,Object value)");
        
        //TENTA OBTER O VALOR DO CAMPO
        try {
            field.set(object,value);
        } catch (IllegalArgumentException ex) {
            
            throw log.error.prepareException(ex,"A classe '%s' não possui o campo '%s'!",objectClass.getName(),field.getName());
            
        } catch (IllegalAccessException ex) {
            
            StringBuilder msg = new StringBuilder();
            msg.append("Acessado negado ao campo '");
            msg.append(field.getName());
            msg.append("' da classe '");
            msg.append(objectClass.getName());
            msg.append("'! Talvez o campo seja PRIVATE ou PROTECTED.");
            
            throw log.error.prepareException(ex,msg.toString());
            
        }
        
    }
    
    public void setFieldValue(T object,String fieldName,Object value){
        setFieldValue(object,getField(fieldName),value);
    }
    
    public void setFieldValue(T object,ZString fieldName,Object value){
        setFieldValue(object,getField(fieldName),value);
    }
    
    
    //==========================================================================
    //MÉTODOS PARA INSTANCIAR A CLASSE
    //==========================================================================
    public T newInstance(){
        ZLogger logger = new ZLogger(getClass(),"newInstance()");
        try {
            return objectClass.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            throw logger.error.prepareException(ex);
        }
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS PARA MÉTODOS
    //==========================================================================
    public Collection<Method> listMethods(){
        return listMethods((ZFilter)null,null);
    }
    
    public Collection<Method> listMethods(ZFilter<Integer,Method> filter){
        return listMethods(filter,null);
    }
    
    public Collection<Method> listMethods(Class<? extends Annotation> annotation){
        return listMethods((ZFilter)null,annotation);
    }
    
    /**
     * BUSCA E LISTA MÉTODOS PELO NOME DO MÉTODO (CASE SENSITIVE)
     * 
     * @param objectClass
     * @param methodName
     * @return 
     */
    public Collection<Method> listMethods(String methodName){
        return listMethods(new ZString(methodName,true));
    }
    
    /**
     *  BUSCA E LISTA MÉTODOS PELO NOME DO MÉTODO (CASE SENSITIVE VAI DEPENDER SE A ZSTRING FOR CASE SENSITIVE)
     * @param objectClass
     * @param methodName
     * @return 
     */
    public Collection<Method> listMethods(ZString methodName){
        return listMethods(methodName,null);
    }
    
    public Collection<Method> listMethods(String methodName,Class<? extends Annotation> annotation){
        return listMethods(new ZString(methodName,true),annotation);
    }
    
    public Collection<Method> listMethods(final ZString methodName,Class<? extends Annotation> annotation){
        
        //PREPARA O FILTRO QUE IRÁ FILTRAR OS MÉTODOS PELO NOME
        ZFilter<Integer,Method> filter = new ZFilter<Integer,Method>(){
            @Override
            public boolean filter(Integer key, Method value) {
                return methodName.equals(value.getName()); //COMPARA O NOME DO MÉTODO COM O NOME INFORMADO
            }
        };
        
        return listMethods(filter,annotation);
    }
    
    public Collection<Method> listMethods(ZFilter<Integer,Method> filter,final Class<? extends Annotation> annotation){
        
        //OBTEM TODOS OS MÉTODOS (USA O CONJUNTO SET PARA NÃO OBTER MÉTODOS DUPLICADOS)
        Set<Method> methodSet = new HashSet<>();
        methodSet.addAll(Arrays.asList(objectClass.getMethods()));
        methodSet.addAll(Arrays.asList(objectClass.getDeclaredMethods()));
       
        //CONVERTE O CONJUNTO SET PARA LISTA
        List<Method> result = new ArrayList<>(methodSet);
        
        //VERIFICA SE FOI INFORMADO UMA ANOTAÇÃO PARA FILTRAR OS MÉTODOS
        if (annotation!=null){
            
            //SE POSSUI ANOTAÇÃO, FILTRA A LISTA DE MÉTODOS POR ELA
           result = new ZFilter<Integer,Method>(){
               @Override
               public boolean filter(Integer key, Method value) {
                  return value.isAnnotationPresent(annotation); //VERIFICA SE O MÉTODO POSSUI A ANOTAÇÃO
               }
            }.filter(result);
           
        }
        
        //VERIFICA SE O USUÁRIO INFORMOU UM FILTRO GENÉRICO
        if (filter!=null){
            //SE INFORMOU ELE É APLICADO
            result = filter.filter(result);
        } 
        
        return result;
        
    }
    
    public Object invokeMethod(Object object,Method method,Object... parameters){
        ZLogger log = new ZLogger(getClass(), "invokeMethod(Object object,Method method,Object... parameters)");
        try {
            return method.invoke(object, parameters);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw log.error.prepareException(ex);
        }
    }
    
    public Object invokeMethod(Method method,Object... parameters){
        return invokeMethod(null,method,parameters);
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public Class<T> getObjectClass() {
        return objectClass;
    }
    
    public boolean isFatherOf(Class<?> c){
        return objectClass.isAssignableFrom(c);
    }
    
    public boolean isChildOf(Class<?> c){
        return c.isAssignableFrom(objectClass);
    }
    
}
