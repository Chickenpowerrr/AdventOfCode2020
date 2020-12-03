package com.gmail.chickenpowerrr.aoc2020.days;

import com.gmail.chickenpowerrr.aoc2020.framework.Day;
import com.gmail.chickenpowerrr.aoc2020.helper.file.FileHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day03 implements Day {

  private final FileHelper fileHelper;

  public Day03() {
    this.fileHelper = new FileHelper();
  }

  private List<List<Node>> getArea() {
    return this.fileHelper.readFileLines("day03/input")
        .map(line -> line.repeat(1000))
        .map(line -> {
          List<Node> nodes = new ArrayList<>();
          for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            nodes.add(new Node(i, c == '#'));
          }
          return nodes;
        }).collect(Collectors.toList());
  }

  private long treesAtSlope(List<List<Node>> area, int deltaX, int deltaY) {
    int trees = 0;
    for (int i = 0; i * deltaY < area.size(); i++) {
      if (area.get(i * deltaY).get(i * deltaX).isTree()) {
        trees++;
      }
    }
    return trees;
  }

  @Override
  public void partOne() {
    System.out.println(treesAtSlope(getArea(), 3, 1));
  }

  @Override
  public void partTwo() {
    long totalTrees = treesAtSlope(getArea(), 1, 1)
        * treesAtSlope(getArea(), 3, 1)
        * treesAtSlope(getArea(), 5, 1)
        * treesAtSlope(getArea(), 7, 1)
        * treesAtSlope(getArea(), 1, 2);

    System.out.println(totalTrees);
  }

  private static class Node {
    private final int location;
    private final boolean tree;

    public Node(int location, boolean tree) {
      this.location = location;
      this.tree = tree;
    }

    public int getLocation() {
      return location;
    }

    public boolean isTree() {
      return tree;
    }
  }
}
