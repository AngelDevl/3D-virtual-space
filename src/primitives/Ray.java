package primitives;

public class Ray {

    public Ray(Point head, Vector direction) {
        this.head = head;
        direction.normalize();

        this.direction = direction;
    }

    @Override public String toString() {
        return "Ray {" + head + ", " + direction + "}";
    }

    @Override public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null) return false;

        return (obj instanceof Ray other) && head.equals(other.head) && direction.equals(other.direction);
    }


    private final Point head;
    private final Vector direction;
}
