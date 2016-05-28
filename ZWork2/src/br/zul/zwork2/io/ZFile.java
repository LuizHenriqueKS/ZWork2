package br.zul.zwork2.io;

import java.io.File;

/**
 *
 * @author Luiz Henrique
 */
public class ZFile {
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private String filename;
    private ZPath path;
    private File file;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZFile(ZPath path){
        this.path = path;
        this.filename = path.getLastPart();
    }
    
    public ZFile(String path){
        this(new ZPath(path));
    }
    
    public ZFile(File file){
        this(file.getAbsolutePath());
        this.file = file;
    }
    
    //==========================================================================
    //GETTERS E SETTERS MODIFICADOS
    //==========================================================================
    public boolean isDirectory(){
        if (file==null){
            return false;
        } else {
            return file.isDirectory();
        }
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public String getFilename() {
        return filename;
    }

    public ZPath getPath() {
        return path;
    }
    
}
