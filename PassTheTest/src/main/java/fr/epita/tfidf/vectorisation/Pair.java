package fr.epita.tfidf.vectorisation;

import java.util.Objects;

public class Pair<A, B> {
    public A left;
    public B right;

    public Pair(A left, B right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "(" + left +
                "," + right +
                ')';
    }

    @Override
    public boolean equals(final Object o) {
        Pair p2 = (Pair)o;
        return p2.left.equals(left) && p2.right.equals(right);
    }
}