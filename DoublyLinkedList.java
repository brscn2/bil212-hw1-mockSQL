import java.util.Iterator;

public class DoublyLinkedList<E> {
    private static class Node<E> {
        private E element;
        private Node<E> prev;
        private Node<E> next;

        public Node(E el, Node<E> p, Node<E> n) {
            element = el;
            prev = p;
            next = n;
        }

        public E getElement() {
            return element;
        }

        public void setPrev(Node<E> p) {
            prev = p;
        }

        public void setNext(Node<E> n) {
            next = n;
        }

        public Node<E> getPrev() {
            return prev;
        }

        public Node<E> getNext() {
            return next;
        }
    }

    private Node<E> header;
    private Node<E> trailer;
    private int size = 0;

    public DoublyLinkedList() {
        header = new Node<E>(null, null, null);
        trailer = new Node<E>(null, header, null);
        header.setNext(trailer);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E first() {
        if(isEmpty()) {
            return null;
        }
        return header.getNext().getElement();
    }

    public E last() {
        if(isEmpty()) {
            return null;
        }
        return trailer.getPrev().getElement();
    }

    public void addFirst(E el) {
        addBetween(el, header, header.getNext());
    }

    public void addLast(E el) {
        addBetween(el, trailer.getPrev(), trailer);
    }

    public E removeFirst() {
        if(isEmpty()) {
            return null;
        }
        Node<E> answerNode = header.getNext();
        header.setNext(answerNode.getNext());
        answerNode.getNext().setPrev(header);
        size--;
        return answerNode.getElement();
    }

    public E removeLast() {
        if(isEmpty()) {
            return null;
        }
        Node<E> answerNode = trailer.getPrev();
        trailer.setPrev(answerNode.getPrev());
        answerNode.getPrev().setNext(trailer);
        size--;
        return answerNode.getElement();
    }

    private void addBetween(E el, Node<E> pre, Node<E> post) {
        Node<E> toAdd = new Node<E>(el, pre, post);
        pre.setNext(toAdd);
        post.setPrev(toAdd);
        size++;
    }

    public Iterator<E> iterator() {
        return new ListIterator<E>(header);
    }

    private class ListIterator<E> implements Iterator<E> {
        private Node<E> currentNode;
        private Node<E> lastNode;

        public ListIterator (Node<E> initial) {
            currentNode = initial;
        }
        
        public boolean hasNext() {
            return currentNode.getNext().getElement() != null;
        }

        public E next() {
            lastNode = currentNode;
            if(hasNext()) {
                currentNode = currentNode.getNext();
                return currentNode.getElement();
            }
            return null;
        }

        public void remove() {
            if(lastNode == currentNode) {
                throw new IllegalStateException("Remove can be called once per next()");
            }

            Node<E> pre = currentNode.getPrev();
            Node<E> post = currentNode.getNext();

            pre.setNext(post);
            post.setPrev(pre);
            currentNode = pre;
            size--;
        }

    }
}