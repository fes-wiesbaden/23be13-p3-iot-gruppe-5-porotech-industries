package com.porotech.backend.utils.pathfinding;

import com.porotech.backend.map.Map3D;

import java.awt.*;
import java.util.*;
import java.util.List;

public class AStar {
    private final Map3D map;

    public AStar (Map3D map) {
        this.map = map;
    }

    public class AStarNode {
        final int X, Y;
        public float G;
        public float H;
        public float F = G + H;
        public AStarNode Connection;

        AStarNode(int x, int y) {
            X = x;
            Y = y;
        }

        public void SetG(float g) {
            G = g;
            F = H + G;
        }

        public void SetH(float h) {
            H = h;
            F = H + G;
        }

        public void SetConnection(AStarNode connection) {
            Connection = connection;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            AStarNode other = (AStarNode) obj;
            return X == other.X && Y == other.Y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(X, Y);
        }
    }

    private float heuristic(AStarNode a, AStarNode b) {
        float dx = a.X - b.X, dy = a.Y - b.Y; //euclidian
        return (float)Math.hypot(dx, dy);

        //return Math.abs(a.X - b.X) + Math.abs(a.Y - b.Y); //manhatten no diagonal movement
    }

    private float distance(AStarNode a, AStarNode b) {
        return heuristic(a, b);
    }

    public List<Point> findPath(Point startPoint, Point endPoint) {
        AStarNode startNode = new AStarNode(startPoint.x, startPoint.y);
        AStarNode endNode = new AStarNode(endPoint.x, endPoint.y);

        List<AStarNode> pathNodes = findPathAStar(startNode, endNode);

        return reconstructPath(pathNodes);
    }

    private List<Point> reconstructPath(List<AStarNode> pathNodes) {
        List<Point> path = new ArrayList<>();

        for (AStarNode node : pathNodes) {
            path.add(new Point(node.X, node.Y));
        }

        return path;
    }

    private List<AStarNode> getNeighbors(AStarNode node) {
        List<AStarNode> neighbors = new ArrayList<>();
        int[][] grid = map.getGrid();

        int[][] directions = {
                {0, 1},
                {1, 0},
                {0, -1},
                {-1, 0},
                {-1, -1},
                {1, 1},
                {-1, 1},
                {1, -1},
        };

        for (int[] direction : directions) {
            int newX = node.X + direction[0];
            int newY = node.Y + direction[1];

            if (newX >= 0 && newX < grid.length &&
                    newY >= 0 && newY < grid[0].length &&
                    grid[newX][newY] != 2) {

                neighbors.add(new AStarNode(newX, newY));
            }
        }

        return neighbors;
    }


        List<AStarNode> toSearch = new ArrayList<>();
        private List<AStarNode> findPathAStar(AStarNode startNode, AStarNode endNode) {
        toSearch.add(startNode);

        map.getGrid()[2][2] = 2;
        map.getGrid()[2][3] = 2;
        map.getGrid()[2][4] = 2;
        map.getGrid()[2][5] = 2;
        map.getGrid()[2][6] = 2;
        map.getGrid()[2][7] = 2;
        map.getGrid()[3][2] = 2;
        map.getGrid()[4][2] = 2;
        map.getGrid()[5][2] = 2;
        map.getGrid()[6][2] = 2;
        map.getGrid()[7][2] = 2;

        List<AStarNode> processed = new ArrayList<>();

        while (!toSearch.isEmpty()) {
            AStarNode current = toSearch.getFirst();

            for (AStarNode t : toSearch) {
                if (t.F < current.F || t.F == current.F && t.H < current.H) {
                    current = t;
                }
            }

            processed.add(current);
            toSearch.remove(current);

            if (current.equals((endNode))) {
                List<AStarNode> path = new LinkedList<>();
                AStarNode temp = current;
                while (temp != null) {
                    path.addFirst(temp);
                    temp = temp.Connection;
                }
                return path;
            }

            for (AStarNode neighbor : getNeighbors(current)) {
                boolean inSearch = toSearch.contains(neighbor);

                float costToNeighbor = current.G + distance(current, neighbor);

                if (map.getGrid()[neighbor.X][neighbor.Y] == 2) { //obstacle
                    continue;
                }

                if (!inSearch || costToNeighbor < neighbor.G) {
                    neighbor.SetG(costToNeighbor);
                    neighbor.SetConnection(current);

                    if (!inSearch) {
                        neighbor.SetH(heuristic(neighbor, endNode));
                        toSearch.add(neighbor);
                    }
                }
            }
        }

        return processed;
    }
}