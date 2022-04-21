import java.util.ArrayList;
import java.util.List;

public class BufHashTbl {
    private final List<List<BufTblRecord>> records; //Forced to make double list of lists because java doesn't support arrays of generics for some dumb reason
    private final int tableSize = 10;

    public BufHashTbl() {
        records = new ArrayList<>();
        for (int i = 0; i < tableSize; i++) {
            records.add(new ArrayList<>());
        }
    }

    public void insert(int pageNum, int frameNum) {
    //  insert(): insert a < page#, frame#> pair into the page table.

        if (frameNum < 0 || frameNum > records.size()) { // do I need this if block?
            System.out.println("that frame number does not exist");
            return;
        }
        else {
            records.get(pageNum % tableSize).add(new BufTblRecord(pageNum, frameNum));
        }
    }

    public Integer lookup(int pageNum) {
    // lookup(): search for a page from the page table, and return the frame# if the page is found.
        List<BufTblRecord> frameRecord = records.get(pageNum); // remvoved % tableSize
        for (int i = 0; i < frameRecord.size(); i++){
            if (frameRecord.get(i).pageNum == pageNum) {
                return frameRecord.get(i).frameNum; // found page, return frame number
            }
        }
        return null; // didnt find it
    }

    public boolean remove(int pageNum, int frameNum) {
    // remove(): remove a < page#, frame#> pair from the page table.
        List<BufTblRecord> rec = records.get(pageNum % tableSize);
        for (int i = 0; i < rec.size(); i++) {
            if (rec.get(i).pageNum == pageNum && rec.get(i).frameNum == frameNum) {
                rec.remove(i);
                return true;
            }
        }
        return false;
    }

    private static class BufTblRecord {
        public int pageNum;
        public int frameNum;

        public BufTblRecord (int pageNum, int frameNum) {
            this.pageNum = pageNum;
            this.frameNum = frameNum;
        }
    }
}
