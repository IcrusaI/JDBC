package jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final String protocol = "jdbc:mariadb";
    private final String myDriver = "org.mariadb.jdbc.Driver";
    private final String host = "194.147.34.112:3306";
    private final String tableName = "IcrusaI_java_web";

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

    public List<Book> getBooks() throws SQLException, ClassNotFoundException {
        ResultSet rs = getQuery("SELECT * FROM `books`");

        List<Book> books = new ArrayList<Book>();

        while (rs.next()) {
            books.add(createBookModel(rs));
        }

        return books;
    }

    public Book getBook(String id) throws Exception {
        ResultSet rs = getQuery("SELECT * FROM `books` WHERE `id` = '" + id + "'");
        if (rs.next()) {
            return createBookModel(rs);
        } else {
            throw new Exception("Не найдено");
        }
    }

    public void deleteBook(String id) throws SQLException, ClassNotFoundException {
        getQuery("DELETE FROM `books` WHERE `id` = " + id);
    }

    public void createBook(Book book) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO `books` (`id`, `title`, `pages`) VALUES (" + book.getId() + ", '" + book.getTitle() + "', '" + book.getPages() + "')";

        getQuery(query);
    }

    public void modifyBook(String id, Book book) throws Exception {
        String query = "UPDATE `books` SET `pages` = '" + book.getPages() + "', `title` = '" + book.getTitle() + "', `id` = '" + book.getId() + "' WHERE `id` = " + id;

        getQuery(query);
    }

    private Book createBookModel(ResultSet rs) throws SQLException {
        Book book = new Book();

        book.setId(rs.getInt("id"));
        book.setTitle(rs.getString("title"));
        book.setPages(rs.getInt("pages"));

        return book;
    }
}
