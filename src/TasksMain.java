import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;


import java.sql.SQLException;


public class TasksMain {

    // JDBC URL, username and password of MySQL server
    public static Scanner scan = new Scanner(System.in);
    private static final String url = "jdbc:mysql://localhost:3306/contactdb"
            +
            "?verifyServerCertificate=false" +
            "&useSSL=false" +
            "&requireSSL=false" +
            "&useLegacyDatetimeCode=false" +
            "&amp" +
            "&serverTimezone=UTC" +
            "&useUnicode=true" +
            "&characterEncoding=utf8";


    private static final String user = "root";
    private static final String password = "123";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public static void main(String[] args) throws IOException, ParseException {
        menu();
        int s = inputNumber();
        while (s != 0) {
            switch (s) {
                case 1:
                    /*TasksMain m = new TasksMain();
                    m.printContacts();*/
                    printContacts();
                    menu();
                    s = inputNumber();
                    break;
                case 2:
                    printJobs();
                    menu();
                    s = inputNumber();
                    break;
                case 3:
                    addContact();
                    menu();
                    s = inputNumber();
                    break;
                case 4:
                    addJob();
                    menu();
                    s = inputNumber();
                    break;
                case 5:
                    deleteContact();
                    menu();
                    s = inputNumber();
                    break;
                case 6:
                    deleteJob();
                    s = inputNumber();
                    break;

                case 7:
                    printJobsSummary();
                    s = inputNumber();
                    break;
                default:
                    System.out.println("Enter the correct value [0 -6] !!!");
                    menu();
                    s = inputNumber();
                    break;

            }
        }
    }


    public static int inputNumber() {
        while (!scan.hasNextInt()) {
            System.out.println("Enter correct symbol!");
            scan.next();
        }
        int s = scan.nextInt();
        return s;
    }

    private static void printContacts() {

        String query = "select * from contacts";

        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();

            // executing SELECT query
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String count = rs.getString("contact_id") + ": " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4);
                System.out.println(count);
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try {
                con.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                stmt.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                rs.close();
            } catch (SQLException se) { /*can't do anything */ }
        }


    }

    public static void menu() {
        System.out.println("Enter\n" +
                "1 - Print CONTACTS\n" +
                "2 - Print JOBS\n" +
                "3 - ADD CONTACTS\n" +
                "4 - ADD JOBS\n" +
                "5 - Delete CONTACT\n" +
                "6 - Delete JOB\n" +
                "7 - Job report SUMMARY\n" +
                "0 - Exit");

    }

    public static void printJobs() {

        String query = "SELECT * FROM JOBS, CONTACTS WHERE JOBS.EMPLOYER=CONTACTS.CONTACT_ID";

        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();

            // executing SELECT query
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String count = rs.getString("jobs_id") + ": Дата: " + rs.getString(2) + " Клиент: " + rs.getString("NAME") + " Часов отработано: " + rs.getString(4) + " Сумма: " + rs.getString(5);
                System.out.println(count);
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try {
                con.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                stmt.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                rs.close();
            } catch (SQLException se) { /*can't do anything */ }
        }


    }

    public static void addContact() {

        scan.nextLine();
        System.out.println("Enter name of new contact");
        String name1 = scan.nextLine();
        System.out.println("Enter phone of new contact");
        String phone = scan.nextLine();
        System.out.println("Enter email of new contact");
        String email = scan.nextLine();

        //String query = "INSERT INTO CONTACTS (NAME,PHONE,EMAIL) \"\n" +
        //        "                    + \"VALUES ('\" + name1 + \"', '\" + phone + \"', '\" + email + \"' );";

        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();

            String sql = "INSERT INTO CONTACTS (NAME,PHONE,EMAIL) VALUES ('" + name1 + "', '" + phone + "', '" + email + "' )";
            stmt.executeUpdate(sql);


        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try {
                con.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                stmt.close();
            } catch (SQLException se) { /*can't do anything */ }

        }
        System.out.println("Records created successfully");


    }

    public static void addJob() {

        scan.nextLine();
        System.out.println("Enter date");
        String date = scan.nextLine();
        System.out.println("Enter code of employer");
        printContacts();
        int emp = scan.nextInt();
        System.out.println("Enter worked hours");
        int hours = scan.nextInt();
        System.out.println("Enter received money");
        int money = scan.nextInt();


        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();

            String sql = "INSERT INTO JOBS (DATA ,EMPLOYER,HOURS, MONEY)" +
                    "VALUES ('" + date + "', '" + emp + "', '" + hours + "', '" + money + "' );";
            stmt.executeUpdate(sql);


        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try {
                con.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                stmt.close();
            } catch (SQLException se) { /*can't do anything */ }

        }
        System.out.println("Records created successfully");

    }

    public static void deleteContact() {

        printContacts();
        scan.nextLine();
        System.out.println("Select CONTACT to delete");
        int s = scan.nextInt();

        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();

            String sql = "DELETE from CONTACTS where CONTACT_ID = '" + s + "';";
            stmt.executeUpdate(sql);


        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try {
                con.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                stmt.close();
            } catch (SQLException se) { /*can't do anything */ }

        }
        System.out.println("Records created successfully");
    }

    public static void deleteJob() {

        printJobs();
        scan.nextLine();
        System.out.println("Select JOB to delete");
        int s = scan.nextInt();

        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();

            String sql = "DELETE from JOBS where JOBS_ID = '" + s + "';";
            stmt.executeUpdate(sql);


        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try {
                con.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                stmt.close();
            } catch (SQLException se) { /*can't do anything */ }

        }
        System.out.println("Records created successfully");

    }

    public static void printJobsSummary() throws IOException, ParseException {

        scan.nextLine();
        System.out.println("Enter first date YY.MM.DD ");
        String date1 = scan.nextLine();

        /*String tempDate = scan.nextLine();
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        Date date1 = format.parse(tempDate);*/
        System.out.println(date1);

        System.out.println("Enter second date YY.MM.DD");
        String date2 = scan.nextLine();

        /*tempDate = scan.nextLine();
        SimpleDateFormat format1 = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        Date date2 = format.parse(tempDate);*/

        System.out.println("Enter employer");
        String emp = scan.nextLine();

        //String query = "SELECT * FROM JOBS WHERE DATA >=('" + date1 + "') AND DATA <=('" + date2 + "') AND EMPLOYER=(SELECT CONTACT_ID FROM CONTACTS WHERE NAME='" + emp + "')";
        //String query = "SELECT * FROM JOBS WHERE DATA BETWEEN '" + date1 + "' AND '" + date2 + "' AND EMPLOYER=(SELECT CONTACT_ID FROM CONTACTS WHERE NAME='" + emp + "')";
        String query = "SELECT * FROM JOBS WHERE DATA between '" + date1 + "' and '" + date2 + "' AND EMPLOYER=(SELECT CONTACT_ID FROM CONTACTS WHERE NAME='" + emp + "')";
        System.out.println(query);
        try {
            con = DriverManager.getConnection(url, user, password);


            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();

            // executing SELECT query
            rs = stmt.executeQuery(query);

            int sum = 0;
            int time = 0;
            while (rs.next()) {
                String str = rs.getString("jobs_id") + ": Дата: " + rs.getString(2) + " Клиент: " + rs.getString(3) + " Часов отработано: " + rs.getString(4) + " Сумма: " + rs.getString(5);
                System.out.println("Jobs: " + str);
                sum += rs.getInt(5);
                time += rs.getInt(4);
            }


            System.out.println("Time total: " + time + " jours, Money total: " + sum + " rub.");
        } catch (
                SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try {
                con.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                stmt.close();
            } catch (SQLException se) { /*can't do anything */ }
            try {
                rs.close();
            } catch (SQLException se) { /*can't do anything */ }

        }

    }
}