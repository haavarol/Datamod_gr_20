import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ExecuteQueries extends DBConnect{
    public void InsertApparat(String navn, String bruksanvisning) throws SQLException {
        connect();
        PreparedStatement statement1 = conn.prepareStatement("insert into apparat(navn, bruksanvisning) values (?, ?)");
        statement1.setString(1, navn);
        statement1.setString(2, bruksanvisning);
        statement1.executeUpdate();
        disconnect();
    }

    public void insertTreningsøkt(String dato, String tidspunkt, String varighet, String informasjon, String personlig_form, String prestasjon) throws SQLException {
        connect();
        PreparedStatement statement1 = conn.prepareStatement("INSERT into treningsøkt(dato, tidspunkt, varighet, informasjon, personlig_form, prestasjon) VALUES (?,?,?,?,?,?,?) ");
        statement1.setString(1, dato);
        statement1.setString(2, tidspunkt);
        statement1.setString(3, varighet);
        statement1.setString(4,informasjon);
        statement1.setString(5,personlig_form);
        statement1.setString(6,prestasjon);
        statement1.executeUpdate();
        disconnect();

    }

    public void insertØvelse() {
        
    }
}
