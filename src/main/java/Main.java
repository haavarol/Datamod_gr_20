import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String args[]) throws SQLException {
        String command = args[0];
        ExecuteQueries eq = new ExecuteQueries();
        if (command.equals("avg")){
            String start = args[1];
            String slutt = args[2];
            ArrayList<String> result = eq.getGjennomsnitt(start, slutt);
            System.out.println(result);
        }
        else if(command.equals("hentØvelseISammeGruppe")){
            String navn = args[1];
            ArrayList<String> result = eq.getØvelserISammeGruppe(navn);
                System.out.println(result);
        }
        else if(command.equals("skapØvelseGruppe")){
            String navn = args[1];
            eq.insertØvelsegruppe(navn);
        }
        else if(command.equals("hentØvelseISammeGruppe")){
            String navn = args[1];
            ArrayList<String> result = eq.getØvelserISammeGruppe(navn);
            System.out.println(result);
        }
        else if(command.equals("hentResultatlogForØvelse")){
            String øvelse = args[1];
            String start = args[2];
            String slutt = args[3];
            ArrayList<String> result = eq.getResultatlogMedInterval(øvelse, start, slutt);
            System.out.println(result);
        }
        else if(command.equals("hentNSiste")){
            String n = args[1];
            ArrayList<String> result = eq.getLastNtreningsøkt(n);
            System.out.println(result);
        }
        else{
            System.out.println("Invalid command");
        }

        /*Scanner scanner = new Scanner(System.in);
        ExecuteQueries a = new ExecuteQueries();
        ResultSet rs = null;

        //a.insertApparat("BeinløfteMaskin", "Løftbeinet");

        // UI
        System.out.println("Available commands:\n insertApparat");
        if(scanner.next().equals("insertApparat")){
            System.out.println("Navn: ");
            String navn = scanner.next();
            System.out.println("Bruksanvisning: ");
            String bruksanvisning = scanner.next();
            a.insertApparat(navn, bruksanvisning);
            rs = a.showAllApparat();
            while(rs.next()) {
                int id  = rs.getInt("id");
                String first = rs.getString("navn");
                String last = rs.getString("bruksanvisning");

                //Display values
                System.out.print("ID: " + id);
                System.out.print(", navn: " + first);
                System.out.println(", bruksanvisning: " + last);
            }
        }*/
    }
}
