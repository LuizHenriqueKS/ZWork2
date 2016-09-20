package br.zul.zwork2.string;

/**
 *
 * @author Luiz Henrique
 */
public abstract class ZStringSearchType {
   
    
    ////////////////////////////////////////////////////////////////////////////
    //ENUMS
    ////////////////////////////////////////////////////////////////////////////
    /**
     * RIGHT - Obtem os indices dos primeiros
     * que aparecem do fim ao início do texto Ex: source = "123,.678." patterns
     * = "." e ",."
     *
     * indices resultantes = "4" e "8"
     *
     */
        /**
         * CUMULATIVE - Obtem todos os indices sem se importar com quem vem
         * primeiro ou se um padrão faz parte de outro. Ex: source = "123,.678."
         * patterns = "." e ",."
         *
         * indices resultantes = {"3",",."},{"4","."} e {"8",",."}
         *      
         *
         */
        
        /**
         * LEFT_UNIQUE - Obtem os indices dos primeiros que aparecerem do início do
         * texto ao fim. Ex: source = "123,.678." patterns = "." e ",."
         *
         * indices resultantes = {"3",",."} e {"8","."} 
         */
    
    //==========================================================================
    //CONSTANTES PÚBLICAS
    //==========================================================================
    /**
    * CUMULATIVE - Obtem todos os indices sem se importar com quem vem
    * primeiro ou se um padrão faz parte de outro. Ex: source = "123,.678."
    * patterns = "." e ",."
    *
    * indices resultantes = {"3",",."},{"4","."} e {"8",",."}
    *      
    *
    */
    public static final ZStringSearchType COMULATIVE = new ZStringSearchType() {
        @Override
        public void filter(ZStringSearchTypeArgs args) {}
    };
    
    
    /**
     * LEFT_UNIQUE - Obtem os indices dos primeiros que aparecerem do início do
     * texto ao fim. Ex: source = "123,.678." patterns = "." e ",."
     *
     * indices resultantes = {"3",",."} e {"8","."} 
     */
    public static final ZStringSearchType LEFT_UNIQUE = new ZStringSearchType() {
        @Override
        public void filter(ZStringSearchTypeArgs args) {
            if (args.getFirstResult().getIndex()<args.getSecondResult().getIndex()){
                args.setRemoveSecond(true);
            } else if (args.getFirstResult().getIndex()>args.getSecondResult().getIndex()){
                args.setRemoveFirst(true);
            } else if (args.getFirstResult().isBetween(args.getSecondResult())){
                args.setRemoveFirst(true);
            } else if (args.getSecondResult().isBetween(args.getFirstResult())){
                args.setRemoveSecond(true);
            }
        }
    };
    
    //==========================================================================
    //MÉTODOS PÚBLICOS ABSTRATOS
    //==========================================================================
    public abstract void filter(ZStringSearchTypeArgs args);
    
}
