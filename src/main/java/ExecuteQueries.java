import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExecuteQueries extends DBConnect{

    /**
     *  INSERTS
     */

    public void insertApparat(String navn, String bruksanvisning) throws SQLException {
        connect();
        PreparedStatement statement1 = conn.prepareStatement("insert into apparat(navn, bruksanvisning) values (?, ?)");
        statement1.setString(1, navn);
        statement1.setString(2, bruksanvisning);
        statement1.executeUpdate();
        System.out.println("insert into apparat(navn, bruksanvisning) values "+ navn + ", "+ bruksanvisning);
        disconnect();
    }

    public void insertTreningsøkt(int varighet, String informasjon, int personlig_form, int prestasjon) throws SQLException {
        connect();
        PreparedStatement statement1 = conn.prepareStatement("INSERT into treningsøkt(varighet, informasjon, personlig_form, prestasjon) VALUES (?,?,?,?) ");
        statement1.setInt(1, varighet);
        statement1.setString(2,informasjon);
        statement1.setInt(3,personlig_form);
        statement1.setInt(4,prestasjon);
        statement1.executeUpdate();
        System.out.println("insert into treningsøkt values "+varighet+", "+informasjon+", "+personlig_form+", "+prestasjon);
        disconnect();
    }

    public void insertTreningMedNotat(int varighet, String informasjon, int personlig_form, int prestasjon, String treningsformål, String treningsopplevelse) throws SQLException{
        connect();
        insertTreningsøkt(varighet,informasjon,personlig_form,prestasjon);
        ResultSet allTreningsøkt = conn.createStatement().executeQuery("SELECT FIRST from treningsøkt ORDER BY id DESC");
        int id = 1;
        while (allTreningsøkt.next()) {
            id = allTreningsøkt.getInt("id");
        }
        insertNotat(treningsformål,treningsopplevelse, id);
    }

    public void insertFriØvelse(String navn, String beskrivelse, String type) throws SQLException {
        connect();
        PreparedStatement statement1 = conn.prepareStatement("INSERT into øvelse(navn, beskrivelse, type) values (?,?,?)");
        statement1.setString(1, navn);
        statement1.setString(2, beskrivelse);
        statement1.setString(3, "fri");
        statement1.executeUpdate();
        System.out.println("insert into øvelse values "+navn+", "+beskrivelse+", "+type);
        disconnect();
    }

    public void insertFastØvelse(String navn, int kilo, int sett, String type) throws SQLException {
        connect();
        PreparedStatement statement1 = conn.prepareStatement("INSERT into øvelse(navn, kilo, sett, type) values (?,?,?,?)");
        statement1.setString(1, navn);
        statement1.setInt(2, kilo);
        statement1.setInt(3, sett);
        statement1.setString(4, "apparat");
        statement1.executeUpdate();
        System.out.println("insert into øvelse values "+navn+", "+kilo+", "+sett+", "+type);
        disconnect();
    }

    public void insertNotat(String treningsformål, String treningsopplevelse, int treningsøktID) throws SQLException {
        connect();
        PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO notat(treningsformål, treningsopplevelse, treningsøktID) VALUES (?,?,?) ");
        preparedStatement.setString(1,treningsformål);
        preparedStatement.setString(2, treningsopplevelse);
        preparedStatement.setInt(3,treningsøktID);
        preparedStatement.executeUpdate();
        System.out.println("insert into notat(treningsformål, treningsopplevelse, treningsøktID) VALUES "+treningsformål+", "+treningsopplevelse+", "+treningsøktID);
    }

    public void insertøvelseGrupper(String navn) throws SQLException {
        connect();
        PreparedStatement preparedStatement = conn.prepareStatement("insert into øvelsegruppe(navn) values ?");
        preparedStatement.setString(1,navn);
        preparedStatement.executeUpdate();
    }

    /**
     *  GETTERS
     */

    public ResultSet getAllApparat() throws SQLException {
        ResultSet rs = Select("SELECT * from apparat");
        return rs;
    }

    public ArrayList<String> getLastNtreningsøkt(int n)throws SQLException{
        connect();
        PreparedStatement preparedStatement = conn.prepareStatement("select dato, tidspunkt, varighet, informasjon, personlig_form, treningsformål, treningsopplevelse from treningsøkt left join notat on treningsøkt.id = notat.treningsøktID order by dato desc, tidspunkt desc limit ?");
        preparedStatement.setInt(1, n);
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
    public ResultSet getNLastTreningsØkterMedNotat() throws SQLException {
        ResultSet rs = Select("select dato, tidspunkt, varighet, informasjon, personlig_form, treningsformål, treningsopplevelse from treningsøkt left join notat on treningsøkt.id = notat.treningsøktID order by dato desc, tidspunkt desc limit ?");
        return rs;
    }

    public ResultSet øvelseResultatLogIInterval() throws SQLException {
        ResultSet rs = Select("select informasjon from treningsøkt left join treningsøktutførerøvelse on treningsøkt.id = treningsøktutførerøvelse.treningsøktID left join øvelse on treningsøktutførerøvelse.øvelseID = øvelse.id where navn = ? and dato > ? and dato < ?");
        return rs;
    }

    public ResultSet finneØvelserISammeGruppe() throws SQLException {
        ResultSet rs = Select("select øvelse.navn from øvelsegruppe left join øvelseigruppe on øvelsegruppe.id = øvelseigruppe.gruppeid left join øvelse on øvelse.id = øvelseigruppe.øvelseid where øvelsegruppe.navn = ?");
        return rs;
    }

    public ResultSet finneGjennomsnitt() throws SQLException{
        ResultSet rs = Select("select avg(kilo) from øvelse left join treningsøktutførerøvelse on treningsøktutførerøvelse.øvelseID = øvelse.id left join treningsøkt on treningsøktutførerøvelse.treningsøktid = treningsøkt.id where dato > ? and dato < ?");
        return rs;
    }

    public ResultSet Select(String Select) throws SQLException {
        connect();
        ResultSet rs = null;
        try {
            rs = conn.createStatement().executeQuery(Select);
        } catch (SQLException e){
            e.printStackTrace();
        }
        if(rs != null) {
            return rs;
        }
        return null;
    }
}
