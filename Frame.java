public class Frame {
    private int pinCount = 0;
    private boolean dirty = false;
    private String content;

    public Frame(String content) {
        this.content = content;
    }

    public int getPin() {
        return pinCount;
    }

    public void incPin() {
        pinCount++;
    }

    public void decPin() {
        if (pinCount > 0) {
            pinCount--;
        } else {
            pinCount = 0;
        }
    }

    public boolean isDirty() {
        return this.dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = !dirty;
    }

    public void displayPage() {
        System.out.println(content);
    }

    public void updatePage(String toAppend) {
        content += "\n" + toAppend;
        dirty = true;
    }

    public String getContent() {
        return content;
    }
}
