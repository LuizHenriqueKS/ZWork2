package br.zul.zwork2.reflection;

import br.zul.zwork2.io.ZPath;
import br.zul.zwork2.io.ZResource;
import br.zul.zwork2.io.ZZipFile;
import br.zul.zwork2.util.ZAppUtils;
import br.zul.zwork2.filter.ZListFilter;
import java.io.File;
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
    private final ZPath path;
    private final Class<?> caller;
    
    //==========================================================================
    //MÉTODOS CONTRUTORES
    //==========================================================================
    public ZPackage(Class<?> caller,Package p){
        this.caller = caller;
        this.path = new ZPath(p);
    }
    
    /**
     * 
     * @param caller A classe de origem onde esse ZPackage foi instanciado. (Usado para obter dados da aplicação).
     * @param p Caminho do pacote
     */
    public ZPackage(Class<?> caller,String p){
        this.caller = caller;
        this.path = new ZPath(p);
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICAS
    //==========================================================================
    public String[] listParts(){
        return this.path.listParts();
    }
    
    public Package toPackage(){
        return Package.getPackage(path.toString());
    }
    
    /**
     * 
     * @param subresources Lista os recursos dos subpackages também.
     * @return 
     */
    public List<ZResource> listResources(boolean subresources){
        
        if (ZAppUtils.isAppFileDirectory(caller)){
            
            List<ZResource> result = new ArrayList<>();
            File dir = new File(ZAppUtils.getAppFile(caller),path.format(ZPath.ZPathPattern.WINDOWS));
            listFiles(result,subresources,dir);
            return result;
            
        } else {
            List<ZResource> result = new ZZipFile(ZAppUtils.getAppFile(caller)).listFiles();
            
            //SE O FILTRO DE PATH ESTÁ NULL RETORNA SEMPRE NULL
            if (path==null){
                //NÃO PRECISA FILTRAR
                return result;
            }
            
            //SE NÃO TIVER NULL, FILTRA
            return new ZListFilter<Integer,ZResource>(){
                @Override
                public boolean filter(Integer key, ZResource value) {
                    
                    //OBTEM O PARENTE
                    ZPath valueParent = value.getPath().getParent();
                    
                    //VERIFICA SE ELE ESTÁ NULL
                    if (valueParent==null){
                        //RETORNA FALSE
                        return false;
                    }
                   
                    //SE NÃO, COMPARA OS DIRETÓRIOS
                    return valueParent.equals(path);
                }
            }.filter(result);
            
        }
        
    }
    
    public List<ZResource> listResources(boolean subresources,ZListFilter<Integer,ZResource> filter){
        return filter.filter(listResources(subresources));
    }
    
    public List<ZClass> listClasses(boolean subresources){
        
        List<ZClass> result = new ArrayList<>();
        ZPath appPath = new ZPath(ZAppUtils.getAppFile(caller).getAbsolutePath());
        boolean isAppFileDirectory = ZAppUtils.isAppFileDirectory(caller);
        
        for (ZResource z:listResources(subresources)){
            if (z.getFilename().toLowerCase().endsWith(".class")){
                
                String classPath;
                if (isAppFileDirectory){
                    classPath = z.getPath().format(ZPath.ZPathPattern.PACKAGE).substring(appPath.format(ZPath.ZPathPattern.PACKAGE).length());
                } else {
                    classPath = z.getPath().format(ZPath.ZPathPattern.PACKAGE);
                }
                
                if (classPath.startsWith(".")){
                    classPath = classPath.substring(1);
                }
                
                if (classPath.toLowerCase().endsWith(".class")){
                    classPath = classPath.substring(0,classPath.length()-".class".length());
                }
                
                result.add(new ZClass(classPath));
            }
        }
        return result;
    }
    
    public List<ZClass> listClasses(boolean subresources,ZListFilter<Integer,ZClass> filter){
        return filter.filter(listClasses(subresources));
    }
    
    /**
     * 
     * @param subresources Listar também classes do subpacotes
     * @param originClass Filtrar por classes que são filhas dessa.
     * @param ignoreClassesEquals Ignorar classes iguais a informada 'originClass'.
     * @return 
     */
    public List<ZClass> listClasses(boolean subresources,final Class<?> originClass,final boolean ignoreClassesEquals){
        
        ZListFilter filter = new ZListFilter<Integer,ZClass>(){
            @Override
            public boolean filter(Integer key, ZClass value) {
                //SE É PARA IGNORAR CLASSES IGUAIS A DEFINIDA
                if (ignoreClassesEquals&&value.getObjectClass().equals(originClass)){
                    return false;
                }
                //VERIFICA SE É UMA CLASSE EXTENDIDA DA ORIGINCLASS
                return value.isChildOf(originClass);
            }
        };
        
        return listClasses(subresources,filter);
        
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private void listFiles(List<ZResource> list,boolean subresources,File directory){
        for (File file:directory.listFiles()){
            list.add(new ZResource(file));
            if (subresources&&file.isDirectory()){
                listFiles(list,subresources,file);
            }
        }
    }
    
}