// Items can only exist ONCE in a SINGLE funky list

import java.util.ArrayList;

public class FunkyList<E extends Indexable> extends ArrayList<E> {

    @Override
    public boolean add(E item) {

        if (item.getIndex() != Indexable.DEFAULT_VALUE) { // Check if item exists already
            throw new IllegalArgumentException("Item must not already exist in the list");
        }

        boolean bool = super.add(item);

        if (bool) { // Check if added successfully
            item.setIndex(super.size() - 1);
        }

        return bool;
    }

    public void funkyRemove(E item) { // Swaps element with last element, and removes last

        if (item.getIndex() == Indexable.DEFAULT_VALUE) { // check if element actually exists
            throw new IllegalArgumentException("Item must exist in the list");
        }

        E last = this.getLast(); // get last elements value
        int index = item.getIndex();

        super.set(index, last); // swap values
        super.removeLast(); // remove last

        last.setIndex(index); // set new indexes for last and selected item
        item.resetIndex();
    }
}
