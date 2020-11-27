package com.gmail.chickenpowerrr.aoc2020.helper.path;

import java.util.HashMap;
import java.util.Map;

public class Graph {

  private final Map<String, Node> nodes;

  public Graph() {
    this.nodes = new HashMap<>();
  }

  public Node addNode(String name) {
    if (this.nodes.containsKey(name)) {
      return this.nodes.get(name);
    } else {
      Node node = new Node(name);
      this.nodes.put(name, node);
      return node;
    }
  }

  public Node getNode(String name) {
    return this.nodes.get(name);
  }

  public void addDirectedEdge(String from, String to, double weight) {
    Node fromNode = addNode(from);
    Node toNode = addNode(to);
    Edge edge = new Edge(fromNode, toNode, weight);

    fromNode.addEdge(edge);
  }

  public void addUndirectedEdge(String from, String to, double weight) {
    addDirectedEdge(from, to, weight);
    addDirectedEdge(to, from, weight);
  }
}
