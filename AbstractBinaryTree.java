import java.util.*;

public abstract class AbstractBinaryTree<E> extends AbstractTree<E> implements BinaryTree<E>{
    public Position<E> sibling(Position<E> p) {
        Position<E> parent = parent(p);
        if(parent == null) return null;
        if(p == left(parent)) {
            return right(parent);
        } else {
            return left(parent);
        }
    }

    public int numChildren(Position<E> p) {
        int count = 0;
        if(left(p) != null) {
            count++;
        }
        if(right(p) != null) {
            count++;
        }
        return count;
    }

    public Iterable<Position<E>> children(Position<E> p) {
        Position<E>[] snap = (Position<E>[]) new Object[2];
        int currentIndex = 0;
        if(left(p) != null) {
            snap[currentIndex] = left(p);
            currentIndex++;
        }
        if(right(p) != null) {
            snap[currentIndex] = right(p);
        }
        return Arrays.asList(snap);
    }

    private void inorderSubtree(Position<E> p, List<Position<E>> snap) {
        if(left(p) != null) {
            inorderSubtree(left(p), snap);
        }
        snap.add(p);
        if(right(p) != null) {
            inorderSubtree(right(p), snap);
        }
    }

    public Iterable<Position<E>> inorder() {
        List<Position<E>> snap = new ArrayList<>();
        if(!isEmpty()) {
            inorderSubtree(root(), snap);
        }
        return snap;
    }

    public Iterable<Position<E>> positions() {
        return inorder();
    }
}
