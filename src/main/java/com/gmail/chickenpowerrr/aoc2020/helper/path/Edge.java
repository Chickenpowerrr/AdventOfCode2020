package com.gmail.chickenpowerrr.aoc2020.helper.path;

import java.util.Objects;

public class Edge {

  private final double weight;
  private final Node from;
  private final Node to;

  public Edge(Node from, Node to, double weight) {
    this.from = from;
    this.to = to;
    this.weight = weight;
  }

  public double getWeight() {
    return this.weight;
  }

  public Node getFrom() {
    return this.from;
  }

  public Node getTo() {
    return this.to;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Edge edge = (Edge) o;
    return Double.compare(edge.weight, this.weight) == 0 &&
        Objects.equals(this.from, edge.from) &&
        Objects.equals(this.to, edge.to);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.weight, this.from, this.to);
  }
}
