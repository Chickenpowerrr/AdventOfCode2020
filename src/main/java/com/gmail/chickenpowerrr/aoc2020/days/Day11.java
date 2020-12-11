package com.gmail.chickenpowerrr.aoc2020.days;

import com.gmail.chickenpowerrr.aoc2020.framework.Day;
import com.gmail.chickenpowerrr.aoc2020.helper.file.FileHelper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Day11 implements Day {

  private final FileHelper fileHelper;

  public Day11() {
    this.fileHelper = new FileHelper();
  }

  @Override
  public void partOne() {
    Collection<Seat> seats = getSeats();

    while (seats.stream().filter(Seat::applyNext).count() > 0) {
      seats.forEach(Seat::calculateNext);
    }

    System.out.println(seats.stream().filter(Seat::isOccupied).count());
  }

  @Override
  public void partTwo() {

  }

  private Collection<Seat> getSeats() {
    List<List<Seat>> seats = this.fileHelper.readFileLines("day11/input")
        .map(line ->
            line.codePoints().mapToObj(c -> (char) c)
                .map(character -> {
                  if (character == 'L') {
                    return new Seat();
                  } else {
                    return null;
                  }
                }).collect(Collectors.toList()))
        .collect(Collectors.toList());

    List<Seat> result = new ArrayList<>();

    for (int i = 0; i < seats.size(); i++) {
      for (int j = 0; j < seats.get(i).size(); j++) {
        Seat target = seats.get(i).get(j);

        if (target != null) {
          result.add(target);
          for (int k = -1; k <= 1; k++) {
            for (int l = -1; l <= 1; l++) {
              if (k == 0 && l == 0) {
                continue;
              }

              if (i + k >= 0 && seats.size() > i + k
                  && j + l >= 0 && seats.get(i).size() > j + l) {
                Seat cursor = seats.get(i + k).get(j + l);
                if (cursor != null) {
                  target.addAdjacent(cursor);
                }
              }
            }
          }
        }
      }
    }

    return result;
  }

  private static class Seat {
    private final Collection<Seat> adjacent;

    private boolean occupied;
    private boolean nextOccupied;

    public Seat() {
      this.occupied = false;
      this.nextOccupied = true;
      this.adjacent = new ArrayList<>();
    }

    public boolean isOccupied() {
      return occupied;
    }

    public void calculateNext() {
      if (isOccupied()) {
        this.nextOccupied = this.adjacent.stream().filter(Seat::isOccupied).count() < 4;
      } else {
        this.nextOccupied = this.adjacent.stream().noneMatch(Seat::isOccupied);
      }
    }

    public void addAdjacent(Seat seat) {
      this.adjacent.add(seat);
    }

    public boolean applyNext() {
      boolean changed = occupied != nextOccupied;
      this.occupied = nextOccupied;
      return changed;
    }

    public int getAdjacentCount() {
      return this.adjacent.size();
    }
  }
}
