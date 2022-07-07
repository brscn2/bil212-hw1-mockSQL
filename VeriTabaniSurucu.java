import java.util.Currency;

public class VeriTabaniSurucu {

    public static void main(String[] args) {
        etuDB veriTabanim = new etuDB();

        // Type "exit" in any case to terminate the program.
        veriTabanim.commandPrompt();

        // veriTabanim.createTable("CREATE TABLE FROM /home/userLogs.csv");
        // veriTabanim.printSchema("userLogs");
        // veriTabanim.query("SELECT first_name FROM userLogs;");
        // veriTabanim.query("SELECT id,first_name,last_name,email FROM userLogs;");
        // veriTabanim.query("SELECT * FROM userLogs;");
        // long start = System.currentTimeMillis();
        // veriTabanim.query("SELECT email,first_name FROM userLogs WHERE id=10321;");
        // long end = System.currentTimeMillis();
        // long timeSpent = end - start;
        // System.out.println(timeSpent);
        // start = System.currentTimeMillis();
        // veriTabanim.query("SELECT email FROM userLogs WHERE first_name=Elfie;");
        // end = System.currentTimeMillis();
        // timeSpent = end - start;
        // System.out.println(timeSpent);

        // veriTabanim.createTable("sample.txt");
        // veriTabanim.printSchema("sample");
        // veriTabanim.query("SELECT thirdColumn FROM sample;" );
        // veriTabanim.query("SELECT Columns FROM userLogs;" );
        // veriTabanim.query("SELECT * FROM sample;" );
        // veriTabanim.query("SELECT fourthColumn FROM sample WHERE secondColumn=value;");

    }

}
