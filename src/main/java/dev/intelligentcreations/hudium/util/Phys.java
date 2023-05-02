package dev.intelligentcreations.hudium.util;

public final class Phys {
    public record Bounds(int width, int height) {}

    public static class Position {
        private int x;
        private int y;

        public Position(int x, int y) {

            this.x = x;
            this.y = y;
        }

        public int x() {
            return x;
        }

        public int y() {
            return y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        public void add(int x, int y) {
            this.x += x;
            this.y += y;
        }

        public void add(Position pos) {
            this.x += pos.x;
            this.y += pos.y;
        }
    }
}
