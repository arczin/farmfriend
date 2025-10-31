package com.google.protobuf;

import androidx.appcompat.widget.ActivityChooserView;
import com.google.protobuf.Internal;
import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class BooleanArrayList extends AbstractProtobufList<Boolean> implements Internal.BooleanList, RandomAccess {
    private static final BooleanArrayList EMPTY_LIST = new BooleanArrayList();
    private boolean[] array;
    private int size;

    static {
        EMPTY_LIST.makeImmutable();
    }

    public static BooleanArrayList emptyList() {
        return EMPTY_LIST;
    }

    BooleanArrayList() {
        this(new boolean[10], 0);
    }

    private BooleanArrayList(boolean[] other, int size2) {
        this.array = other;
        this.size = size2;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BooleanArrayList)) {
            return super.equals(o);
        }
        BooleanArrayList other = (BooleanArrayList) o;
        if (this.size != other.size) {
            return false;
        }
        boolean[] arr = other.array;
        for (int i = 0; i < this.size; i++) {
            if (this.array[i] != arr[i]) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int result = 1;
        for (int i = 0; i < this.size; i++) {
            result = (result * 31) + Internal.hashBoolean(this.array[i]);
        }
        return result;
    }

    public Internal.BooleanList mutableCopyWithCapacity(int capacity) {
        if (capacity >= this.size) {
            return new BooleanArrayList(Arrays.copyOf(this.array, capacity), this.size);
        }
        throw new IllegalArgumentException();
    }

    public Boolean get(int index) {
        return Boolean.valueOf(getBoolean(index));
    }

    public boolean getBoolean(int index) {
        ensureIndexInRange(index);
        return this.array[index];
    }

    public int size() {
        return this.size;
    }

    public Boolean set(int index, Boolean element) {
        return Boolean.valueOf(setBoolean(index, element.booleanValue()));
    }

    public boolean setBoolean(int index, boolean element) {
        ensureIsMutable();
        ensureIndexInRange(index);
        boolean previousValue = this.array[index];
        this.array[index] = element;
        return previousValue;
    }

    public void add(int index, Boolean element) {
        addBoolean(index, element.booleanValue());
    }

    public void addBoolean(boolean element) {
        addBoolean(this.size, element);
    }

    private void addBoolean(int index, boolean element) {
        ensureIsMutable();
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException(makeOutOfBoundsExceptionMessage(index));
        }
        if (this.size < this.array.length) {
            System.arraycopy(this.array, index, this.array, index + 1, this.size - index);
        } else {
            boolean[] newArray = new boolean[(((this.size * 3) / 2) + 1)];
            System.arraycopy(this.array, 0, newArray, 0, index);
            System.arraycopy(this.array, index, newArray, index + 1, this.size - index);
            this.array = newArray;
        }
        this.array[index] = element;
        this.size++;
        this.modCount++;
    }

    public boolean addAll(Collection<? extends Boolean> collection) {
        ensureIsMutable();
        if (collection == null) {
            throw new NullPointerException();
        } else if (!(collection instanceof BooleanArrayList)) {
            return super.addAll(collection);
        } else {
            BooleanArrayList list = (BooleanArrayList) collection;
            if (list.size == 0) {
                return false;
            }
            if (ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED - this.size >= list.size) {
                int newSize = this.size + list.size;
                if (newSize > this.array.length) {
                    this.array = Arrays.copyOf(this.array, newSize);
                }
                System.arraycopy(list.array, 0, this.array, this.size, list.size);
                this.size = newSize;
                this.modCount++;
                return true;
            }
            throw new OutOfMemoryError();
        }
    }

    public boolean remove(Object o) {
        ensureIsMutable();
        for (int i = 0; i < this.size; i++) {
            if (o.equals(Boolean.valueOf(this.array[i]))) {
                System.arraycopy(this.array, i + 1, this.array, i, this.size - i);
                this.size--;
                this.modCount++;
                return true;
            }
        }
        return false;
    }

    public Boolean remove(int index) {
        ensureIsMutable();
        ensureIndexInRange(index);
        boolean value = this.array[index];
        System.arraycopy(this.array, index + 1, this.array, index, this.size - index);
        this.size--;
        this.modCount++;
        return Boolean.valueOf(value);
    }

    private void ensureIndexInRange(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(makeOutOfBoundsExceptionMessage(index));
        }
    }

    private String makeOutOfBoundsExceptionMessage(int index) {
        return "Index:" + index + ", Size:" + this.size;
    }
}
