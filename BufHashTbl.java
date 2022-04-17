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
        // your code goes here
    }

    public int lookup(int pageNum) {
        // your code goes here
        return 0;  // you need to change the returned value
    }

    public boolean remove(int pageNum, int frameNum) {
        // your code goes here
        return true; // you need to change the returned value
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
