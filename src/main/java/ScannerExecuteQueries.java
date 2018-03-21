import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ScannerExecuteQueries extends DBConnect{

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

    public void insertØvelseGruppe(String navn) throws SQLException {
        connect();
        PreparedStatement preparedStatement = conn.prepareStatement("insert into øvelsegruppe(navn) values (?)");
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

    public ResultSet getNLastTreningsØkterMedNotat(int n) throws SQLException {
        connect();
        PreparedStatement preparedStatement  = conn.prepareStatement("select timestamp, varighet, informasjon, personlig_form, treningsformål, treningsopplevelse from treningsøkt left join notat on treningsøkt.id = notat.treningsøktID order by timestamp desc limit ?");
        preparedStatement.setInt(1,n);
        ResultSet rs = preparedStatement.executeQuery();
        return rs;
    }

    public ResultSet getØvelseResultatLogIInterval(String øvelse, String start, String slutt) throws SQLException {
        connect();
        PreparedStatement preparedStatement = conn.prepareStatement("select informasjon from treningsøkt left join treningsøktutførerøvelse on treningsøkt.id = treningsøktutførerøvelse.treningsøktID left join øvelse on treningsøktutførerøvelse.øvelseID = øvelse.id where navn = ? and timestamp > ? and timestamp < ?");
        preparedStatement.setString(1, øvelse);
        preparedStatement.setString(2, start);
        preparedStatement.setString(3, slutt);
        ResultSet rs = preparedStatement.executeQuery();
        return rs;
    }

    public ResultSet getØvelserISammeGruppe(String navn) throws SQLException {
        connect();
        PreparedStatement preparedStatement = conn.prepareStatement("select øvelse.navn AS navn from øvelsegruppe left join øvelseigruppe on øvelsegruppe.id = øvelseigruppe.gruppeid left join øvelse on øvelse.id = øvelseigruppe.øvelseid where øvelsegruppe.navn = ?");
        preparedStatement.setString(1, navn);
        ResultSet rs = preparedStatement.executeQuery();
        return rs;
    }

    public ResultSet getGjennomsnitt(String start, String slutt) throws SQLException{
        connect();
        PreparedStatement preparedStatement = conn.prepareStatement("select avg(kilo) from øvelse left join treningsøktutførerøvelse on treningsøktutførerøvelse.øvelseID = øvelse.id left join treningsøkt on treningsøktutførerøvelse.treningsøktid = treningsøkt.id where timestamp > ? and timestamp < ?");
        preparedStatement.setString(1,start);
        preparedStatement.setString(2, slutt);
        ResultSet rs = preparedStatement.executeQuery();
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
