public class Row implements Comparable<Row> {
    private String[] rowElements;

    public Row(String[] columns, String... elements) {
        rowElements = new String[columns.length];
        for(int i = 0; i < columns.length; i++) {
            rowElements[i] = elements[i];
        }
    }

    
    /* 
     * A deep copy of the array will be created and returned.
     * Since Strings are immutable, original elements can not be changed.
    */
    public String[] getRow() {
        String[] tempArray = new String[rowElements.length];
        for(int i = 0; i < rowElements.length; i++) {
            tempArray[i] = rowElements[i];
        }
        return tempArray;
    }

    /*
     * Overloaded getRow(), takes a column index and returns the row value.
     */
    public String getRow(int indexOfColumn) {
        return rowElements[indexOfColumn];
    }

    /*
     * Sets the whole row. If the number of given elements does not match with
     * the rowElements array, IllegalArgumentException is thrown.
     */
    public void setRow(String... elements) throws IllegalArgumentException{
        if(elements.length != rowElements.length) {
            throw new IllegalArgumentException("The number of elements does not match.");
        }

        for(int i = 0; i < rowElements.length; i++) {
            rowElements[i] = elements[i];
        }
    }


    /*
     * Sets a single element of the row at the column given by the index.
     * Returns the replaced element.
     */
    public String setRowElement(int index, String element) throws IndexOutOfBoundsException{
        if(index >= rowElements.length || index < 0) {
            throw new IndexOutOfBoundsException("Index is out of bounds.");
        }
        String temp = rowElements[index];
        rowElements[index] = element;
        return temp;
    }

    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder("");
        for(String s : rowElements) {
            sb.append(s);
            sb.append(" ");
        }
        return sb.toString();
    }

    public int compareTo(Row r) {
        int comparisonResult = getRow(0).compareTo(r.getRow(0));
        if(comparisonResult < 0) {
            return -1;
        } else if (comparisonResult > 0) {
            return 1;
        } else {
            return 0;
        }
    }
}
