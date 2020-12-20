package com.gmail.chickenpowerrr.aoc2020.days;

import com.gmail.chickenpowerrr.aoc2020.framework.Day;
import com.gmail.chickenpowerrr.aoc2020.helper.file.FileHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day20 implements Day {

  private static final int TILE_HORIZONTAL = 10;
  private static final int TILE_VERTICAL = TILE_HORIZONTAL;

  private final FileHelper fileHelper;

  public Day20() {
    this.fileHelper = new FileHelper();
  }

  @Override
  public void partOne() {
    Grid grid = getGrid();

    int max = grid.grid.length - 1;
    System.out.println(grid.getId(0, 0) * grid.getId(0, max)
        * grid.getId(max, 0) * grid.getId(max, max));
  }

  @Override
  public void partTwo() {
    Tile tile = getNormalizedGrid();
    int seaMonsterTiles = getSeaMonsterCount(tile) * 15;
    int totalTiles = getTileCount(tile);

    System.out.println(totalTiles - seaMonsterTiles);
  }

  private int getTileCount(Tile tile) {
    int count = 0;

    for (boolean[] line : tile.getTileRotations().get(0).grid) {
      for (boolean b : line) {
        if (b) {
          count++;
        }
      }
    }

    return count;
  }

  private int getSeaMonsterCount(Tile tile) {
    int seaMonsterCount = 0;
    for (TileRotation rotation : tile.getTileRotations()) {
      int count = 0;
      for (int y = 0; y < rotation.grid.length; y++) {
        for (int x = 0; x < rotation.grid.length; x++) {
          if (isSeaMonster(rotation, x, y)) {
            count++;
          }
        }
      }

      seaMonsterCount = Math.max(seaMonsterCount, count);
    }

    return seaMonsterCount;
  }

  private boolean isSeaMonster(TileRotation tileRotation, int x, int y) {
    if (x < 0 || y < 0 || y + 2 >= tileRotation.grid.length || x + 19 >= tileRotation.grid.length) {
      return false;
    }

    return tileRotation.grid[y + 1][x]
        && tileRotation.grid[y + 2][x + 1]
        && tileRotation.grid[y + 2][x + 4]
        && tileRotation.grid[y + 1][x + 5]
        && tileRotation.grid[y + 1][x + 6]
        && tileRotation.grid[y + 2][x + 7]
        && tileRotation.grid[y + 2][x + 10]
        && tileRotation.grid[y + 1][x + 11]
        && tileRotation.grid[y + 1][x + 12]
        && tileRotation.grid[y + 2][x + 13]
        && tileRotation.grid[y + 2][x + 16]
        && tileRotation.grid[y + 1][x + 17]
        && tileRotation.grid[y][x + 18]
        && tileRotation.grid[y + 1][x + 18]
        && tileRotation.grid[y + 1][x + 19];
  }

  private Tile getNormalizedGrid() {
    Grid grid = getGrid();

    int sideLength = grid.grid.length * TILE_HORIZONTAL;
    int newSideLength = grid.grid.length * (TILE_HORIZONTAL - 2);
    boolean[][] normalizedGrid = new boolean[newSideLength][newSideLength];

    for (int y = 0; y < sideLength; y++) {
      int blockY = y / TILE_VERTICAL;
      int relativeY = y % TILE_VERTICAL;

      if (relativeY == 0 || relativeY + 1 == TILE_VERTICAL) {
        continue;
      }

      for (int x = 0; x < sideLength; x++) {
        int blockX = x / TILE_HORIZONTAL;
        int relativeX = x % TILE_HORIZONTAL;

        if (relativeX == 0 || relativeX + 1 == TILE_VERTICAL) {
          continue;
        }

        boolean value = grid.grid[blockY][blockX].grid[relativeY][relativeX];

        normalizedGrid[blockY * (TILE_VERTICAL - 2) + relativeY - 1]
            [blockX * (TILE_HORIZONTAL - 2) + relativeX - 1] = value;
      }
    }

    return new Tile(-1, new TileRotation(normalizedGrid));
  }

  private Grid getGrid() {
    List<Tile> tiles = getTiles();
    int gridSide = (int) Math.sqrt(tiles.size());

    return generateGrid(new Grid(gridSide), tiles, 0, 0, gridSide);
  }

  private Grid generateGrid(Grid grid, List<Tile> remainingParts, int x, int y, int gridSide) {
    int nextX = (x + 1) % gridSide;
    int nextY = nextX == 0 ? y + 1 : y;

    for (Tile tile : remainingParts) {
      for (TileRotation tileRotation : tile.getTileRotations()) {
        if (grid.isValid(tileRotation, x, y)) {
          Grid nextGrid = grid.copy();
          nextGrid.setTile(tile, tileRotation, x, y);
          List<Tile> nextRemainingPart = new ArrayList<>(remainingParts);
          nextRemainingPart.remove(tile);

          if (nextY == gridSide) {
            return nextGrid;
          }

          Grid result = generateGrid(nextGrid, nextRemainingPart, nextX, nextY, gridSide);
          if (result != null) {
            return result;
          }
        }
      }
    }

    return null;
  }

  private List<Tile> getTiles() {
    List<Tile> parts = new ArrayList<>();

    boolean[][] rotation = new boolean[TILE_VERTICAL][TILE_HORIZONTAL];
    int id = 0;
    int i = 0;

    for (String line : this.fileHelper.readFileLines("day20/input")
        .filter(line -> !line.isEmpty())
        .collect(Collectors.toList())) {
      if (line.startsWith("Tile")) {
        id = Integer.parseInt(line.split("Tile ")[1].replace(":", ""));
        continue;
      }

      rotation[i] = parseLine(line);

      if (++i == TILE_VERTICAL) {
        parts.add(new Tile(id, new TileRotation(rotation)));

        rotation = new boolean[TILE_VERTICAL][TILE_HORIZONTAL];
        i = 0;
      }
    }

    return parts;
  }

  private boolean[] parseLine(String line) {
    boolean[] result = new boolean[TILE_HORIZONTAL];

    for (int i = 0; i < line.length(); i++) {
      result[i] = line.charAt(i) == '#';
    }

    return result;
  }

  private static class Grid {

    private final TileRotation[][] grid;
    private final int[][] ids;

    public Grid(int gridSide) {
      this.grid = new TileRotation[gridSide][gridSide];
      this.ids = new int[gridSide][gridSide];
    }

    private Grid(TileRotation[][] grid, int[][] ids) {
      this.grid = grid;
      this.ids = ids;
    }

    public boolean isValid(TileRotation target, int x, int y) {
      for (int direction = 0; direction < 4; direction++) {
        int nextX = direction == 1 ? x + 1 : direction == 3 ? x - 1 : x;
        int nextY = direction == 0 ? y - 1 : direction == 2 ? y + 1 : y;

        TileRotation tileRotation = getTileRotation(nextX, nextY);
        if (tileRotation != null && !target.sharesSide(tileRotation, direction)) {
          return false;
        }
      }

      return true;
    }

    public void setTile(Tile tile, TileRotation rotation, int x, int y) {
      this.grid[y][x] = rotation;
      this.ids[y][x] = tile.getId();
    }

    public TileRotation getTileRotation(int x, int y) {
      if (x < 0 || y < 0 || x >= grid[0].length || y >= grid.length) {
        return null;
      }

      return this.grid[y][x];
    }

    public long getId(int x, int y) {
      if (x < 0 || y < 0 || x >= grid[0].length || y >= grid.length) {
        return -1;
      }

      return this.ids[y][x];
    }

    @Override
    public String toString() {
      return Arrays.deepToString(grid);
    }

    public Grid copy() {
      TileRotation[][] nextGrid = new TileRotation[grid.length][grid[0].length];
      int[][] nextIds = new int[ids.length][ids[0].length];

      for (int y = 0; y < nextGrid.length; y++) {
        nextGrid[y] = Arrays.copyOf(grid[y], grid[y].length);
        nextIds[y] = Arrays.copyOf(ids[y], ids[y].length);
      }

      return new Grid(nextGrid, nextIds);
    }
  }

  private static class Tile {

    private final int id;
    private final List<TileRotation> tileRotations;

    public Tile(int id, TileRotation tileRotation) {
      this.id = id;
      this.tileRotations = IntStream.range(0, 4)
          .mapToObj(direction -> Stream.of(true, false)
              .map(flip -> {
                TileRotation result = tileRotation;

                for (int i = 0; i < direction; i++) {
                  result = result.rotate();
                }

                if (flip) {
                  result = result.flip();
                }

                return result;
              })).flatMap(t -> t).collect(Collectors.toList());
    }

    public int getId() {
      return id;
    }

    public List<TileRotation> getTileRotations() {
      return tileRotations;
    }
  }

  private static class TileRotation {

    private final boolean[][] grid;

    public TileRotation(boolean[][] grid) {
      this.grid = grid;
    }

    public boolean sharesSide(TileRotation other, int direction) {
      if (direction == 0) {
        return Arrays.equals(grid[0], other.grid[other.grid.length - 1]);
      } else if (direction == 2) {
        return Arrays.equals(grid[other.grid.length - 1], other.grid[0]);
      } else if (direction == 1) {
        for (int y = 0; y < grid.length; y++) {
          if (grid[y][other.grid.length - 1] != other.grid[y][0]) {
            return false;
          }
        }

        return true;
      } else {
        for (int y = 0; y < grid.length; y++) {
          if (grid[y][0] != other.grid[y][other.grid.length - 1]) {
            return false;
          }
        }

        return true;
      }
    }

    @Override
    public String toString() {
      return Arrays.stream(grid)
          .map(this::lineToString)
          .collect(Collectors.joining("\n"));
    }

    private String lineToString(boolean[] line) {
      StringBuilder result = new StringBuilder();

      for (boolean b : line) {
        result.append(b ? '#' : '.');
      }

      return result.toString();
    }

    public TileRotation flip() {
      boolean[][] result = new boolean[grid.length][grid.length];

      for (int y = 0; y < grid.length; y++) {
        result[y] = Arrays.copyOf(grid[grid.length - y - 1], grid.length);
      }

      return new TileRotation(result);
    }

    private TileRotation rotate() {
      boolean[][] result = new boolean[grid.length][grid.length];

      for (int y = 0; y < grid.length; y++) {
        for (int x = 0; x < grid[y].length; x++) {
          result[y][x] = grid[x][grid.length - y - 1];
        }
      }

      return new TileRotation(result);
    }
  }
}
