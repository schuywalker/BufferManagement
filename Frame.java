public class Frame {
    private int pinCount = 0;
    private boolean dirty = false;
    private String content;

    public Frame(String content) {
        this.content = content;
    }

    public int getPin() {
        // your code goes here
        return 0; // you need to change the returned value
    }

    public void incPin() {
        // your code goes here
    }

    public void decPin() {
        // your code goes here
    }

    public boolean isDirty() {
        // your code goes here
        return true; // you need to change the returned value
    }

    public void setDirty(boolean dirty) {
        // your code goes here
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
