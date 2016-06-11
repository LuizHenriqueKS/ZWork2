package br.zul.zwork2.reflection;

import br.zul.zwork2.io.ZFile;
import br.zul.zwork2.io.ZPath;
import br.zul.zwork2.io.ZZipFile;
import br.zul.zwork2.util.ZAppUtils;
import br.zul.zwork2.util.ZFilter;
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
    public List<ZFile> listResources(boolean subresources){
        
        if (ZAppUtils.isAppFileDirectory(getClass())){
            
            List<ZFile> result = new ArrayList<>();
            File dir = new File(ZAppUtils.getAppFile(caller),path.format(ZPath.ZPathPattern.WINDOWS));
            listFiles(result,subresources,dir);
            return result;
            
        } else {
            
            List<ZFile> result = new ZZipFile(ZAppUtils.getAppFile(caller)).listFiles();
            
            return new ZFilter<Integer,ZFile>(){
                @Override
                public boolean filter(Integer key, ZFile value) {
                    return value.getPath().equals(path);
                }
            }.filter(result);
            
        }
        
    }
    
    public List<ZFile> listResources(boolean subresources,ZFilter<Integer,ZFile> filter){
        return filter.filter(listResources(subresources));
    }
    
    public List<ZClass> listClasses(boolean subresources){
        List<ZClass> result = new ArrayList<>();
        for (ZFile z:listResources(subresources)){
            if (z.getFilename().toLowerCase().endsWith(".class")){
                result.add(new ZClass(z));
            }
        }
        return result;
    }
    
    public List<ZClass> listClasses(boolean subresources,ZFilter<Integer,ZClass> filter){
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
        
        ZFilter filter = new ZFilter<Integer,ZClass>(){
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
    private void listFiles(List<ZFile> list,boolean subresources,File directory){
        for (File file:directory.listFiles()){
            list.add(new ZFile(file));
            if (subresources&&file.isDirectory()){
                listFiles(list,subresources,file);
            }
        }
    }
    
}