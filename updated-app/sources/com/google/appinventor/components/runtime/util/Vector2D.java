package com.google.appinventor.components.runtime.util;

import java.util.List;

public class Vector2D {
    private double x;
    private double y;

    public Vector2D(double x2, double y2) {
        this.x = x2;
        this.y = y2;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double magnitude() {
        return Math.sqrt(magnitudeSquared());
    }

    public double magnitudeSquared() {
        return (this.x * this.x) + (this.y * this.y);
    }

    public boolean isGreater(Vector2D that) {
        return magnitudeSquared() > that.magnitudeSquared();
    }

    public Vector2D getNormalVector() {
        return new Vector2D(this.y, -this.x);
    }

    public static Vector2D difference(Vector2D vector1, Vector2D vector2) {
        return new Vector2D(vector1.getX() - vector2.getX(), vector1.getY() - vector2.getY());
    }

    public static Vector2D addition(Vector2D vector1, Vector2D vector2) {
        return new Vector2D(vector1.getX() + vector2.getX(), vector1.getY() + vector2.getY());
    }

    public static double dotProduct(Vector2D vector1, Vector2D vector2) {
        return (vector1.getX() * vector2.getX()) + (vector1.getY() * vector2.getY());
    }

    public Vector2D unitVector() {
        return new Vector2D(getX() / magnitude(), getY() / magnitude());
    }

    public Vector2D getClosestVector(List<Vector2D> vectors) {
        Vector2D closestVector = vectors.get(0);
        double minDistance = Double.MAX_VALUE;
        for (Vector2D v : vectors) {
            double distance = difference(this, v).magnitudeSquared();
            if (distance < minDistance) {
                minDistance = distance;
                closestVector = v;
            }
        }
        return closestVector;
    }

    public void rotate(double angle) {
        this.x = (this.x * Math.cos(angle)) - (this.y * Math.sin(angle));
        this.y = (this.x * Math.sin(angle)) + (this.y * Math.cos(angle));
    }
}
