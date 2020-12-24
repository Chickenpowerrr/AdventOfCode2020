package com.gmail.chickenpowerrr.aoc2020.days;

import com.gmail.chickenpowerrr.aoc2020.framework.Day;
import com.gmail.chickenpowerrr.aoc2020.helper.file.FileHelper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Day24 implements Day {

  private static final List<Direction> DIRECTIONS = List.of(
      new Direction("e", 2, 0),
      new Direction("se", 1, -1),
      new Direction("sw", -1, -1),
      new Direction("w", -2, 0),
      new Direction("nw", -1, 1),
      new Direction("ne", 1, 1));

  private final FileHelper fileHelper;

  public Day24() {
    this.fileHelper = new FileHelper();
  }

  @Override
  public void partOne() {
    Set<Tile> tiles = new HashSet<>();
    for (Tile tile : getTargetTiles()) {
      if (tiles.contains(tile)) {
        tiles.remove(tile);
      } else {
        tiles.add(tile);
      }
    }

    System.out.println(tiles.size());
  }

  @Override
  public void partTwo() {
    Set<Tile> tiles = new HashSet<>();
    for (Tile tile : getTargetTiles()) {
      if (tiles.contains(tile)) {
        tiles.remove(tile);
      } else {
        tiles.add(tile);
      }
    }

    for (int i = 0; i < 100; i++) {
      tiles = simulateRound(tiles);
    }

    System.out.println(tiles.size());
  }

  private Set<Tile> simulateRound(Set<Tile> tiles) {
    Set<Tile> result = new HashSet<>();

    for (Tile tile : tiles) {
      if (shouldBeAdded(tile, tiles)) {
        result.add(tile);
      }

      for (Direction direction : DIRECTIONS) {
        Tile target = new Tile(tile.getX() + direction.getDeltaX(),
            tile.getY() + direction.getDeltaY());
        if (shouldBeAdded(target, tiles)) {
          result.add(target);
        }
      }
    }

    return result;
  }

  private boolean shouldBeAdded(Tile target, Set<Tile> floor) {
    boolean isBlack = floor.contains(target);
    int neighbours = 0;

    for (Direction direction : DIRECTIONS) {
      Tile neighbour = new Tile(target.x + direction.deltaX, target.y + direction.deltaY);
      boolean neighbourIsBlack = floor.contains(neighbour);
      if (neighbourIsBlack) {
        neighbours++;
      }
    }

    if (isBlack && (neighbours == 1 || neighbours == 2)) {
      return true;
    }

    return !isBlack && neighbours == 2;
  }

  private List<Tile> getTargetTiles() {
    return this.fileHelper.readFileLines("day24/input")
        .map(this::getTile)
        .collect(Collectors.toList());
  }

  private Tile getTile(String line) {
    int x = 0;
    int y = 0;

    for (Direction direction : splitLine(line)) {
      x += direction.getDeltaX();
      y += direction.getDeltaY();
    }

    return new Tile(x, y);
  }

  private List<Direction> splitLine(String line) {
    List<Direction> result = new ArrayList<>();
    splitLine(line, result);
    return result;
  }

  private void splitLine(String line, List<Direction> list) {
    if (line.isEmpty()) {
      return;
    }

    for (Direction direction : DIRECTIONS) {
      if (line.startsWith(direction.getName())) {
        list.add(direction);
        splitLine(line.substring(direction.getName().length()), list);
        break;
      }
    }
  }

  private static final class Tile {
    private final int x;
    private final int y;

    public Tile(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public int getX() {
      return x;
    }

    public int getY() {
      return y;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Tile tile = (Tile) o;
      return x == tile.x && y == tile.y;
    }

    @Override
    public int hashCode() {
      return Objects.hash(x, y);
    }

    @Override
    public String toString() {
      return "Tile{" +
          "x=" + x +
          ", y=" + y +
          '}';
    }
  }

  private static final class Direction {
    private final String name;
    private final int deltaX;
    private final int deltaY;

    public Direction(String name, int deltaX, int deltaY) {
      this.name = name;
      this.deltaX = deltaX;
      this.deltaY = deltaY;
    }

    public String getName() {
      return name;
    }

    public int getDeltaX() {
      return deltaX;
    }

    public int getDeltaY() {
      return deltaY;
    }

    @Override
    public String toString() {
      return "Direction{" +
          "name='" + name + '\'' +
          ", deltaX=" + deltaX +
          ", deltaY=" + deltaY +
          '}';
    }
  }
}
