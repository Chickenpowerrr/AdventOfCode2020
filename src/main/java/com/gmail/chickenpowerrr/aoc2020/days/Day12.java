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
    MovingObject ship = new MovingObject(0, 0, 1);

    this.fileHelper.readFileLines("day12/input")
        .map(line -> new AllShipInstruction(line.charAt(0), Integer.parseInt(line.substring(1))))
        .forEach(instruction -> instruction.apply(ship));

    System.out.println(ship.getTravelled());
  }

  @Override
  public void partTwo() {
    MovingObject ship = new MovingObject(0, 0, 1);
    MovingObject waypoint = new MovingObject(1, 10, 1);

    this.fileHelper.readFileLines("day12/input")
        .map(line -> new ShipWaypointInstruction(
            line.charAt(0), Integer.parseInt(line.substring(1))))
        .forEach(instruction -> instruction.apply(ship, waypoint));

    System.out.println(ship.getTravelled());
  }

  private static class AllShipInstruction {
    private final char action;
    private final int argument;

    public AllShipInstruction(char action, int argument) {
      this.action = action;
      this.argument = argument;
    }

    public void apply(MovingObject ship) {
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

    private void applyForward(MovingObject ship) {
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

    private void applyMovement(MovingObject ship) {
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

  private static class ShipWaypointInstruction {
    private final char action;
    private final int argument;

    public ShipWaypointInstruction(char action, int argument) {
      this.action = action;
      this.argument = argument;
    }

    public void apply(MovingObject ship, MovingObject waypoint) {
      if (isRotation()) {
        applyRotation(waypoint);
      } else if (isForward()) {
        applyForward(ship, waypoint);
      } else {
        applyMovement(waypoint);
      }
    }

    private boolean isRotation() {
      return this.action == 'R' || this.action == 'L';
    }

    private boolean isForward() {
      return this.action == 'F';
    }

    private void applyRotation(MovingObject waypoint) {
      int delta = (this.action == 'R' ? 1 : -1) * this.argument / 90;
      int direction = (4 + delta) % 4;
      int newNorth;

      switch (direction) {
        case 0:
          break;
        case 1:
          newNorth = -waypoint.east;
          waypoint.east = waypoint.north;
          waypoint.north = newNorth;
          break;
        case 2:
          waypoint.north *= -1;
          waypoint.east *= -1;
          break;
        case 3:
          newNorth = waypoint.east;
          waypoint.east = -waypoint.north;
          waypoint.north = newNorth;
          break;
      }
    }

    private void applyForward(MovingObject ship, MovingObject waypoint) {
      ship.north += this.argument * waypoint.north;
      ship.east += this.argument * waypoint.east;
    }

    private void applyMovement(MovingObject waypoint) {
      switch (this.action) {
        case 'N':
          waypoint.north += this.argument;
          break;
        case 'E':
          waypoint.east += this.argument;
          break;
        case 'S':
          waypoint.north -= this.argument;
          break;
        case 'W':
          waypoint.east -= this.argument;
          break;
      }
    }
  }

  private static class MovingObject {
    private int north;
    private int east;
    private int direction;

    public MovingObject(int north, int east, int direction) {
      this.north = north;
      this.east = east;
      this.direction = direction;
    }

    public int getTravelled() {
      return Math.abs(north) + Math.abs(east);
    }
  }
}
