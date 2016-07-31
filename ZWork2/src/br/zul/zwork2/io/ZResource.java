package br.zul.zwork2.io;

import java.io.File;

/**
 *
 * @author Luiz Henrique
 */
public class ZResource {
    
    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private String filename;
    private ZPath path;
    private Boolean isDirectory;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
     public ZResource(String path){
        init(new ZPath(path));
    }
    
    public ZResource(ZPath path){
        init(path);
    }
    
    public ZResource(File file){
        this.isDirectory = file.isDirectory();
        init(new ZPath(file.getAbsolutePath()));
    }
    
    //==========================================================================
    //MÉTODOS DE CONSTRUÇÃO
    //==========================================================================
    private void init(ZPath path){
        this.path = path;
        this.filename = path.getLastPart();
    }
    
    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }

    public ZPath getPath() {
        return path;
    }
    public void setPath(ZPath path) {
        this.path = path;
    }

    public Boolean getIsDirectory() {
        return isDirectory;
    }
    public void setIsDirectory(Boolean isDirectory) {
        this.isDirectory = isDirectory;
    }
    
}
