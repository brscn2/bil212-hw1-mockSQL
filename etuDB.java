import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class etuDB {
    private DoublyLinkedList<Table> tableList;

    public etuDB() {
        tableList = new DoublyLinkedList<Table>();
    }

    public void createTable(String createTableCommand) throws IllegalArgumentException {
        String[] querySplit = null;
        String filePath = null;
        int indexOfLastSlash;
        int indexOfLastDot;
        String fileExtension = null;
        String tableName = null;
        try {
            querySplit = createTableCommand.split(" ");
            filePath = querySplit[querySplit.length - 1];
            indexOfLastSlash = filePath.lastIndexOf("/");
            indexOfLastDot = filePath.lastIndexOf(".");
            fileExtension = filePath.substring(indexOfLastDot + 1);
            tableName = filePath.substring(indexOfLastSlash + 1, indexOfLastDot);
        } catch (Exception e) {
            System.out.println("Problem encountered while parsing the query.");
            return;
        }

        if (!fileExtension.equals("csv")) {
            System.out.println("File extension must be csv.");
            return;
        }

        Scanner fileInput = null;
        try {
            fileInput = new Scanner(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            System.out.println("Problem encountered while initializing file input stream.");
            return;
        }

        String[] columnNames = fileInput.nextLine().split(",");

        Iterator<Table> it = tableList.iterator();
        // Check if a table with the same name exists, drops the old table if it already
        // exists.
        if (tableList.size() > 0) {
            while (it.hasNext()) {
                Table currentTable = it.next();
                String name = currentTable.getTableName();
                if (name.equals(tableName)) {
                    it.remove();
                }
            }
        }

        tableList.addLast(new Table(fileInput, tableName, columnNames));
        querySplit = null; // Aid garbage collection
    }

    public String[][] query(String query) throws IllegalArgumentException {
        String[] querySplit = null;
        String[] columnQuery = null;
        String filterColumn = null;
        String filterValue = null;
        String tableNameQuery = null;
        Table queryTable = null;

        if(query.lastIndexOf(";") != query.length() - 1) {
            System.out.println("Problem encountered while parsing the query. (No semicolon at the end)");
            return null;
        }
        try {
            querySplit = query.substring(0, query.length() - 1).split(" ");
            columnQuery = null;
            filterColumn = null;
            filterValue = null;
            tableNameQuery = querySplit[3];
            queryTable = null;
        } catch (Exception e) {
            System.out.println("Problem encountered while parsing the query.");
            return null;
        }

        // Find the table mentioned. If it doesn't exist ignore the query.
        Iterator<Table> it = tableList.iterator();
        while (it.hasNext()) {
            Table currentTable = it.next();
            if (currentTable.getTableName().equals(tableNameQuery)) {
                queryTable = currentTable;
            }
        }
        if (queryTable == null) {
            System.out.println("This table does not exist.");
            return null;
        }

        // Initialize which columns are requested in the query.
        if (querySplit[1].contains(",")) {
            columnQuery = querySplit[1].split(",");
        } else if (querySplit[1].equals("*")) {
            columnQuery = queryTable.getColumnNames();
        } else {
            columnQuery = new String[1];
            columnQuery[0] = querySplit[1];
        }

        // Initialize column filter and value if there is a filter.
        if (querySplit.length > 4) {
            try {
                String[] filter = querySplit[querySplit.length - 1].split("=");
                filterColumn = filter[0];
                filterValue = filter[1];
            } catch(Exception e) {
                System.out.println("Problem encountered while parsing the query.");
                return null;
            }
        }

        String[][] listedColumns = queryTable.getListColumns(columnQuery, filterColumn, filterValue);

        // Print the list to the screen.
        if (listedColumns == null) {
            System.out.println("Query is not valid for the chosen table.");
            return null;
        }
        return listedColumns;
    }

    public String printSchema(String tableName) {
        Iterator<Table> it = tableList.iterator();
        Table queryTable = null;
        while (it.hasNext()) {
            Table currentTable = it.next();
            if (currentTable.getTableName().equals(tableName)) {
                queryTable = currentTable;
            }
        }

        if (queryTable == null) {
            System.out.println("Table is not found.");
            return null;
        } else {
            StringBuilder sb = new StringBuilder("");
            String[] columns = queryTable.getColumnNames();
            for (String s : columns) {
                sb.append(s);
                sb.append(" ");
            }
            return sb.toString();
        }
    }

    public void commandPrompt() {
        Scanner keyboard = new Scanner(System.in);
        String command = "";
        while (true) {
            System.out.print("etuDB>>");
            command = keyboard.nextLine();
            if (command.contains("CREATE")) {
                createTable(command);
            } else if (command.contains("SELECT")) {
                String[][] answer = query(command);
                if (answer != null) {
                    for(int i = 0; i < answer[0].length; i++) {
                        for(int j = 0; j < answer.length; j++) {
                            System.out.print(answer[j][i] + " ");
                        }
                        System.out.println();
                    }
                }
            } else if (command.toLowerCase().trim().equals("exit")) {
                System.exit(0);
            } else {
                String schema = printSchema(command);
                if(schema != null) {
                    System.out.println(schema);
                    Iterator<Table> tableIterator = tableList.iterator();
                    Table desiredTable = null;
                    while(tableIterator.hasNext()) {
                        Table curr = tableIterator.next();
                        if(command.equals(curr.getTableName())) {
                            desiredTable = curr;
                        }
                    }
                    System.out.println(desiredTable.toString());
                }
            }
        }
    }
}
