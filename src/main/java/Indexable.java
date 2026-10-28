public class Indexable { // Without hashmap, this class can only store
                         // it's index within a single FunkyList efficiently
    public static final int DEFAULT_VALUE = -1;
    private int index;

    public Indexable() {
        resetIndex();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int newIndex) {
        this.index = newIndex;
    }

    public void resetIndex() {
        this.index = DEFAULT_VALUE;
    }
}
