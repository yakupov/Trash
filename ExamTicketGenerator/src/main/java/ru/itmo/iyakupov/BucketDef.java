package ru.itmo.iyakupov;

public class BucketDef implements Comparable<BucketDef> {
    private int id;
    private int capacity;

    public BucketDef(int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public int compareTo(BucketDef o) {
        return Integer.compare(hashCode(), o.hashCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BucketDef bucketDef = (BucketDef) o;

        if (id != bucketDef.id) return false;
        return capacity == bucketDef.capacity;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + capacity;
        return result;
    }

    @Override
    public String toString() {
        return "BucketDef{" +
                "id=" + id +
                ", capacity=" + capacity +
                '}';
    }
}
