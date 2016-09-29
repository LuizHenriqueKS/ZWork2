package br.zul.zwork2.sql;

import br.zul.zwork2.db.ZDBType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luiz Henrique
 */
public class ZSql {

    //==========================================================================
    //VARIÁVEIS PRIVADAS
    //==========================================================================
    private String tableName;
    private final List<String> columnList;
    private final List<String> joinList;
    private final List<String> conditionList;
    private final List<ZSqlOrderBy> orderByList;
    private final List<String> havingList;
    private final List<String> groupByList;
    private Long limit;
    private Long offset;
    private ZDBType type;

    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZSql(String tableName) {
        this.tableName = tableName;
        this.columnList = new ArrayList<>();
        this.joinList = new ArrayList<>();
        this.conditionList = new ArrayList<>();
        this.orderByList = new ArrayList<>();
        this.havingList = new ArrayList<>();
        this.groupByList = new ArrayList<>();
    }

    //==========================================================================
    //MÉTODOS PARA COLUNAS
    //==========================================================================
    /**
     * addColumn("t.column");
     *
     * @param column
     * @return
     */
    public ZSql addColumn(String column) {
        this.columnList.add(column);
        return this;
    }

    public ZSql removeColumn(String column) {
        this.columnList.remove(column);
        return this;
    }

    public ZSql clearColumns() {
        this.columnList.clear();
        return this;
    }

    public List<String> listColumns() {
        return new ArrayList<>(this.columnList);
    }

    //==========================================================================
    //MÉTODOS PARA LEFT JOINS
    //==========================================================================
    /**
     * Ex: addJoin("LEFT JOIN Table AS t ON t.id = ;idT");
     *
     * @param join
     * @return
     */
    public ZSql addJoin(String join) {
        this.joinList.add(join);
        return this;
    }

    public ZSql removeJoin(String join) {
        this.joinList.remove(join);
        return this;
    }

    public ZSql clearJoins() {
        joinList.clear();
        return this;
    }

    //==========================================================================
    //MÉTODOS PARA CONDIÇÕES
    //==========================================================================
    /**
     * addCondition("column = :column")
     *
     * @param condition
     * @return
     */
    public ZSql addCondition(String condition) {
        conditionList.add(condition);
        return this;
    }

    public ZSql removeCondition(String condition) {
        conditionList.remove(condition);
        return this;
    }

    public ZSql clearConditions() {
        conditionList.clear();
        return this;
    }

    public List<String> listConditions() {
        return new ArrayList<>(conditionList);
    }

    //==========================================================================
    //MÉTODOS PARA GRUPOS
    //==========================================================================
    public ZSql addGroupBy(String groupBy) {
        this.groupByList.add(groupBy);
        return this;
    }

    public ZSql removeGroupBy(String groupBy) {
        this.groupByList.remove(groupBy);
        return this;
    }

    public ZSql clearGroupBy() {
        this.groupByList.clear();
        return this;
    }

    public List<String> listGroupBy() {
        return new ArrayList<>(groupByList);
    }

    //==========================================================================
    //MÉTODOS PARA ORDENAR
    //==========================================================================
    public ZSql addOrderBy(String column, boolean ascending) {
        orderByList.add(new ZSqlOrderBy(column, ascending));
        return this;
    }

    public ZSql removeOrderBy(ZSqlOrderBy orderBy) {
        orderByList.remove(orderBy);
        return this;
    }

    public ZSql clearOrderBys() {
        orderByList.clear();
        return this;
    }

    public List<ZSqlOrderBy> listOrderBys() {
        return new ArrayList<>(orderByList);
    }

    //==========================================================================
    //MÉTODOS PARA HAVING
    //==========================================================================
    public ZSql addHaving(String having) {
        havingList.add(having);
        return this;
    }

    public ZSql removeHaving(String having) {
        havingList.remove(having);
        return this;
    }

    public ZSql clearHavings() {
        havingList.clear();
        return this;
    }

    public List<String> listHavings() {
        return new ArrayList<>(havingList);
    }

    //==========================================================================
    //MÉTODOS PARA MONTAR O SQL
    //==========================================================================
    public String getWhereSql() {
        //VERIFICA SE NÃO POSSUI CONDIÇÕES
        if (conditionList.isEmpty()) {
            //RETORNA VAZIO
            return "";
        }
        //COMEÇA A MONTAR O SQL
        StringBuilder result = new StringBuilder();
        result.append("WHERE ");
        boolean addAnd = false;
        for (String condition : conditionList) {
            if (addAnd) {
                result.append(" AND ");
            }
            result.append(condition);
            addAnd = true;
        }
        return result.toString();
    }

    public String getSelectSql() {
        //COMEÇA A MONTAR O SQL
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        //VERIFICA SE NÃO POSSUI COLUNAS
        if (columnList.isEmpty()) {
            //SE NÃO FOI INFORMADO QUAIS COLUNAS RETORNAS, RETORNA TUDO
            sql.append("*");
        } else {
            boolean first = true;
            for (String column:columnList){
                if (!first){
                    sql.append(",");
                }
                sql.append(column);
                first = false;
            }
        }
        //ADICIONA QUAL TABELA BUSCAR
        sql.append(" FROM ");
        sql.append(tableName);
        sql.append(" \r\n ");
        //ADICIONA OS NOVOS JOINS
        for (String join : joinList) {
            sql.append(join);
            sql.append(" \r\n ");
        }
        //ADICIONA AS CONDIÇÕES SE TIVER
        sql.append(getWhereSql());
        //ADICIONA O GRUPO BY
        if (!groupByList.isEmpty()) {
            //ADICIONA O CLAUSULA DO GRUPO BY
            sql.append(" GROUP BY ");
            boolean addComma = false;
            //ADICIONO AS COLUNAS A AGRUPAR
            for (String groupBy : groupByList) {
                if (addComma) {
                    sql.append(", ");
                }
                sql.append(groupBy);
                addComma = true;
            }
            sql.append(" \r\n ");
        }
        //ADICIONA O ORDER BY
        if (!orderByList.isEmpty()) {
            //ADICIONA A CLAUSULA DO ORDER BY
            sql.append(" ORDER BY ");
            boolean addComma = false;
            //ADICIONO AS COLUNAS A ORDENAR
            for (ZSqlOrderBy orderBy : orderByList) {
                if (addComma) {
                    sql.append(", ");
                }
                sql.append(orderBy.getColumn());
                if (!orderBy.isAscending()) {
                    sql.append(" DESC");
                }
                addComma = true;
            }
            sql.append(" \r\n ");
        }
        //ADICIONA O HAVING
        if (!havingList.isEmpty()) {
            //ADICIONA O CLAUSULA DO HAVING
            sql.append(" HAVING ");
            boolean addComma = false;
            //ADICIONO AS COLUNAS HAVING
            for (String having : havingList) {
                if (addComma) {
                    sql.append(", ");
                }
                sql.append(having);
                addComma = true;
            }
            sql.append(" \r\n ");
        }
        //ACIONA O OFFSET
        if (offset!=null){
            sql.append(String.format(" OFFSET %d\r\n ",offset));
        }
        //ACIONA O LIMIT
        if (limit!=null){
            sql.append(String.format(" LIMIT %d\r\n ",limit));
        }
        //RETORNA O SQL MONTADO
        return sql.toString();
    }

    //==========================================================================
    //GETTERS E SETTERS
    //==========================================================================
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public ZDBType getType() {
        return type;
    }

    public void setType(ZDBType type) {
        this.type = type;
    }

}
