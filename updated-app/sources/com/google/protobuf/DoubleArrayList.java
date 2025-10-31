package com.google.protobuf;

import androidx.appcompat.widget.ActivityChooserView;
import com.google.protobuf.Internal;
import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class DoubleArrayList extends AbstractProtobufList<Double> implements Internal.DoubleList, RandomAccess {
    private static final DoubleArrayList EMPTY_LIST = new DoubleArrayList();
    private double[] array;
    private int size;

    static {
        EMPTY_LIST.makeImmutable();
    }

    public static DoubleArrayList emptyList() {
        return EMPTY_LIST;
    }

    DoubleArrayList() {
        this(new double[10], 0);
    }

    private DoubleArrayList(double[] other, int size2) {
        this.array = other;
        this.size = size2;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DoubleArrayList)) {
            return super.equals(o);
        }
        DoubleArrayList other = (DoubleArrayList) o;
        if (this.size != other.size) {
            return false;
        }
        double[] arr = other.array;
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
            result = (result * 31) + Internal.hashLong(Double.doubleToLongBits(this.array[i]));
        }
        return result;
    }

    public Internal.DoubleList mutableCopyWithCapacity(int capacity) {
        if (capacity >= this.size) {
            return new DoubleArrayList(Arrays.copyOf(this.array, capacity), this.size);
        }
        throw new IllegalArgumentException();
    }

    public Double get(int index) {
        return Double.valueOf(getDouble(index));
    }

    public double getDouble(int index) {
        ensureIndexInRange(index);
        return this.array[index];
    }

    public int size() {
        return this.size;
    }

    public Double set(int index, Double element) {
        return Double.valueOf(setDouble(index, element.doubleValue()));
    }

    public double setDouble(int index, double element) {
        ensureIsMutable();
        ensureIndexInRange(index);
        double previousValue = this.array[index];
        this.array[index] = element;
        return previousValue;
    }

    public void add(int index, Double element) {
        addDouble(index, element.doubleValue());
    }

    public void addDouble(double element) {
        addDouble(this.size, element);
    }

    private void addDouble(int index, double element) {
        ensureIsMutable();
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException(makeOutOfBoundsExceptionMessage(index));
        }
        if (this.size < this.array.length) {
            System.arraycopy(this.array, index, this.array, index + 1, this.size - index);
        } else {
            double[] newArray = new double[(((this.size * 3) / 2) + 1)];
            System.arraycopy(this.array, 0, newArray, 0, index);
            System.arraycopy(this.array, index, newArray, index + 1, this.size - index);
            this.array = newArray;
        }
        this.array[index] = element;
        this.size++;
        this.modCount++;
    }

    public boolean addAll(Collection<? extends Double> collection) {
        ensureIsMutable();
        if (collection == null) {
            throw new NullPointerException();
        } else if (!(collection instanceof DoubleArrayList)) {
            return super.addAll(collection);
        } else {
            DoubleArrayList list = (DoubleArrayList) collection;
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
            if (o.equals(Double.valueOf(this.array[i]))) {
                System.arraycopy(this.array, i + 1, this.array, i, this.size - i);
                this.size--;
                this.modCount++;
                return true;
            }
        }
        return false;
    }

    public Double remove(int index) {
        ensureIsMutable();
        ensureIndexInRange(index);
        double value = this.array[index];
        System.arraycopy(this.array, index + 1, this.array, index, this.size - index);
        this.size--;
        this.modCount++;
        return Double.valueOf(value);
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
