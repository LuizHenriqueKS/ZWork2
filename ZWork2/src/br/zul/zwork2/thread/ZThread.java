package br.zul.zwork2.thread;

import br.zul.zwork2.log.ZLogger;

/**
 *
 * @author Luiz Henrique
 */
public class ZThread {
    
    public static void sleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            new ZLogger(Thread.class,"sleep(long millis)").error.print(ex);
        }
    }
    
}
