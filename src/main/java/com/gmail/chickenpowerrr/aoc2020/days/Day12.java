package com.gmail.chickenpowerrr.aoc2020.days;

import com.gmail.chickenpowerrr.aoc2020.framework.Day;
import com.gmail.chickenpowerrr.aoc2020.helper.file.FileHelper;

public class Day12 implements Day {

  private final FileHelper fileHelper;

  public Day12() {
    this.fileHelper = new FileHelper();
  }

  @Override
  public void partOne() {
    Ship ship = new Ship(0, 0, 1);

    this.fileHelper.readFileLines("day12/input")
        .map(line -> new Instruction(line.charAt(0), Integer.parseInt(line.substring(1))))
        .forEach(instruction -> instruction.apply(ship));

    System.out.println(ship.getTravelled());
  }

  @Override
  public void partTwo() {

  }

  private static class Instruction {
    private final char action;
    private final int argument;

    public Instruction(char action, int argument) {
      this.action = action;
      this.argument = argument;
    }

    public void apply(Ship ship) {
      if (isRotation()) {
        ship.direction = applyRotation(ship.direction);
      } else if (isForward()) {
        applyForward(ship);
      } else {
        applyMovement(ship);
      }
    }

    private boolean isRotation() {
      return this.action == 'R' || this.action == 'L';
    }

    private boolean isForward() {
      return this.action == 'F';
    }

    private int applyRotation(int direction) {
      int delta = (this.action == 'R' ? 1 : -1) * this.argument / 90;
      return (direction + 4 + delta) % 4;
    }

    private void applyForward(Ship ship) {
      switch (ship.direction) {
        case 0:
          ship.north += this.argument;
          break;
        case 1:
          ship.east += this.argument;
          break;
        case 2:
          ship.north -= this.argument;
          break;
        case 3:
          ship.east -= this.argument;
          break;
      }
    }

    private void applyMovement(Ship ship) {
      switch (this.action) {
        case 'N':
          ship.north += this.argument;
          break;
        case 'E':
          ship.east += this.argument;
          break;
        case 'S':
          ship.north -= this.argument;
          break;
        case 'W':
          ship.east -= this.argument;
          break;
      }
    }
  }

  private static class Ship {
    private int north;
    private int east;
    private int direction;

    public Ship(int north, int east, int direction) {
      this.north = north;
      this.east = east;
      this.direction = direction;
    }

    public int getTravelled() {
      return Math.abs(north) + Math.abs(east);
    }
  }
}
