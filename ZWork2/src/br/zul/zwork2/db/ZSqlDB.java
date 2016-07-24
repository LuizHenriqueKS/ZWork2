package br.zul.zwork2.db;

import br.zul.zwork2.db.filter.ZDBFilter;
import br.zul.zwork2.db.filter.ZDBFilterEquals;
import br.zul.zwork2.db.query.ZDBJoin;
import br.zul.zwork2.db.query.ZDBOrderBy;
import br.zul.zwork2.db.query.ZDBQuery;
import br.zul.zwork2.db.query.ZDBSelect;
import br.zul.zwork2.db.selection.ZDBSelection;
import br.zul.zwork2.log.ZLogger;
import br.zul.zwork2.sql.ZSql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Luiz Henrique
 */
public abstract class ZSqlDB extends ZDB {

    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private Connection connection;
    private String connectionUrl;
    private String host;
    private Integer port;

    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    //==========================================================================
    //MÉTODOS PÚBLICOS ABSTRATOS
    //==========================================================================
    public abstract String getDriverClassName();

    public abstract String getConnectionUrl(String host, Integer port);

    public abstract String filterToCondition(ZDBFilter filter);

    //==========================================================================
    //MÉTODOS PÚBLICOS SOBRESCRITOS
    //==========================================================================
    @Override
    public void connect() {
        //PREPARA O LOGGER
        ZLogger logger = new ZLogger(getClass(), "connect()");
        try {
            //CARREGA O CLASSE DO DRIVE PARA CONECTAR COM O BANCO
            Class.forName(getDriverClassName());
            //VERIFICA SE TEM AS INFORMAÇÕES PARA ISSO
            if (connectionUrl != null) {
                //TENTA CONECTAR COM O BANCO
                connection = DriverManager.getConnection(connectionUrl, getUsername(), getPassword());
            } else if (host != null && port != null) {
                //MONTA O URL DE CONEXÃO
                String url = getConnectionUrl(host, port);
                //TENTA CONECTAR COM O BANCO
                connection = DriverManager.getConnection(url, getUsername(), getPassword());
            }
            //SE CHEGOU AQUI É PORQUE NÃO TEM AS INFORMAÇÕES NECESSÁRIAS
            throw logger.error.prepareException("Não foi informado o url de conexão ou o host e senha para se conectar ao banco de dados!");
        } catch (ClassNotFoundException | SQLException | RuntimeException e) {
            throw logger.error.prepareException(e);
        }
    }

    @Override
    public void disconnect() {
        try {
            //FECHA A CONEXÃO
            connection.close();
            //LIMPA A MEMORIA
            connection = null;
        } catch (SQLException ex) {
            throw new ZLogger(getClass(), "disconnect").error.prepareException(ex);
        }
    }

    @Override
    public ZDBResult execute(ZDBQuery query) {
        switch (query.getType()) {
            case SELECT:
                return select(query);
        }
        ZLogger logger = new ZLogger(getClass(), "execute(ZDBQuery query)");
        throw logger.error.prepareException("O tipo de query '%s' não foi programado.", query.getType());
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }

    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    private void setValue(int index, PreparedStatement ps, ZDBFilter filter) {
        Object value = null;
        switch (filter.getType()) {
            case EQUALS:
                value = ((ZDBFilterEquals) filter).getValue();
                break;
        }
        //SE TEM VALOR A SER SETADO (AS VEZES PODE SER UM FILTRO QUE NÃO PRECISE SETAR VALOR)
        if (value != null) {
            setValue(index,ps, filter);
        }
    }

    public void setValue(int index, PreparedStatement ps, Object obj) {
        ZLogger logger = new ZLogger(getClass(),"setValue(int index, PreparedStatement ps, Object obj)");
        try {
            //String
            if (obj instanceof String) {
                ps.setString(index, (String) obj);
                return;
            }
            //SE CHEGOU AQUI, É PORQUE NÃO FOI PROGRAMADO PARA PASSAR ESSA VARIAVEL
            throw logger.error.prepareException("Não foi programado para setar esse tipo de objeto '%s' no statement!",obj.getClass().getName());
        } catch (SQLException ex) {
            throw logger.error.prepareException(ex);
        }

    }

    //==========================================================================
    //MÉTODOS PARA QUERY
    //==========================================================================
    private ZDBResult select(ZDBQuery query) {
        //PREPARA O LOGGER
        ZLogger logger = new ZLogger(getClass(), "select(ZDBQuery query)");
        //PREPARA PARA FAZER A SELAÇÃO
        ZDBSelect select;
        //OBTEM OU MONTA A SELEÇÃO
        if (query instanceof ZDBSelect) {
            select = (ZDBSelect) query;
        } else {
            select = new ZDBSelect(query.getName());
            select.copyFiltersFrom(query);
        }
        //MONTA O SQL
        ZSql sql = new ZSql(select.getName());
        //PASSA AS COLUNAS DA SELAÇÃO
        for (ZDBSelection selection : select.listSelections()) {
            sql.addColumn(selection.toString());
        }
        //PASSA OS JOINS
        for (ZDBJoin join : select.listJoins()) {
            StringBuilder leftJoin = new StringBuilder();
            leftJoin.append("LEFT JOIN ");
            leftJoin.append(join.getName());
            leftJoin.append(" ON ");
            ZSql subSql = new ZSql(join.getQuery().getName());
            for (ZDBFilter filter : join.getQuery().listFilters()) {
                subSql.addCondition(filterToCondition(filter));
            }
            leftJoin.append(subSql.getWhereSql());
            sql.addLeftJoin(leftJoin.toString());
        }
        //PASSA O GROUP BY
        for (String groupBy : select.listGroupBys()) {
            sql.addGroupBy(groupBy);
        }
        //PASSA O ORDER BY
        for (ZDBOrderBy orderBy : select.listOrderBys()) {
            sql.addOrderBy(orderBy.getName(), orderBy.isAscending());
        }
        //PASSA AS CONDIÇÕES
        for (ZDBFilter filter : select.listFilters()) {
            sql.addCondition(filterToCondition(filter));
        }
        //PASSA OS PARAMETROS
        try {
            //PREPARA O STATAMENT
            PreparedStatement ps = getConnection().prepareStatement(sql.getSelectSql());
            int index = 0;
            //PASSA O PARAMETRO
            for (ZDBFilter filter : select.listFilters()) {
                setValue(index++, ps, filter);
            }
            //RETORNA O RESULTADO
            ResultSet rs = ps.executeQuery();
            return new ZSqlDBResult(ps,rs);
        } catch (SQLException ex) {
            throw logger.error.prepareException(ex);
        }
    }

    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getConnectionUrl() {
        return connectionUrl;
    }

    public void setConnectionUrl(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

}
