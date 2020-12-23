package com.gmail.chickenpowerrr.aoc2020.days;

import com.gmail.chickenpowerrr.aoc2020.framework.Day;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Day23 implements Day {

  private static final String INPUT = "963275481";
  private static final int EXTREME_CUPS = 1_000_000;

  @Override
  public void partOne() {
    System.out.println(gameToString(simulateGame(100, getStart())));
  }

  @Override
  public void partTwo() {
    System.out.println(gameToString(simulateGame(10_000_000, getExtremeStart())));
  }

  private String gameToString(List<Integer> game) {
    StringBuilder result = new StringBuilder();
    int startingIndex = game.indexOf(1);

    for (int i = 1; i < game.size(); i++) {
      result.append(game.get((startingIndex + i) % game.size()));
    }

    return result.toString();
  }

  // get
  // index
  // size
  private List<Integer> simulateGame(int rounds, List<Integer> start) {
    List<Integer> position = new ArrayList<>(start);
    int target = position.get(0);
    for (int i = 0; i < rounds; i++) {
      System.out.println(i);
      position = simulateRound(position, target);
      target = position.get((position.indexOf(target) + 1) % position.size());
    }
    return position;
  }

  private List<Integer> getStart() {
    List<Integer> start = new ArrayList<>();

    for (char c : INPUT.toCharArray()) {
      start.add(Integer.parseInt(Character.toString(c)));
    }

    return start;
  }

  private List<Integer> getExtremeStart() {
    ArrayList<Integer> start = new ArrayList<>(getStart());
    start.ensureCapacity(EXTREME_CUPS);
    for (int i = start.size() + 1; i <= EXTREME_CUPS; i++) {
      start.add(i);
    }

    return start;
  }

  // index
  // get
  // size
  // removeAll
  // addAll
  private List<Integer> simulateRound(List<Integer> position, Integer target) {
    List<Integer> nextPosition = new LinkedList<>(position);

    int targetIndex = position.indexOf(target);
    List<Integer> moving = new LinkedList<>();

    for (int i = 1; i <= 3; i++) {
      moving.add(position.get((targetIndex + i) % position.size()));
    }

    nextPosition.removeAll(moving);

    int next = (target + position.size() - 1) % position.size();
    next = next == 0 ? position.size() : next;
    for (int i = 0; i < position.size(); i++) {
      if (moving.contains(next)) {
        next = (next + position.size() - 1) % position.size();
        next = next == 0 ? position.size() : next;
      }
    }

    int movingIndex = nextPosition.indexOf(next) + 1;
    nextPosition.addAll(movingIndex, moving);

    return nextPosition;
  }
}
