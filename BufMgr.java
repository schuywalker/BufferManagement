import java.io.*;
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
        lruQueue = new ArrayList<>();
        for (int i = 0; i <= poolSize; i++) {
            lruQueue.add(i);
        }

    }

    public void pin(int pageNum) {
    // 'I want page x protocol'
    // note: a pin represents a tally for an outside object/process that is using a frame.

        Integer frameNum = bufTbl.lookup(pageNum);

        if (frameNum != null) { // page already in queue.
            pool[frameNum].incPin();
        }


            // page not in queue
            /*
            Choose a frame for replacement using a page replacement policy
                – must choose a frame with pincount == 0
            • If frame is dirty, write the old (“dirty”) page back to disk
            • Read requested page into chosen frame – Set pincount = 1
                – Set dirty bit = false
             */


        else { // frameNum is not null

            frameNum = lruQueue.get(0); // Choose a frame for replacement using a page replacement policy



            if (pool[frameNum] != null && pool[frameNum].isDirty()) { // write contents of frame back to the file whos pageNum it's holding
                this.writePage(frameNum);
            }

            this.readPage(pageNum); // read file $pageNum.txt to the available frame
            pool[frameNum].incPin();
            used++;
        }
    }
    public void unpin(int pageNum) {
    // 'I'm done with page X' protocol
        int frameNum = bufTbl.lookup(pageNum);
        if (frameNum < 0) {
            System.out.println("this page isn't in the pool.");
            // throw
            return;
        }
        pool[frameNum].decPin();
        if (pool[frameNum].getPin() == 0){
            pool[frameNum].setDirty(true);
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

    public void readPage(int pageNum) {
    // readPage(): read from disk to buffer, i.e., read the corresponding .txt file.

        // get file contents
        String fileName = getPageFileName(pageNum);
        Scanner reader;
        String pageContent = "";
        File file = new File(fileName);
        try {
            reader = new Scanner(file);
            while (reader.hasNextLine()){
                    pageContent += reader.nextLine();
            }
            reader.close();
        } catch (Exception e ){
            e.printStackTrace();
        }
        // "FRAME CONSTRUCTION SUITE"
        // get Q position (same as in pin but now we remove - doing this to keep return types same as teachers)
        int newFrameNum = lruQueue.remove(0); // next available frameNum
        Frame newFrame = new Frame(pageContent); // make frame
        pool[newFrameNum] = newFrame; // put frame in pool
        bufTbl.insert(pageNum,newFrameNum); // put reference in bufTbl for retrieval of frameNum via pageNum
    }

    public void writePage(int pageNum) {
        // writePage(): write from buffer to disk, i.e., overwrite the corresponding .txt file.
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
        pool[frameNum].setDirty(false);
    }

    public void displayPage(int pageNum) {
    // display the contents in the current frame.
        Integer frameNum = bufTbl.lookup(pageNum);
        if (frameNum == null) throw new IllegalArgumentException("Cannot display page that is not in memory");

        System.out.println(pool[frameNum].getContent());
    }

    public void updatePage(int pageNum, String toAppend) {
    // append the new contents to the current frame.
        Integer frameNum = bufTbl.lookup(pageNum);
        if (frameNum == null) throw new IllegalArgumentException("Cannot update page that is not in memory");

        pool[frameNum].updatePage(toAppend);
    }

    private String getPageFileName(int pageNum) {
        return pageNum + ".txt";
    }
}
