package br.zul.zwork2.reflection;

import br.zul.zwork2.io.ZFile;
import br.zul.zwork2.io.ZPath;
import br.zul.zwork2.log.ZLogger;

/**
 *
 * @author Luiz Henrique
 */
public class ZClass {

    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private Class<?> _class;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZClass(Class<?> _class){
        this._class = _class;
    }
    
    public ZClass(ZPath path){
        try {
            this._class = Class.forName(path.format(ZPath.ZPathPattern.PACKAGE));
        } catch (ClassNotFoundException ex) {
            ZLogger log = new ZLogger(getClass(),"ZClass(ZPath path)");
            log.error.println(ex, "Classe '%s' não encontrada!", path.format(ZPath.ZPathPattern.PACKAGE));
        }
    }
    
    public ZClass(String classPackage){
        try {
            this._class = Class.forName(classPackage);
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
            this._class = Class.forName(_package);
        } catch (ClassNotFoundException ex) {
            ZLogger log = new ZLogger(getClass(),"ZClass(String classPackage)");
            log.error.println(ex, "Classe '%s' não encontrada!", _package);
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
