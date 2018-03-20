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
            int n = Integer.parseInt(args[1]);
            ArrayList<String> result = eq.getLastNtreningsøkt(n);
            System.out.println(result);
        }
        else if(command.equals("nyttAparat")){
            String navn = args[1];
            String bruksanvisning = args[2];
            eq.insertApparat(navn, bruksanvisning);
        }
        else{
            System.out.println("Invalid command");
        }

        /*Scanner scanner = new Scanner(System.in);
        ExecuteQueries a = new ExecuteQueries();
        ResultSet rs = null;

        //a.insertApparat("BeinløfteMaskin", "Løftbeinet");
        String choice;
        // UI
        System.out.println("Available commands:\ninsertApparat, insertTreningsøkt, insertØvelse, insertNotat");
        while(true) {
            choice = scanner.next();

            /**
             * InsertApparat
             *
            if (choice.equals("insertApparat")) {
                System.out.print("Navn: ");
                String navn = scanner.next();
                System.out.print("tall: ");
                int test1 = scanner.nextInt();
                System.out.print("Bruksanvisning: ");
                String bruksanvisning = scanner.next();
                a.insertApparat(navn, bruksanvisning);
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
             *  InsertTreningsøkt
             *
            if(choice.equals("insertTreningsøkt")) {
                System.out.print("Varighet: ");
                int varighet = scanner.nextInt();scanner.nextLine();
                System.out.print("Informasjon: ");
                String informasjon = scanner.next();scanner.nextLine();
                System.out.print("Personlig form: ");
                int form = scanner.nextInt();
                System.out.print("Prestasjon: ");
                int prestasjon = scanner.nextInt();scanner.nextLine();
                a.insertTreningsøkt(varighet, informasjon, form, prestasjon);
            }

               InsertØvelse
            *
            if(choice.equals("insertØvelse")) {
                System.out.print("Navn: ");
                String navn = scanner.next();
                System.out.print("Type(fastmontert eller fri): ");
                String type = scanner.next();
                if(type.equals("fri")) {
                    System.out.print("Beskrivelse: ");
                    String beskrivelse= scanner.next();
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

             *  InsertNotat
             */
        /*
            if(choice.equals("insertNotat")) {
                System.out.print("Treningsformål: ");
                String treningsformål = scanner.next();
                System.out.print("Treningsopplevelse: ");
                String treningsopplevelse = scanner.next();
                System.out.print("TreningsøktID: ");
                int id = scanner.nextInt();scanner.nextLine();
                a.insertNotat(treningsformål,treningsopplevelse,id);
            }
        }*/
    }
}
