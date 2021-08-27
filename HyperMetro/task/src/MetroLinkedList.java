import Metro.Station;

import java.util.Collection;
/*
class MetroLinkedList {
    Node head;
    Node tail;
    int size;

    static class Node {
        Station value;
        Node prev;
        Node next;

        public Node(Station value, Node prev, Node next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    public MetroLinkedList() {
    }

    public MetroLinkedList(Collection<Station> collection) {
        for (Station el : collection) {
            add(el);
        }
    }

    public MetroLinkedList(Station[] array) {
        for (Station el : array) {
            add(el);
        }
    }

    public void printLine(Station first, Station last) {
        System.out.println(first.name);
        Node cur = head;
        while (cur != null) {
            System.out.println(cur.value.name);
            cur = cur.next;
        }
        System.out.println(last.name);
    }

    public void printTriples(Station first, Station last) {
        Station prev = first;
        Node cur = head;
        while (cur != null) {
            Station next = cur.next == null ? last : cur.next.value;
            System.out.print(prev.name);
            System.out.print(" - ");
            System.out.print(cur.value.name);
            System.out.print(" - ");
            System.out.println(next.name);
            prev = cur.value;
            cur = cur.next;
        }
    }

    public void addFirst(Station value) {
        if (head == null) {
            add(value);
        } else {
            head.prev = new Node(value, null, head);
            head = head.prev;
            size++;
        }
    }

    public void addLast(Station value) {
        add(value);
    }

    public void remove(Station value) {
        Node cur = head;
        while (cur != null && !cur.value.equals(value)) {
            cur = cur.next;
        }
        if (cur != null) {
            if (cur == head) {
                removeFirst();
            } else if (cur == tail) {
                removeLast();
            } else {
                cur.prev.next = cur.next;
                cur.prev = null;
                cur.next = null;
            }
        }
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
        Node cur = head;
        while (cur != null) {
            Node next = cur.next;
            cur.next = cur.prev;
            cur.prev = next;
            cur = next;
        }
        cur = tail;
        tail = head;
        head = cur;
    }

    public void split(int index) {
        Node cur = head;
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

    public void add(Station el) {
        if (tail == null) {
            head = tail = new Node(el, null, null);
        } else {
            tail.next = new Node(el, tail, null);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public String toString() {
        Node cur = head;
        StringBuilder buf = new StringBuilder();
        while (cur != null) {
            buf.append(cur.value).append(" ");
            cur = cur.next;
        }
        return buf.toString();
    }
}
*/