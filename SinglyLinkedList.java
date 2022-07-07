import java.util.Iterator;

public class SinglyLinkedList<E> implements Iterable<E> {
    private static class Node<E> {
        private E element;
        private Node<E> next;
        
        public Node(E e, Node<E> n) {
            element = e;
            next = n;
        }

        public E getElement() {
            return element;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> n) {
            next = n;
        }
    }

    private Node<E> head;
    private Node<E> tail;
    private int size = 0;
    
    public SinglyLinkedList() {}

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E first() {
        if(isEmpty())
            return null;
        return head.getElement();
    }

    public E last() {
        if(isEmpty())
            return null;
        return tail.getElement();
    }

    public void addFirst(E el) {
        head = new Node<>(el, head);
        if(isEmpty()) {
            tail = head; 
        }
        size++;
    }

    public void addLast(E el) {
        Node<E> newNode = new Node<>(el, null);
        if(isEmpty()) {
            head = newNode;    
        } else {
            tail.setNext(newNode); 
        }
        tail = newNode;
        size++;
    }

    public E removeFirst() {
        if(isEmpty()) 
            return null;
        E toReturn = head.getElement();
        head = head.getNext();
        size--;
        if(isEmpty()) {
            tail = null;
        }
        return toReturn;
    }

    public Iterator<E> iterator() {
        return new ListIterator<E>(head);
    }

    private class ListIterator<E> implements Iterator<E> {
        private Node<E> currentNode;

        public ListIterator (Node<E> initial) {
            currentNode = initial;
        }
        
        public boolean hasNext() {
            return currentNode != null;
        }

        public E next() {
            if(hasNext()) {
                E element = currentNode.getElement();
                currentNode = currentNode.getNext();
                return element;
            }
            return null;
        }

        public void remove() {

        }

    }
    
}
