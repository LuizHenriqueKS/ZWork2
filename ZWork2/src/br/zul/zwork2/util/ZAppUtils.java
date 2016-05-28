/*
 * To change this license header, choose License Headers in App Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.zul.zwork2.util;

import br.zul.zwork2.log.ZLogger;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 *
 * @author Luiz
 */
public class ZAppUtils {
    
    
    public static File getAppFile(Class _class){
        String path = _class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedPath;
        try {
            decodedPath = URLDecoder.decode(path, "UTF-8");
            return new File(decodedPath);
        } catch (UnsupportedEncodingException ex) {
            ZLogger log = new ZLogger(ZAppUtils.class, "getAppFile(Class _class)");
            log.error.println(ex,"Não foi possível converter o path '%s' para UTF-8.",path);
        }
        return new File(path);
    }
    
    public static String getAppFilename(Class anyClass){
        return getAppFile(anyClass).getName();
    }
    
    
    public static boolean isAppFileJar(Class _class){
        
        return getAppFile(_class).getPath().toLowerCase().endsWith(".jar");
        
    }
    
    public static boolean isAppFileExe(Class _class){
        
        return getAppFile(_class).getPath().toLowerCase().endsWith(".exe");
        
    }
    
    public static boolean isAppFileDirectory(Class _class){
        return getAppFile(_class).isDirectory();
    }
    
    public static String getCurrentRelativePath(){
        
        //Path currentRelativePath = Paths.get("");
        //return currentRelativePath.toAbsolutePath().toString();
        return new File("").getAbsolutePath();
    }
    
}
