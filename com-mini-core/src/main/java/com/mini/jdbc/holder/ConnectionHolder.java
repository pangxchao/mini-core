package com.mini.jdbc.holder;

import java.sql.Connection;
import java.sql.SQLException;

public final class ConnectionHolder {
    private Connection connection;
    private int referenceCount = 0;

    /**
     * The value of connection
     * @param connection The value of connection
     * @return {@Code this}
     */
    public ConnectionHolder setConnection(Connection connection) {
        this.connection = connection;
        return this;
    }

    /**
     * Gets the value of connection.
     * @return The value of connection
     */
    public Connection getConnection() {
        return connection;
    }

    public void requestedConnection() {
        this.referenceCount++;
    }

    public void releasedConnection() throws SQLException {
        this.referenceCount--;
        if (referenceCount <= 0) {
            closeConnection();
        }
    }

    public boolean hasOpenConnection() throws SQLException {
        return connection != null && !connection.isClosed();
    }

    private void closeConnection() throws SQLException {
        if (connection.isClosed()) return;
        this.connection.close();
    }
}
