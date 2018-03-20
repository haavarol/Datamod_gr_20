import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ScannerMain {

    public static void main(String args[]) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        ExecuteQueries a = new ExecuteQueries();
        ResultSet rs = null;

        //a.insertApparat("BeinløfteMaskin", "Løftbeinet");
        String choice;
        // UI
        System.out.println("Available commands:\ninsertApparat, insertTreningsøkt, insertØvelse, insertØvelse, insertNotat, insertTreningMedNotat, insertØvelseGruppe\ngetAllApparat");
        while(true) {
            choice = scanner.next();

            /**
             * InsertApparat
             */
            if (choice.equals("insertApparat")) {
                System.out.print("Navn: ");
                String navn = readLine(scanner);
                System.out.print("Bruksanvisning: ");
                String bruksanvisning = readLine(scanner);
                a.insertApparat(navn, bruksanvisning);
            }
            /**
             *  InsertTreningsøkt
             */
            if(choice.equals("insertTreningsøkt")) {
                System.out.print("Varighet: ");
                int varighet = scanner.nextInt();scanner.nextLine();
                System.out.print("Informasjon: ");
                String informasjon = readLine(scanner);
                System.out.print("Personlig form: ");
                int form = scanner.nextInt();scanner.nextLine();
                System.out.print("Prestasjon: ");
                int prestasjon = scanner.nextInt();scanner.nextLine();
                a.insertTreningsøkt(varighet, informasjon, form, prestasjon);
            }
            /**
             *  InsertØvelse
             */
            if(choice.equals("insertØvelse")) {
                System.out.print("Navn: ");
                String navn = readLine(scanner);
                System.out.print("Type(fastmontert eller fri): ");
                String type = scanner.next();
                if(type.equals("fri")) {
                    System.out.print("Beskrivelse: ");
                    String beskrivelse= readLine(scanner);
                    a.insertFriØvelse(navn,beskrivelse,type);
                }
                else {
                    type = "fast";
                    System.out.print("Kilo: ");
                    int kilo = scanner.nextInt();scanner.nextLine();
                    System.out.print("Sett: ");
                    int sett = scanner.nextInt();scanner.nextLine();
                    a.insertFastØvelse(navn,kilo,sett,type);
                }
            }
            /**
             *  InsertNotat
             */
            if(choice.equals("insertNotat")) {
                System.out.print("Treningsformål: ");
                String treningsformål = readLine(scanner);
                System.out.print("Treningsopplevelse: ");
                String treningsopplevelse = readLine(scanner);
                System.out.print("TreningsøktID: ");
                int id = scanner.nextInt();scanner.nextLine();
                a.insertNotat(treningsformål,treningsopplevelse,id);
            }
            /**
             *  InsertTreningMedNotat
             */
            if(choice.equals("insertTreningMedNotat")) {
                System.out.print("Varighet: ");
                int varighet = scanner.nextInt();scanner.nextLine();
                System.out.print("Informasjon: ");
                String informasjon = readLine(scanner);
                System.out.print("Personlig form: ");
                int form = scanner.nextInt();
                System.out.print("Prestasjon: ");
                int prestasjon = scanner.nextInt();scanner.nextLine();
                System.out.print("Treningsformål: ");
                String treningsformål = readLine(scanner);
                System.out.print("Treningsopplevelse: ");
                String treningsopplevelse = readLine(scanner);
                a.insertTreningMedNotat(varighet, informasjon, form, prestasjon, treningsformål, treningsopplevelse);
            }
            /**
             *  InsertØvelseGruppe
             */
            if(choice.equals("insertØvelseGruppe")){
                System.out.print("Navn: ");
                String navn = readLine(scanner);
                a.insertøvelseGrupper(navn);
            }
            /**
             *  getAllApparat
             */
            if(choice.equals("getAllApparat")) {
                rs = a.getAllApparat();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String first = rs.getString("navn");
                    String last = rs.getString("bruksanvisning");

                    //Display values
                    System.out.print("ID: " + id);
                    System.out.print(", navn: " + first);
                    System.out.println(", bruksanvisning: " + last);
                }
            }
            /**
             *  getNLastTreningsØkterMedNotat
             */
            if(choice.equals("getNLastTreningsØkterMedNotat")) {
                rs = a.getNLastTreningsØkterMedNotat();
                /* while(rs.next()) {

                 */}
        }
    }

    // Lånt fra http://www.gubatron.com/blog/2017/01/21/codejava-scanner-read-a-full-line/
    // Kode for å enkelt lese en hel setning med Scanner
    public static String readLine(Scanner scanner) {
        Pattern oldDelimiter = scanner.delimiter();
        scanner.useDelimiter("\\r\\n|[\\n\\x0B\\x0C\\r\\u0085\\u2028\\u2029]");
        String r = scanner.next();
        scanner.useDelimiter(oldDelimiter);
        return r;
    }
}