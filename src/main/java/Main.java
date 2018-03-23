import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    public static void main(String args[]) throws SQLException, NoSuchFieldException, IllegalAccessException {
        Scanner scanner = new Scanner(System.in);
        ExecuteQueries a = new ExecuteQueries();
        ResultSet rs = null;
        String choice;
        // UI
        System.out.println("--------------\n" +
                "TRENINGSDAGBOK\n" +
                "--------------\n" +
                "" +
                "Available commands:\n|INSERTS| insertApparat, insertTreningsokt, insertOvelse, insertNotat, insertTreningMedNotat, insertOvelseGruppe\n" +
                "|GETTERS| getAllApparat, getNLastTreningsOkterMedNotat, getOvelseResultatLogIInterval, getOvelserISammeGruppe, getGjennomsnitt");
        while (true) {
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
             *  InsertTreningsokt
             */
            if (choice.equals("insertTreningsøkt")) {
                System.out.print("Varighet: ");
                int varighet = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Informasjon: ");
                String informasjon = readLine(scanner);
                System.out.print("Personlig form: ");
                int form = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Prestasjon: ");
                int prestasjon = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Øvelsenavn: ");
                String øvelse = readLine(scanner);
                scanner.nextLine();
                a.insertTreningsøkt(varighet, informasjon, form, prestasjon, false, "", "", øvelse);
            }
            /**
             *  insertOvelse
             */
            if (choice.equals("insertOvelse")) {
                System.out.print("Navn: ");
                String navn = readLine(scanner);
                System.out.print("Type(fastmontert eller fri): ");
                String type = scanner.next();
                if (type.equals("fri")) {
                    System.out.print("Beskrivelse: ");
                    String beskrivelse = readLine(scanner);
                    a.insertFriØvelse(navn, beskrivelse, type);
                } else {
                    type = "fast";
                    System.out.print("Kilo: ");
                    int kilo = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Sett: ");
                    int sett = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Apparat navn: ");
                    String apparat = readLine(scanner);
                    scanner.nextLine();
                    a.insertFastØvelse(navn, kilo, sett, type, apparat);
                }
            }
            /**
             *  InsertTreningMedNotat
             */
            if (choice.equals("insertTreningMedNotat")) {
                System.out.print("Varighet: ");
                int varighet = scanner.nextInt();
                scanner.nextLine();
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
                System.out.print("Øvelsenavn: ");
                String øvelse = readLine(scanner);
                scanner.nextLine();
                a.insertTreningsøkt(varighet, informasjon, form, prestasjon, true, treningsformål, treningsopplevelse, øvelse);
            }
            /**
             *  insertOvelseGruppe
             */
            if (choice.equals("insertOvelseGruppe")) {
                System.out.print("Navn: ");
                String navn = readLine(scanner);
                a.insertØvelseGruppe(navn);
            }
            /**
             *  getAllApparat
             */
            if (choice.equals("getAllApparat")) {
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
             *  getNLastTreningsOkterMedNotat
             */
            if (choice.equals("getNLastTreningsOkterMedNotat")) {
                System.out.print("Antall økter: ");
                int n = scanner.nextInt();scanner.nextLine();
                rs = a.getNLastTreningsØkterMedNotat(n);
                while (rs.next()) {
                    String time = rs.getString("timestamp");
                    int varighet = rs.getInt("varighet");
                    String info = rs.getString("informasjon");
                    int form = rs.getInt("personlig_form");
                    String formål = rs.getString("treningsformål");
                    String opplevelse = rs.getString("treningsopplevelse");

                    //Display values

                    System.out.print("Dato: " + time.substring(0,time.length()-2)+" ");
                    System.out.print("Varighet: " + varighet);
                    System.out.print(", Informasjon: " + info);
                    System.out.print(", Personlig form: " + form);
                    System.out.print(", Treningsformål: " + formål);
                    System.out.println(", Treningsopplevelse: " + opplevelse);
                }
            }
            if (choice.equals("getOvelseResultatLogIInterval")) {
                System.out.print("Øvelse: ");
                String øvelse = readLine(scanner);
                System.out.println("Oppgi tid på formen <yyyy-mm-dd hh:mm:ss>");
                System.out.print("Start: ");
                String start = readLine(scanner);
                System.out.print("Slutt: ");
                String slutt = readLine(scanner);
                rs = a.getØvelseResultatLogIInterval(øvelse,start,slutt);
                while (rs.next()) {
                    String info = rs.getString("informasjon");

                    //Display values
                    System.out.println("Informasjon: " + info);
                }
            }
            if (choice.equals("getOvelserISammeGruppe")) {
                System.out.print("Navn: ");
                String navn = readLine(scanner);
                rs = a.getØvelserISammeGruppe(navn);
                while(rs.next()) {
                    String øvelsenavn = rs.getString("navn");

                    //Display values
                    System.out.println("Navn: " + øvelsenavn);
                }
            }
            if(choice.equals("getGjennomsnitt")) {
                System.out.print("N siste økter: ");
                int n = scanner.nextInt();scanner.nextLine();
                rs = a.getGjennomsnitt(n);
                while(rs.next()) {
                    int kilo = rs.getInt("avg(kilo)");

                    //Display values
                    System.out.println("Gjennomsnitt: " + kilo);
                }
            }
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
