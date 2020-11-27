package com.gmail.chickenpowerrr.aoc2020.helper.path;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class Node {

  private final String name;
  private final Collection<Edge> edges;

  public Node(String name) {
    this.name = name;
    this.edges = new ArrayList<>();
  }

  public String getName() {
    return this.name;
  }

  public void addEdge(Edge edge) {
    this.edges.add(edge);
  }

  public Collection<Edge> getEdges() {
    return Collections.unmodifiableCollection(this.edges);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Node node = (Node) o;
    return Objects.equals(this.name, node.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.name);
  }
}
