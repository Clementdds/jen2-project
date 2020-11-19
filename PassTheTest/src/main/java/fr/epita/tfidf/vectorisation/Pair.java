package fr.epita.tfidf.vectorisation;

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
}