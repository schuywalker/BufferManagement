public class HashTable {

    protected SLL[] table;

    public HashTable(int size){
        table = new SLL[size];
        // each array position represents a frame number.
    }

    public void insert(int pageNum){
        if (pageNum < 0) { // do I need this if block?
            System.out.println("that frame number does not exist");
            return;
        }
        else {
            int frame = table.length % pageNum;
            table[frame].insert(pageNum);
        }
    }

    public int lookup(int pageNum){
        if (pageNum < 0) {
            System.out.println("that frame number does not exist");
            return -1;
        }
        return table[table.length % pageNum].pageSearch(pageNum); // returns the number of the frame holding the page
    }

    public void deletePageFromFrame(int pageNum){
        if (pageNum < 0) {
            System.out.println("that frame number does not exist");
            return;
        }

        int targetFrameNum = this.lookup(pageNum);

        if (targetFrameNum < 0) {
            System.out.println("that page isn't in the hashtable.");
        }
        else {
            table[targetFrameNum].deleteNode(pageNum);
        }

    }

}
