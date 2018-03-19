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

    public void insertØvelse(String navn, String kilo, String sett, String beskrivelse, String type) throws SQLException {
        connect();
        PreparedStatement statement1 = conn.prepareStatement("INSERT into øvelse(navn, kilo, sett, beskrivelse, type) values (?,?,?,?,?)");
        statement1.setString(1, navn);
        statement1.setString(2, kilo);
        statement1.setString(3, sett);
        statement1.setString(4, beskrivelse);
        statement1.setString(5, type);
        statement1.executeUpdate();
        disconnect();

    }

    public void insertNotat(String treningsformål, String treningsopplevelse, int treningsøktID) throws SQLException {
        connect();
        PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO notat(treningsformål, treningsopplevelse, treningsøktID) VALUES (?,?,?) ");
        preparedStatement.setString(1,treningsformål);
        preparedStatement.setString(2, treningsopplevelse);
        preparedStatement.setInt(3,treningsøktID);
    }
}
