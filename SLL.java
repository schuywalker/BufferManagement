// probably need to make a master SLL constructor used only for representing
// pages? Or maybe just make actual txt files and have a folder for these...

public class SLL {

    protected Node head;
    protected Node tail;
    private int frameNum;

    int size = 0;

    //contructor for hashTable
    public SLL(int frameNum) {
        head = new Node();
        tail = new Node();
        head.setNext(tail);
        this.frameNum = frameNum;
    }

    public void insert(int pageNum){
        if (size == 0) {
            Node newNode = new Node(pageNum);
            head.setNext(newNode);
            newNode.setNext(tail);
        }
        else { //inserts at front of list
            Node newNode = new Node(pageNum);
            newNode.setNext(head.getNext());
            head.setNext(newNode);
        }
        size++;
    }

    public int pageSearch(int queriedPageNum) {
        if (size == 0) { return -1; }
        else {
            Node cursor = head.getNext();
            while (cursor != tail){
                if (cursor.pageNum == queriedPageNum) {
                    return this.frameNum;
                }
            }
        }
        return -1;
    }

    // lets assume that we know the page is in this list, since we'll call pageSearch from
    // the hashtable to find the right SLL first.
    public Node deleteNode(int pageNum) {
        if (size == 0) { return null; }
        else {
            Node cursor = head.getNext();
            Node cursorPrev = head;

            while (cursor != tail) {
                if (cursor.getPageNum() == pageNum) { // we've found a match
                    cursorPrev.setNext(cursor.getNext());
                    size--;
                    return cursor;
                }

                cursor = cursor.next;
                cursorPrev = cursorPrev.next;
            }

            System.out.println("if reaching this, then delete node function failed");
            return null;
        }
    }


    public static void printList(SLL list){ // calls Node class print method
        if (list == null) {
            System.out.println("list is null");
            return;
        }
        System.out.println("Frame: "+list.frameNum);
        if (list.head.next == list.tail) {
            return;
        }
        Node temp = list.head.getNext();
        while(temp != list.tail){
            temp.printNode();
            temp = temp.getNext();
        }
    }

}
