import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BufMgr {
    private BufHashTbl bufTbl;
    private Frame[] pool;
    private int poolSize;
    private List<Integer> lruQueue;
    private int used = 0;


    public BufMgr(int poolSize) {
        bufTbl = new BufHashTbl();
        this.poolSize = poolSize;
        this.pool = new Frame[poolSize];
        lruQueue = new ArrayList<>(poolSize);
    }

    // 'I want page x protocol'
    // note: a pin represents a tally for an outside object/process that is using a frame.
    public void pin(int pageNum) {

        int frameLookup = bufTbl.lookup(pageNum);
        if (frameLookup >= 0) { // page already in queue.
            pool[frameLookup].incPin();
        }
        else { // page not in queue
            /*
            Choose a frame for replacement using a page replacement policy
                – must choose a frame with pincount == 0
            • If frame is dirty, write the old (“dirty”) page back to disk
            • Read requested page into chosen frame – Set pincount = 1
                – Set dirty bit = false
             */
            int newFrameNum = lruQueue.remove(0); // next available frameNum

            if (pool[frameLookup].isDirty()) {
                this.writePage(frameLookup); // how to get pageNum to write to
                pool[frameLookup].setDirty(false);
            }
            Frame newFrame = new Frame(this.readPage(pageNum));
            pool[newFrameNum] = newFrame;
            pool[newFrameNum].incPin();
            bufTbl.insert(pageNum, newFrameNum);
        }

    }

    // 'I'm done with page X' protocol
    public void unpin(int pageNum) {
        int frameNum = bufTbl.lookup(pageNum);
        if (frameNum < 0) {
            System.out.println("this page isn't in the pool.");
            // throw
            return;
        }
        pool[frameNum].decPin();
        if (pool[frameNum].getPin() == 0){
            /////////////////////////////////////////////
            if (pool[frameNum].isDirty()){
                // dont need anything here I think,
                // because we write page back when it comes out of the lruQ
            }
            lruQueue.add(frameNum); // add freed up frame to end of queue
        }
    }

    public void createPage(int pageNum) {
        String name = getPageFileName(pageNum);
        String contents = "This is page " + pageNum + ".";
        FileWriter writer;

        try {
            writer = new FileWriter(name, false);
            writer.write(contents);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Something went wrong while creating the page");
        }
    }

    // she had this as returning void - ask her about this???
    // readPage(): read from disk to buffer, i.e., read the corresponding .txt file.
    public String readPage(int pageNum) {
        String fileName = getPageFileName(pageNum);
        Scanner reader;
        String pageContent = "";
        try {
            reader = new Scanner(fileName);
            while (reader.hasNextLine()){
                    pageContent += reader.nextLine();
            }
            reader.close();
        } catch (Exception e ){
            e.printStackTrace();
        }
        // assigning frame content to pageContent will happen in pin.
        // displayPage prints pageContent
        return pageContent;
    }

    // writePage(): write from buffer to disk, i.e., overwrite the corresponding .txt file.
    public void writePage(int pageNum) {
        // write from buffer to disk, i.e., overwrite the corresponding .txt file.
        int frameNum = bufTbl.lookup(pageNum);
        String name = getPageFileName(pageNum);
        // String contents = "This is page " + pageNum + ".";
        FileWriter writer = null;

        try {
            writer = new FileWriter(name, false);
            writer.write(pool[frameNum].getContent());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Something went wrong while creating the page");
        }
    }

    // display the contents in the current frame.
    public void displayPage(int pageNum) {
        Integer frameNum = bufTbl.lookup(pageNum);
        if (frameNum == null) throw new IllegalArgumentException("Cannot display page that is not in memory");
        System.out.println(readPage(pageNum));
    }

    // append the new contents to the current frame.
    public void updatePage(int pageNum, String toAppend) {
        Integer frameNum = bufTbl.lookup(pageNum);
        if (frameNum == null) throw new IllegalArgumentException("Cannot update page that is not in memory");

        pool[frameNum].updatePage(toAppend);
    } // appends frame in pool

    private String getPageFileName(int pageNum) {
        return pageNum + ".txt";
    }
}
