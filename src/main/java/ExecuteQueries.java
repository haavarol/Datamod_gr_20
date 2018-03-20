import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExecuteQueries extends DBConnect{
    public void insertApparat(String navn, String bruksanvisning) throws SQLException {
        connect();
        PreparedStatement statement1 = conn.prepareStatement("insert into apparat(navn, bruksanvisning) values (?, ?)");
        statement1.setString(1, navn);
        statement1.setString(2, bruksanvisning);
        statement1.executeUpdate();
        System.out.println("insert into apparat(navn, bruksanvisning) values "+ navn + ", "+ bruksanvisning);
        disconnect();
    }

    public ResultSet showAllApparat() {
        connect();
        ResultSet rs = null;
        try {
            rs = conn.createStatement().executeQuery("SELECT * from apparat");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(rs != null) {
            return rs;
        }
        return null;
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

    public ArrayList<String> getLastNtreningsøkt(String n)throws SQLException{
        connect();
        PreparedStatement preparedStatement = conn.prepareStatement("select dato, tidspunkt, varighet, informasjon, personlig_form, treningsformål, treningsopplevelse from treningsøkt\n" +
                "left\n" +
                "join notat on treningsøkt.id = notat.treningsøktID\n" +
                "\n" +
                "order by dato desc, tidspunkt desc limit ?");
        preparedStatement.setString(1, "3");
        preparedStatement.execute();
        ResultSet rs = preparedStatement.getResultSet();
        ArrayList<String> result = new ArrayList<String>();
        while (rs.next()){
            String resultString = new String();
            resultString += "Dato: " + rs.getString("dato") + " | ";
            resultString += "Tidspunks: " + rs.getString("tidspunkt") + " | ";
            resultString += "Varighet: " + rs.getString("varighet") + " | ";
            resultString += "Informasjon: " + rs.getString("informasjon") + " | ";
            resultString += "Personlig form: " + rs.getString("personlig_form") + " | ";
            resultString += "Treningsformål: " + rs.getString("treningsformål") + " | ";
            resultString += "Treningsopplevelse: " + rs.getString("treningsopplevelse") + " | ";
            result.add(resultString);
        }
        disconnect();
        return result;
    }

    public ArrayList<String> getResultatlogMedInterval(String øvelse, String start, String slutt)throws SQLException{
        connect();
        PreparedStatement preparedStatement = conn.prepareStatement("select informasjon from treningsøkt\n" +
                "\n" +
                "left join treningsøktutførerøvelse on treningsøkt.id = treningsøktutførerøvelse.treningsøktID\n" +
                "\n" +
                "left join øvelse on treningsøktutførerøvelse.øvelseID = øvelse.id\n" +
                "where navn = ? and dato > ? and dato < ?");
        preparedStatement.setString(1, øvelse);
        preparedStatement.setString(2, start);
        preparedStatement.setString(3, slutt);
        preparedStatement.execute();
        ResultSet rs = preparedStatement.getResultSet();
        ArrayList<String> result = new ArrayList<String>();
        while (rs.next()){
            String resultString = new String();
            resultString += "Informasjon: " + rs.getString("informasjon");
            result.add(resultString);
        }
        disconnect();
        return result;
    }

    public void insertØvelsegruppe(String navn)throws SQLException{
        connect();
        PreparedStatement preparedStatement = conn.prepareStatement("insert into øvelsegruppe(navn) values (?)");
        preparedStatement.setString(1, navn);
        preparedStatement.execute();
        disconnect();
    }

    public ArrayList<String> getØvelserISammeGruppe(String navn) throws SQLException{
        connect();
        PreparedStatement preparedStatement = conn.prepareStatement("select øvelse.navn from øvelsegruppe\n" +
                "\n" +
                "left join øvelseigruppe on øvelsegruppe.id = øvelseigruppe.gruppeid\n" +
                "\n" +
                "left join øvelse on øvelse.id = øvelseigruppe.øvelseid\n" +
                "\n" +
                "where øvelsegruppe.navn = ?");
        preparedStatement.setString(1, navn);
        preparedStatement.execute();
        ResultSet rs = preparedStatement.getResultSet();
        ArrayList<String> result = new ArrayList<String>();
        while (rs.next()){
            String resultString = new String();
            resultString += "Navn: " + rs.getString("navn");
            result.add(resultString);
        }
        disconnect();
        return result;
    }

    public ArrayList<String> getGjennomsnitt(String start, String slutt)throws SQLException{
        connect();
        PreparedStatement preparedStatement = conn.prepareStatement("select avg(kilo) from øvelse\n" +
                "\n" +
                "left join treningsøktutførerøvelse on treningsøktutførerøvelse.øvelseID = øvelse.id\n" +
                "\n" +
                "left join treningsøkt on treningsøktutførerøvelse.treningsøktid = treningsøkt.id\n" +
                "\n" +
                "where dato > ? and dato < ?\n");
        preparedStatement.setString(1, start);
        preparedStatement.setString(2, slutt);
        preparedStatement.execute();
        ResultSet rs = preparedStatement.getResultSet();
        ArrayList<String> result = new ArrayList<String>();
        while (rs.next()){
            String resultString = new String();
            resultString += "Avg: " + rs.getString("avg(kilo)");
            result.add(resultString);
        }
        disconnect();
        return result;
    }
}
