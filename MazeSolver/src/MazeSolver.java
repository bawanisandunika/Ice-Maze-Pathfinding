import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MazeSolver {

    public static final char EMPTY = '.';
    public static final char ROCK = '0';
    public static final char START = 'S';
    public static final char FINISH = 'F';

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java MazeSolver <input_file>");
            return;
        }

        String fileName = args[0];
        char[][] map = readMap(fileName);

        Point start = findPoint(map, START);
        Point finish = findPoint(map, FINISH);

        List<Point> path = findShortestPath(map, start, finish);

        if (path == null) {
            System.out.println("No path found!");
        } else {
            System.out.println("Shortest path found in " + path.size() + " steps:");
            for (int i = 0; i < path.size(); i++) {
                System.out.println((i + 1) + ". Move " + getDirection(path.get(i), i > 0 ? path.get(i - 1) : null) + " to (" + path.get(i).x + "," + path.get(i).y + ")");
            }
            System.out.println("Done!");
        }
    }

    private static char[][] readMap(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();

        int width = lines.get(0).length();
        int height = lines.size();
        char[][] map = new char[height][width];
        for (int y = 0; y < height; y++) {
            String row = lines.get(y);
            for (int x = 0; x < width; x++) {
                map[y][x] = row.charAt(x);
            }
        }
        return map;
    }

    private static Point findPoint(char[][] map, char target) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == target) {
                    return new Point(x, y);
                }
            }
        }
        return null;
    }

    private static List<Point> findShortestPath(char[][] map, Point start, Point finish) {
        int height = map.length;
        int width = map[0].length;

        // Create a visited set to keep track of explored points
        boolean[][] visited = new boolean[height][width];

        // Use a priority queue to prioritize exploring points with lower distances
        PriorityQueue<Point> queue = new PriorityQueue<>((p1, p2) -> Integer.compare(p1.distance, p2.distance));

        // Set distance of start point to 0 and add it to the queue
        start.distance = 0;
        queue.add(start);

        // Dijkstra's algorithm loop
        while (!queue.isEmpty()) {
            Point current = queue.poll();
            if (visited[current.y][current.x]) {
                continue;
            }
            visited[current.y][current.x] = true;

            if (current.equals(finish)) {
                // Reconstruct the path by backtracking from finish
                return reconstructPath(current);
            }

            // Explore neighbors (up, down, left, right)
            for (int dy = -1; dy <= 1; dy++) {
                for (int dx = -1; dx <= 1; dx++) {
                    if (dy != 0 && dx != 0) continue; // Skip diagonal moves
                    int newY = current.y + dy;
                    int newX = current.x + dx;

                    // Check if neighbor is within bounds, not visited, and not a rock
                    if (isValidPoint(map, newY, newX, visited, ROCK)) {
                        int newDistance = current.distance + 1;
                        if (relaxPoint(queue, map, visited, newDistance, newY, newX)) {
                            map[newY][newX] = 'x'; // Mark as visited in the map for visualization
                        }
                    }
                }
            }
        }

        return null; // No path found
    }

    private static boolean isValidPoint(char[][] map, int y, int x, boolean[][] visited, char obstacle) {
        return y >= 0 && y < map.length && x >= 0 && x < map[0].length && !visited[y][x] && map[y][x] != obstacle;
    }

    private static boolean relaxPoint(PriorityQueue<Point> queue, char[][] map, boolean[][] visited, int newDistance, int y, int x) {
        visited[y][x] = true;
        Point neighbor = new Point(x, y);
        neighbor.distance = newDistance;
        queue.add(neighbor);
        return true;
    }

    private static List<Point> reconstructPath(Point finish) {
        List<Point> path = new ArrayList<>();
        Point current = finish;
        while (current != null) {
            path.add(current);
            current = current.previous;
        }
        Collections.reverse(path);
        return path;
    }

    private static String getDirection(Point current, Point previous) {
        if (previous == null) return "Start";
        if (current.x == previous.x) {
            if (current.y < previous.y) return "Up";
            else return "Down";
        } else {
            if (current.x < previous.x) return "Left";
            else return "Right";
        }
    }

    static class Point {
        int x;
        int y;
        int distance; // Distance from start
        Point previous; // Previous point in the path

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Point)) return false;
            Point other = (Point) obj;
            return this.x == other.x && this.y == other.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
