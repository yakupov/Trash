package ru.itmo.iyakupov;

import java.security.SecureRandom;
import java.util.*;

public class BucketState implements Comparable<BucketState> {
    private transient Random random;
    private BucketDef bucketDef;
    private SortedSet<Integer> availableMembers = new TreeSet<>();

    public BucketState(BucketDef bucketDef) {
        this.bucketDef = bucketDef;
        for (int i = 0; i < bucketDef.getCapacity(); ++i) {
            availableMembers.add(i);
        }
    }

    public BucketDef getBucketDef() {
        return bucketDef;
    }

    public void setBucketDef(BucketDef bucketDef) {
        this.bucketDef = bucketDef;
    }

    public Set<Integer> getAvailableMembers() {
        return availableMembers;
    }

    public void setAvailableMembers(SortedSet<Integer> availableMembers) {
        this.availableMembers = availableMembers;
    }

    public boolean isExhausted() {
        return (availableMembers.isEmpty());
    }

    public int takeRandomMember() {
        if (isExhausted())
            throw new IllegalArgumentException("Bucket exhausted");

        if (random == null) {
            random = new SecureRandom();
            random.setSeed(System.nanoTime());
        }

        int memberCounter = random.nextInt(availableMembers.size());
        for (Integer availableMember : availableMembers) {
            if (memberCounter == 0) {
                availableMembers.remove(availableMember);
                return availableMember;
            } else {
                --memberCounter;
            }
        }

        throw new IllegalArgumentException("Bucket exhausted??");
    }

    @Override
    public int compareTo(BucketState o) {
        return Integer.compare(hashCode(), o.hashCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BucketState that = (BucketState) o;

        if (random != null ? !random.equals(that.random) : that.random != null) return false;
        if (bucketDef != null ? !bucketDef.equals(that.bucketDef) : that.bucketDef != null) return false;
        return availableMembers != null ? availableMembers.equals(that.availableMembers) : that.availableMembers == null;
    }

    @Override
    public int hashCode() {
        int result = random != null ? random.hashCode() : 0;
        result = 31 * result + (bucketDef != null ? bucketDef.hashCode() : 0);
        result = 31 * result + (availableMembers != null ? availableMembers.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BucketState{" +
                "random=" + random +
                ", bucketDef=" + bucketDef +
                ", availableMembers=" + availableMembers +
                '}';
    }
}
