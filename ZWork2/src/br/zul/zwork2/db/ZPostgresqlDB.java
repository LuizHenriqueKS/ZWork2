package br.zul.zwork2.db;

import br.zul.zwork2.db.filter.ZDBFilter;

/**
 *
 * @author luiz.silva
 */
public class ZPostgresqlDB extends ZSqlDB {

    public ZPostgresqlDB(String host, Integer port,String dataBaseName) {
        super(host, port, dataBaseName);
    }

    @Override
    public String getDriverClassName() {
        return null;
    }

    @Override
    public String getConnectionUrl(String host, Integer port,String dataBaseName) {
        return String.format("jdbc:postgresql://%s:%d/%s",host,port,dataBaseName);
    }

    @Override
    public String filterToConditionCustom(ZDBFilter filter) {
        return null;
    }

    @Override
    public ZDBDefinition getDefinition() {
        return new ZDBDefinition(this);
    }

    @Override
    public ZDBType getType() {
        return ZDBType.POSTGRES;
    }
    
}
