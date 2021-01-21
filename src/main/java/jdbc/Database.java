package jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final String protocol = "jdbc:mariadb";
    private final String myDriver = "org.mariadb.jdbc.Driver";
    private final String host = "194.147.34.112:3306";
    private final String tableName = "IcrusaI_bank_lend";

    private ResultSet getQuery(String query) throws ClassNotFoundException, SQLException {
        // create our mysql database connection
        String myUrl = protocol + "://" + host + "/" + tableName;
        Class.forName(myDriver);

        Connection conn = DriverManager.getConnection(myUrl, "IcrusaI_IcrusaI", ")E0Meh>[nNk(");

        Statement st = conn.createStatement();

        ResultSet rs = st.executeQuery(query);
        st.close();
        return rs;
    }

    public List<Worker> getWorkers() throws SQLException, ClassNotFoundException {
        ResultSet rs = getQuery("SELECT * FROM `customers`");

        List<Worker> workers = new ArrayList<Worker>();

        while (rs.next()) {
            workers.add(createWorker(rs));
        }

        return workers;
    }

    public List<Worker> getWorkers(String TIN) throws SQLException, ClassNotFoundException {
        ResultSet rs = getQuery("SELECT * FROM `customers` WHERE `TIN` = '" + TIN + "'");

        List<Worker> workers = new ArrayList<Worker>();

        while (rs.next()) {
            workers.add(createWorker(rs));
        }

        return workers;
    }

    private Worker createWorker(ResultSet rs) throws SQLException {
        Worker worker = new Worker();

        worker.setTIN(rs.getInt("TIN"));
        worker.setName(rs.getString("name"));
        worker.setType(rs.getString("type"));
        worker.setRevenue(rs.getInt("revenue"));

        return worker;
    }
}
