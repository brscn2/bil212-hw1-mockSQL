import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class Table {
    private String tableName = "";
    private String[] columns;
    private SinglyLinkedList<Row> rowList;
    private LinkedBinaryTree indexTree;

    public Table(Scanner inputStream, String name, String[] columnNames) {
        tableName = name;
        columns = columnNames;
        rowList = new SinglyLinkedList<Row>();
        indexTree = new LinkedBinaryTree();
        String[] elements = null;
        while (inputStream.hasNextLine()) {
            elements = inputStream.nextLine().split(",");
            rowList.addLast(new Row(columns, elements));
        }

        /* Adds rows to an array for indexing. */
        Row[] firstColumnValues = new Row[rowList.size()];
        int index = 0;
        Iterator<Row> it = rowList.iterator();
        while (it.hasNext()) {
            firstColumnValues[index] = it.next();
            index++;
        }

        // Own sorting algorithm may be written.
        Arrays.sort(firstColumnValues);

        int medianIndex = (int) Math.ceil((firstColumnValues.length / 2.0)) - 1;
        SinglyLinkedList<Row> rootElement = new SinglyLinkedList<>();
        rootElement.addLast(firstColumnValues[medianIndex]);
        Position<SinglyLinkedList<Row>> root = indexTree.addRoot(rootElement);
        binaryIndexer(0, medianIndex - 1, indexTree, root, firstColumnValues);
        binaryIndexer(medianIndex + 1, firstColumnValues.length - 1, indexTree, root, firstColumnValues);

        // PRINT OUT THE TREE FOR TESTING
        // Iterator<SinglyLinkedList<Row>> testi = indexTree.iterator();
        // while(testi.hasNext()) {
        // SinglyLinkedList<Row> curr = testi.next();
        // for(Row r : curr) {
        // System.out.println(r);
        // }
        // }
        // System.out.println(indexTree.root().getElement().first());
    }

    private void binaryIndexer(int low, int high, LinkedBinaryTree indexTree, Position<SinglyLinkedList<Row>> root,
            Row[] arr) {
        if (low > high) {
            return;
        }

        int mid = (low + high) / 2;
        indexTree.insert(root, arr[mid]);
        binaryIndexer(low, mid - 1, indexTree, root, arr);
        binaryIndexer(mid + 1, high, indexTree, root, arr);
    }

    /*
     * Returns the name of the table.
     */
    public String getTableName() {
        return tableName;
    }

    /*
     * Returns the column names in an array.
     */
    public String[] getColumnNames() {
        String[] tempArray = new String[columns.length];
        for (int i = 0; i < columns.length; i++) {
            tempArray[i] = columns[i];
        }
        return tempArray;
    }

    /*
     * Checks if the column exists in the table.
     */
    // TODO CHECK FOR BETTER IMPLEMENTATION (currently O(n^2) complexity)
    private boolean isValidColumn(String[] columnArr) {
        boolean allColumnsExist = true;
        for (int i = 0; i < columnArr.length; i++) {
            boolean exists = false;
            for (int j = 0; j < columns.length; j++) {
                if (columnArr[i].equals(columns[j])) {
                    exists = true;
                }
            }
            if (!exists) {
                allColumnsExist = false;
            }
        }
        return allColumnsExist;
    }

    /*
     * Finds the index that corresponds to a given column name in the "columns"
     * array.
     */
    private int findColumnIndex(String columnName) {
        for (int i = 0; i < columns.length; i++) {
            if (columns[i].equals(columnName)) {
                return i;
            }
        }
        return -1;
    }

    /*
     * Returns a two dimensional array.
     * First dimension has lists of each column, second dimension has the values.
     * If given column(s) is not valid, prints a error message and returns null.
     */
    public String[][] getListColumns(String[] queryColumns, String filterColumn, String filterValue) {
        String[][] columnList = new String[queryColumns.length][];

        if (isValidColumn(queryColumns)) {
            if (filterColumn != null && filterValue != null) {
                if (filterColumn.equals(columns[0])) {
                    SinglyLinkedList<Row> foundRows = new SinglyLinkedList<>();
                    foundRows = indexTree.search(filterValue);
                    for (int i = 0; i < columnList.length; i++) {
                        Iterator<Row> it = foundRows.iterator();
                        String currentColumnQuery = queryColumns[i];
                        int columnIndex = findColumnIndex(currentColumnQuery);
                        String[] requestedColumn = new String[foundRows.size()];

                        int rowIndex = 0;
                        while (it.hasNext()) {
                            Row currentRow = it.next();
                            requestedColumn[rowIndex] = currentRow.getRow(columnIndex);
                            rowIndex++;
                        }
                        columnList[i] = requestedColumn;
                    }
                } else {
                    SinglyLinkedList<Row> foundRows = new SinglyLinkedList<>();
                    // First search the rowList to find matching rows.
                    Iterator<Row> it = rowList.iterator();
                    int filterColumnIndex = findColumnIndex(filterColumn);
                    if(filterColumnIndex == -1) {
                        System.out.println("Column does not exist in the table.");
                        return null;
                    }
                    while (it.hasNext()) {
                        Row currentRow = it.next();
                        if (currentRow.getRow(filterColumnIndex).equals(filterValue)) {
                            foundRows.addLast(currentRow);
                        }
                    }
                    for (int i = 0; i < columnList.length; i++) {
                        it = foundRows.iterator();
                        String currentColumnQuery = queryColumns[i];
                        int columnIndex = findColumnIndex(currentColumnQuery);
                        String[] requestedColumn = new String[foundRows.size()];

                        int rowIndex = 0;
                        while (it.hasNext()) {
                            Row currentRow = it.next();
                            requestedColumn[rowIndex] = currentRow.getRow(columnIndex);
                            rowIndex++;
                        }
                        columnList[i] = requestedColumn;
                    }
                }
            } else {
                for (int i = 0; i < columnList.length; i++) {
                    Iterator<Row> it = rowList.iterator();
                    String currentColumnQuery = queryColumns[i];
                    int columnIndex = findColumnIndex(currentColumnQuery);
                    String[] requestedColumn = new String[rowList.size()];

                    int rowIndex = 0;
                    while (it.hasNext()) {
                        Row currentRow = it.next();
                        requestedColumn[rowIndex] = currentRow.getRow(columnIndex);
                        rowIndex++;
                    }
                    columnList[i] = requestedColumn;
                }
            }
        } else {
            System.out.println("Column(s) does not exist in the table.");
            return null;
        }

        return columnList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        for (Row r : rowList) {
            sb.append(r.toString());
            sb.append("\n");
        }
        sb.deleteCharAt(sb.lastIndexOf("\n"));
        return sb.toString();
    }
}