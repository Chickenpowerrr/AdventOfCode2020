package com.gmail.chickenpowerrr.aoc2020.days;

import com.gmail.chickenpowerrr.aoc2020.framework.Day;
import com.gmail.chickenpowerrr.aoc2020.helper.file.FileHelper;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day17 implements Day {

  private final FileHelper fileHelper;

  public Day17() {
    this.fileHelper = new FileHelper();
  }

  @Override
  public void partOne() {
    Grid grid = getInput(0, getThreeDimensionalDeltas());
    for (int i = 0; i < 6; i++) {
      grid.simulateRound();
    }

    System.out.println(grid.enabled.size());
  }

  @Override
  public void partTwo() {
    Grid grid = getInput(1, getFourDimensionalDeltas());
    for (int i = 0; i < 6; i++) {
      grid.simulateRound();
    }

    System.out.println(grid.enabled.size());
  }

  private Grid getInput(int deltaW, Collection<Coordinate> deltas) {
    List<String> lines = this.fileHelper.readFileLines("day17/input")
        .collect(Collectors.toList());
    Collection<Coordinate> enabled = new HashSet<>();
    Coordinate dimensions = new Coordinate(1 + lines.get(0).length() / 2, 1 + lines.size() / 2, 1,
        deltaW);

    for (int i = 0; i < lines.size(); i++) {
      String line = lines.get(i);
      for (int j = 0; j < line.length(); j++) {
        if (line.charAt(j) == '#') {
          enabled.add(new Coordinate(j - dimensions.x + 1, i - dimensions.y + 1, 0, 0));
        }
      }
    }

    return new Grid(enabled, dimensions, deltas, deltaW);
  }

  private Collection<Coordinate> getThreeDimensionalDeltas() {
    Collection<Coordinate> deltas = new HashSet<>();
    for (int x = -1; x <= 1; x++) {
      for (int y = -1; y <= 1; y++) {
        for (int z = -1; z <= 1; z++) {
          if (x != 0 || y != 0 || z != 0) {
            deltas.add(new Coordinate(x, y, z, 0));
          }
        }
      }
    }
    return deltas;
  }

  private Collection<Coordinate> getFourDimensionalDeltas() {
    Collection<Coordinate> deltas = new HashSet<>();
    for (int x = -1; x <= 1; x++) {
      for (int y = -1; y <= 1; y++) {
        for (int z = -1; z <= 1; z++) {
          for (int w = -1; w <= 1; w++) {
            if (x != 0 || y != 0 || z != 0 || w != 0) {
              deltas.add(new Coordinate(x, y, z, w));
            }
          }
        }
      }
    }
    return deltas;
  }

  private static class Grid {
    private Coordinate dimensions;
    private Collection<Coordinate> enabled;
    private final Collection<Coordinate> deltas;
    private final int deltaW;

    public Grid(Collection<Coordinate> enabled, Coordinate dimensions,
        Collection<Coordinate> deltas, int deltaW) {
      this.dimensions = dimensions;
      this.enabled = new HashSet<>(enabled);
      this.deltas = deltas;
      this.deltaW = deltaW;
    }

    public void simulateRound() {
      Collection<Coordinate> nextEnabled = new HashSet<>();

      for (int x = -this.dimensions.x; x <= this.dimensions.x; x++) {
        for (int y = -this.dimensions.y; y <= this.dimensions.y; y++) {
          for (int z = -this.dimensions.z; z <= this.dimensions.z; z++) {
            for (int w = -this.dimensions.w; w <= this.dimensions.w; w++) {
              Coordinate target = new Coordinate(x, y, z, w);
              boolean targetEnabled = this.enabled.contains(target);
              int neighbourCount = 0;
              for (Coordinate neighbour : target.getNeighbours(this.deltas)) {
                if (this.enabled.contains(neighbour)) {
                  neighbourCount++;
                }
              }

              if (targetEnabled && (neighbourCount == 2 || neighbourCount == 3)) {
                nextEnabled.add(target);
              } else if (!targetEnabled && neighbourCount == 3) {
                nextEnabled.add(target);
              }
            }
          }
        }
      }

      this.dimensions = new Coordinate(this.dimensions.x + 1, this.dimensions.y + 1,
          this.dimensions.z + 1, this.dimensions.w + this.deltaW);

      this.enabled = nextEnabled;
    }

    @Override
    public String toString() {
      return "Grid{" +
          "dimensions=" + dimensions +
          ", enabled=" + enabled +
          '}';
    }
  }

  private static class Coordinate {

    private Collection<Coordinate> neighbours;

    private final int x;
    private final int y;
    private final int z;
    private final int w;

    public Coordinate(int x, int y, int z, int w) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.w = w;

      this.neighbours = null;
    }

    public Collection<Coordinate> getNeighbours(Collection<Coordinate> deltas) {
      if (this.neighbours != null) {
        return this.neighbours;
      }

      Collection<Coordinate> neighbours = new HashSet<>();
      for (Coordinate delta : deltas) {
        neighbours.add(new Coordinate(this.x + delta.x, this.y + delta.y, this.z + delta.z,
            this.w + delta.w));
      }

      this.neighbours = neighbours;
      return neighbours;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Coordinate that = (Coordinate) o;
      return x == that.x && y == that.y && z == that.z && w == that.w;
    }

    @Override
    public int hashCode() {
      return Objects.hash(x, y, z, w);
    }

    @Override
    public String toString() {
      return "(" + x + ", " + y + ", " + z + ", " + w + ")";
    }
  }
}
