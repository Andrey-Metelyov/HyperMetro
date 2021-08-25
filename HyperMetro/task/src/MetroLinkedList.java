import java.util.Collection;

class MetroLinkedList<T> {
    Node<T> head;
    Node<T> tail;
    int size;

    static class Node<T> {
        T value;
        Node<T> prev;
        Node<T> next;

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    public MetroLinkedList() {
    }

    public MetroLinkedList(Collection<T> collection) {
        for (T el : collection) {
            add(el);
        }
    }

    public MetroLinkedList(T[] array) {
        for (T el : array) {
            add(el);
        }
    }

    public void printTriples(T first, T last) {
        T prev = first;
        Node<T> cur = head;
        while (cur != null) {
            T next = cur.next == null ? last : cur.next.value;
            System.out.print(prev);
            System.out.print(" - ");
            System.out.print(cur.value);
            System.out.print(" - ");
            System.out.println(next);
            prev = cur.value;
            cur = cur.next;
        }
    }

    public void addFirst(T value) {
        if (head == null) {
            add(value);
        } else {
            head.prev = new Node<>(value, null, head);
            head = head.prev;
            size++;
        }
    }

    public void addLast(T value) {
        add(value);
    }

    public void removeFirst() {
        if (head != null) {
            head = head.next;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null;
            }
        }
        size--;
    }

    public void removeLast() {
        if (tail != null) {
            tail = tail.prev;
            if (tail != null) {
                tail.next = null;
            } else {
                head = null;
            }
        }
        size--;
    }

    public void reverse() {
        Node<T> cur = head;
        while (cur != null) {
            Node<T> next = cur.next;
            cur.next = cur.prev;
            cur.prev = next;
            cur = next;
        }
        cur = tail;
        tail = head;
        head = cur;
    }

    public void split(int index) {
        Node<T> cur = head;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        tail.next = head;
        head.prev = tail;
        head = cur;
        tail = cur.prev;
        head.prev = null;
        tail.next = null;
    }

    public void add(T el) {
        if (tail == null) {
            head = tail = new Node<>(el, null, null);
        } else {
            tail.next = new Node<>(el, tail, null);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public String toString() {
        Node<T> cur = head;
        StringBuilder buf = new StringBuilder();
        while (cur != null) {
            buf.append(cur.value).append(" ");
            cur = cur.next;
        }
        return buf.toString();
    }
}
