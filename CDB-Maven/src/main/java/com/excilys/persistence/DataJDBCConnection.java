package com.excilys.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataJDBCConnection {
	
	private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl( "jdbc:mysql://127.0.0.1:3306/computer-database-db" );
        config.setUsername( "admincdb" );
        config.setPassword( "qwerty1234" );
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource( config );
    }

    private DataJDBCConnection() {}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

}
