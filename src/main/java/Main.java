import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String args[]) throws SQLException {
        Scanner scanner = new Scanner(System.in);
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
        }
    }
}
