package de.bkdev.transformation.parser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataReader {
    private final Connection conn;

    public DataReader(Connection conn) {
        this.conn = conn;
    }

    public   ResultSet readTableData(String table) throws SQLException {
        return conn.createStatement().executeQuery("SELECT * from " + table);
    }
}
