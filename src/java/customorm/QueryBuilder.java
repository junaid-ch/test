/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customorm;

/**
 *
 * @author junaid.ahmad
 */
public class QueryBuilder {

    StringBuilder query = new StringBuilder();

    public QueryBuilder select(String... arg) {
        query.append("select ");
        if (arg.length == 0) {
            query.append("*");
        } else {
            for (String arg1 : arg) {
                query.append(arg1).append(",");
            }
            query.deleteCharAt(query.length() - 1);
        }
        return this;
    }

    public void insert(String table, String... columns) {
        StringBuilder value = new StringBuilder();

        query.append("insert into ")
                .append(table)
                .append(" (");
        value.append(" values(");
        for (String column : columns) {
            query.append(column).append(",");
            value.append("?,");
        }
        query.deleteCharAt(query.length() - 1);
        value.deleteCharAt(value.length() - 1);
        query.append(")");
        value.append(")");
        query.append(value);
    }

    public QueryBuilder delete() {
        query.append("delete ");
        return this;
    }

    public QueryBuilder update(String tableName) {
        query.append("update ")
                .append(tableName);
        return this;
    }

    public QueryBuilder set(String... columns) {
        query.append(" set ");
        for (String column : columns) {
            query.append(column)
                    .append(" = ?,");
        }
        query.deleteCharAt(query.length() - 1);

        return this;
    }

    public QueryBuilder from(String table) {
        query.append(" from ")
                .append(table);

        return this;
    }

    public QueryBuilder where(String condition) {
        query.append(" where ")
                .append(condition)
                .append("?");

        return this;
    }

    public String prepare() {
        return query.toString();
    }
}
