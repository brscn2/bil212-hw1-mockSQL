public class LinkedBinaryTree extends AbstractBinaryTree<SinglyLinkedList<Row>> {
    protected static class Node implements Position<SinglyLinkedList<Row>> {
        private SinglyLinkedList<Row> element;
        private Row key;
        private Node parent;
        private Node left;
        private Node right;

        public Node(SinglyLinkedList<Row> e, Node parentNode, Node leftChild, Node rightChild) {
            element = e;
            key = e.first();
            parent = parentNode;
            left = leftChild;
            right = rightChild;
        }

        public Node() {
            element = null;
            parent = null;
            left = null;
            right = null;
        }

        public SinglyLinkedList<Row> getElement() {
            return element;
        }

        public Node getParent() {
            return parent;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        public void setElement(SinglyLinkedList<Row> el) {
            element = el;
        }

        public void setParent(Node parentNode) {
            parent = parentNode;
        }

        public void setLeft(Node leftChild) {
            left = leftChild;
        }

        public void setRight(Node rightChild) {
            right = rightChild;
        }
    }

    private Node root = null;
    private int size = 0;

    public LinkedBinaryTree() {
    }

    private Node validate(Position<SinglyLinkedList<Row>> p) throws IllegalArgumentException {
        if (!(p instanceof Node))
            throw new IllegalArgumentException("This position is not valid.");
        Node node = (Node) p;
        if (node.getParent() == node)
            throw new IllegalArgumentException("This position is not in the tree.");
        return node;
    }

    public int size() {
        return size;
    }

    public Position<SinglyLinkedList<Row>> root() {
        return root;
    }

    public Position<SinglyLinkedList<Row>> parent(Position<SinglyLinkedList<Row>> p) throws IllegalArgumentException {
        Node node = validate(p);
        return node.getParent();
    }

    public Position<SinglyLinkedList<Row>> left(Position<SinglyLinkedList<Row>> p) throws IllegalArgumentException {
        Node node = validate(p);
        return node.getLeft();
    }

    public Position<SinglyLinkedList<Row>> right(Position<SinglyLinkedList<Row>> p) throws IllegalArgumentException {
        Node node = validate(p);
        return node.getRight();
    }

    public Position<SinglyLinkedList<Row>> insert(Position<SinglyLinkedList<Row>> rootPosition, Row e) {
        Node rootNode = validate(rootPosition);
        int comparisonResult = e.compareTo(rootNode.key);

        if (comparisonResult < 0) {
            if (rootNode.getLeft() != null) {
                return insert(rootNode.getLeft(), e);

            } else {
                return addLeft(rootNode, e);
            }
        } else if (comparisonResult > 0) {
            if (rootNode.getRight() != null) {
                return insert(rootNode.getRight(), e);
            } else {
                return addRight(rootNode, e);
            }
        } else {
            rootNode.getElement().addLast(e);
            return rootNode;
        }
    }

    public SinglyLinkedList<Row> search(String s) {
        return search_recursive(root, s);
    }

    private SinglyLinkedList<Row> search_recursive(Position<SinglyLinkedList<Row>> rootPosition, String e) {
        Node rootNode = validate(rootPosition);
        int comparisonResult = e.compareTo(rootNode.getElement().first().getRow(0));

        if (comparisonResult < 0) {
            if (rootNode.getLeft() != null) {
                return search_recursive(rootNode.getLeft(), e);
            }
        } else if (comparisonResult > 0) {
            if (rootNode.getRight() != null) {
                return search_recursive(rootNode.getRight(), e);
            }
        } else {
            return rootNode.getElement();
        }
        return null;
    }

    public Position<SinglyLinkedList<Row>> addRoot(SinglyLinkedList<Row> e) throws IllegalStateException {
        if (!isEmpty())
            throw new IllegalStateException("The tree is not empty.");
        root = new Node(e, null, null, null);
        size = 1;
        return root;
    }

    public Position<SinglyLinkedList<Row>> addLeft(Position<SinglyLinkedList<Row>> p, Row e)
            throws IllegalArgumentException {
        Node parent = validate(p);
        if (parent.getLeft() != null) {
            throw new IllegalArgumentException("Parent already has a left child.");
        }

        SinglyLinkedList<Row> temp = new SinglyLinkedList<>();
        temp.addFirst(e);
        Node child = new Node(temp, parent, null, null);
        parent.setLeft(child);
        size++;
        return child;
    }

    public Position<SinglyLinkedList<Row>> addRight(Position<SinglyLinkedList<Row>> p, Row e)
            throws IllegalArgumentException {
        Node parent = validate(p);
        if (parent.getRight() != null) {
            throw new IllegalArgumentException("Parent already has a right child.");
        }

        SinglyLinkedList<Row> temp = new SinglyLinkedList<>();
        temp.addFirst(e);
        Node child = new Node(temp, parent, null, null);
        parent.setRight(child);
        size++;
        return child;
    }

    public SinglyLinkedList<Row> set(Position<SinglyLinkedList<Row>> p, SinglyLinkedList<Row> e)
            throws IllegalArgumentException {
        Node node = validate(p);
        SinglyLinkedList<Row> temp = node.getElement();
        node.setElement(e);
        return temp;
    }

    // No need for a attach method

    public SinglyLinkedList<Row> remove(Position<SinglyLinkedList<Row>> p) throws IllegalArgumentException {
        Node node = validate(p);

        if (numChildren(p) == 2) {
            throw new IllegalArgumentException("This position has 2 children.");
        }
        Node child = null;
        if (node.getLeft() != null)
            child = node.getLeft();
        else
            child = node.getRight();

        if (child != null) {
            child.setParent(node.getParent());
        }
        if (node == root)
            root = child;
        else {
            Node parent = node.getParent();
            if (node == parent.getLeft()) {
                parent.setLeft(child);
            } else {
                parent.setRight(child);
            }
        }
        size--;
        SinglyLinkedList<Row> temp = node.getElement();
        node.setElement(null);
        node.setLeft(null);
        node.setRight(null);
        node.setParent(node);
        return temp;
    }
}