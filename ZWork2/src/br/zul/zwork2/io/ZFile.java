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
    private ZResource resource;
    
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
    
    public ZFile(ZResource resource){
        this.resource = resource;
        this.filename = resource.getFilename();
        this.path = resource.getPath();
    }
    
    //==========================================================================
    //GETTERS E SETTERS MODIFICADOS
    //==========================================================================
    public boolean isDirectory(){
        if (file==null&&resource==null){
            return false;
        } else if (file!=null){
            return file.isDirectory();
        } else if (resource!=null&&resource.getIsDirectory()!=null) {
            return resource.getIsDirectory();
        } else {
            return false;
        }
    }
    
    public boolean isResource(){
        return resource!=null;
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

    public File getFile() {
        return file;
    }
    public void setFile(File file) {
        this.file = file;
    }

    public ZResource getResource() {
        return resource;
    }
    public void setResource(ZResource resource) {
        this.resource = resource;
    }
    
}
