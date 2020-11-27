package com.gmail.chickenpowerrr.aoc2020.helper.path;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Path implements Comparable<Path> {

  private double length;
  private final List<Edge> edges;
  private final Collection<Node> nodes;

  public Path() {
    this.nodes = new HashSet<>();
    this.edges = new ArrayList<>();
  }

  private Path(List<Edge> edges, Collection<Node> nodes, double length) {
    this.edges = edges;
    this.nodes = nodes;
    this.length = length;
  }

  public Path addEdge(Edge edge) {
    Path copy = new Path(new ArrayList<>(this.edges), new HashSet<>(this.nodes), this.length);

    copy.edges.add(edge);
    copy.nodes.add(edge.getFrom());
    copy.nodes.add(edge.getTo());
    copy.length += edge.getWeight();

    return copy;
  }

  public Node getLastNode() {
    return this.edges.get(this.edges.size() - 1).getTo();
  }

  public boolean containsNode(Node node) {
    return this.nodes.contains(node);
  }

  public double getLength() {
    return this.length;
  }

  @Override
  public int compareTo(Path other) {
    return Double.compare(this.length, other.length);
  }

  @Override
  public String toString() {
    String path = this.edges.stream()
        .map(edge -> "-" + edge.getWeight() + "-" + edge.getTo().getName())
        .collect(Collectors.joining("", this.edges.get(0).getFrom().getName(), ""));

    return "Path{" +
        "length=" + this.length +
        ", path=" + path +
        '}';
  }
}
