//import java.util.Optional;

// nodes tell us is a page is in the frame pool and if so in which frame.
// nodes are not actual pages - just a record-keeper. The frame number is held in the SLL
// that contains a given node.

public class Node {

    protected int pageNum;
    //protected String content = "";
    protected Node next;

    public Node(){}

    public Node(int pageNum){
        if (pageNum < 0)
        {
            throw new IllegalArgumentException("can't have negative page number");
        }

        this.pageNum = pageNum;
    }

    public Node(int pageNum, Node next){
        if (pageNum < 0)
        {
            throw new IllegalArgumentException("can't have negative page number");
        }
        this.pageNum = pageNum;
        this.next = next;
    }

//    public void setPageNum(int pageNum){ this.pageNum = pageNum; }
    // delete content method?
    // pages never created in same step as

    public int getPageNum(){ return this.pageNum; } // page num read only?
    //public void setPageNum(int pageNum) { this.pageNum = pageNum; }

    public Node getNext(){ return this.next; }
    public void setNext(Node next){ this.next = next; }

   // public String getContent(){ return this.content; }
  //  public void AppendContent(String newContent){ this.content += newContent; }

    public void printNode(){ System.out.println("Page Number: "+this.pageNum); }
}
