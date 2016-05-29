package br.zul.zwork2.reflection;

import br.zul.zwork2.io.ZFile;
import br.zul.zwork2.io.ZPath;
import br.zul.zwork2.log.ZLogger;
import br.zul.zwork2.string.ZString;
import br.zul.zwork2.util.ZFilter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
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
 */
public class ZClass<T> {

    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private Class<T> _class;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZClass(Class<T> _class){
        this._class = _class;
    }
    
    public ZClass(ZPath path){
        try {
            this._class = (Class<T>)Class.forName(path.format(ZPath.ZPathPattern.PACKAGE));
        } catch (ClassNotFoundException ex) {
            ZLogger log = new ZLogger(getClass(),"ZClass(ZPath path)");
            log.error.println(ex, "Classe '%s' não encontrada!", path.format(ZPath.ZPathPattern.PACKAGE));
        }
    }
    
    public ZClass(String classPackage){
        try {
            this._class = (Class<T>)Class.forName(classPackage);
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
            this._class = (Class<T>)Class.forName(_package);
        } catch (ClassNotFoundException ex) {
            ZLogger log = new ZLogger(getClass(),"ZClass(String classPackage)");
            log.error.println(ex, "Classe '%s' não encontrada!", _package);
        }
        
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    ///==========================================================================
    public Collection<Field> listFields(Class<T> object){
        return listFields(object,null,null);
    }
    
    public Collection<Field> listFields(Class<T> object,ZFilter<String,Field> filter){
        return listFields(object,filter,null);
    }
    
    public Collection<Field> listFields(Class<T> object,Class<? extends Annotation> annotation){
        return listFields(object,null,annotation);
    }
    
    public Collection<Field> listFields(Class<T> object,ZFilter<String,Field> filter,Class<? extends Annotation> annotation){
        //PREPARA A LISTA DE RESULTADO
        Set<Field> set = new HashSet<>();
        
        //ADICIONA TODOS OS CAMPOS, TANDOS DAS CLASSES MÃES QUANTO DAS CLASSES FILHAS
        set.addAll(Arrays.asList(object.getDeclaredFields()));
        set.addAll(Arrays.asList(object.getFields()));
        
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
    
    public Field getField(Class<T> object,final ZString fieldName){
        
        //LOGGER
        ZLogger log = new ZLogger(getClass(),"getField(Class<T> object,final ZString fieldName)");
        
        //OBTEM OS CAMPOS COM ESSE NOME
        Collection<Field> collection = listFields(object, new ZFilter<String,Field>(){
            @Override
            public boolean filter(String key, Field value) {
                return fieldName.equals(key);
            }            
        });
        
        //CONVERTE A COLEÇÃO EM LISTA
        List<Field> list = new ArrayList<>(collection);
        
        //FAZ VALIDAÇÕES
        if (list.size()>1){
            throw log.error.prepareException("Foi encontrado mais de um campo com o nome '%s' na classe '%s'.", fieldName,object.getName());
        } else if (list.isEmpty()){
            throw log.error.prepareException("Não foi encontrado um campo com o nome '%s' na classe '%s'.", fieldName,object.getName());
        } else {
            return list.get(0);
        }
        
    }
    
    public Field getField(Class<T> object,String fieldName){
        
        //LOGGER
        ZLogger log = new ZLogger(getClass(),"getField(Class<T> object,String fieldName)");
        
        //TENTA OBTER O CAMPO
        try {
            return object.getField(fieldName);
        } catch (NoSuchFieldException ex) {
            throw log.error.prepareException(ex, "O campo '%s' não foi encontrado na classe '%s'!", fieldName,object.getName());
        } catch (SecurityException ex) {
            throw log.error.prepareException(ex, "Problema em tentar obter o campo '%s' na classe '%s'." ,fieldName);
        }
        
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public Class<?> get_class(){
        return this._class;
    }
    
    public boolean isFatherOf(Class<?> c){
        return _class.isAssignableFrom(c);
    }
    
    public boolean isChildOf(Class<?> c){
        return c.isAssignableFrom(_class);
    }
    
}
