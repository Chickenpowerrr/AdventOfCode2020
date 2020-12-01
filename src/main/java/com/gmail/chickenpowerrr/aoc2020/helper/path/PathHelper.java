package com.gmail.chickenpowerrr.aoc2020.helper.path;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class PathHelper {

  public Path getShortestPath(Graph graph, String from, String to) {
    if (from.equals(to)) {
      return new Path();
    }

    Node toNode = graph.getNode(to);
    Map<Node, Double> distanceToNodes = new HashMap<>();
    PriorityQueue<Path> paths = getInitialPaths(graph, from, distanceToNodes);

    Path currentPath;
    while ((currentPath = paths.poll()) != null) {
      Node lastNode = currentPath.getLastNode();
      for (Edge edge : lastNode.getEdges()) {
        if (currentPath.containsNode(edge.getTo())) {
          continue;
        }

        Path newPath = currentPath.addEdge(edge);
        boolean isBetterPath = !distanceToNodes.containsKey(newPath.getLastNode())
            || distanceToNodes.get(newPath.getLastNode()) > newPath.getLength();

        if (newPath.getLastNode().equals(toNode)) {
          return newPath;
        }

        if (isBetterPath) {
          paths.add(newPath);
        }
      }
    }

    return null;
  }

  private PriorityQueue<Path> getInitialPaths(Graph graph, String from,
      Map<Node, Double> distanceToNodes) {
    Node startNode = graph.getNode(from);
    distanceToNodes.putIfAbsent(startNode, 0D);

    PriorityQueue<Path> paths = new PriorityQueue<>();

    for (Edge edge : startNode.getEdges()) {
      Path path = new Path().addEdge(edge);
      paths.add(path);

      distanceToNodes.put(edge.getTo(), edge.getWeight());
    }

    return paths;
  }
}
