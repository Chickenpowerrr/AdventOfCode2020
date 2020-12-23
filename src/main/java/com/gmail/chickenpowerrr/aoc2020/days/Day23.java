package com.gmail.chickenpowerrr.aoc2020.days;

import com.gmail.chickenpowerrr.aoc2020.framework.Day;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day23 implements Day {

  private static final String INPUT = "963275481";
  private static final int EXTREME_CUPS = 1_000_000;

  @Override
  public void partOne() {
    System.out.println(gameToString(simulateGame(100, getStart())));
  }

  @Override
  public void partTwo() {
    List<Integer> positions = simulateGame(10_000_000, getExtremeStart());
    System.out.println((long) positions.get(0) * (long) positions.get(1));
  }

  private String gameToString(List<Integer> game) {
    StringBuilder result = new StringBuilder();
    int startingIndex = game.indexOf(1);

    for (int i = 1; i < game.size(); i++) {
      result.append(game.get((startingIndex + i) % game.size()));
    }

    return result.toString();
  }

  private List<Integer> simulateGame(int rounds, List<Integer> start) {
    Map<Integer, Integer> position = getGraph(start);

    int target = start.get(0);
    for (int i = 0; i < rounds; i++) {
      simulateRound(position, target);
      target = position.get(target);
    }

    List<Integer> result = new ArrayList<>();
    result.add(position.get(1));
    for (int i = 1; i < position.size(); i++) {
      result.add(position.get(result.get(result.size() - 1)));
    }

    return result;
  }

  private Map<Integer, Integer> getGraph(List<Integer> start) {
    Map<Integer, Integer> graph = new HashMap<>();
    for (int i = 0; i < start.size(); i++) {
      graph.put(start.get(i), start.get((i + 1) % start.size()));
    }
    return graph;
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

  private void simulateRound(Map<Integer, Integer> position, Integer target) {
    List<Integer> moving = new ArrayList<>();
    int movingCursor = target;
    for (int i = 0; i < 3; i++) {
      movingCursor = position.get(movingCursor);
      moving.add(movingCursor);
    }

    int movePosition = (target + position.size() - 1) % position.size();
    movePosition = movePosition == 0 ? position.size() : movePosition;
    while (moving.contains(movePosition)) {
      movePosition = (movePosition + position.size() - 1) % position.size();
      movePosition = movePosition == 0 ? position.size() : movePosition;
    }

    position.put(target, position.get(moving.get(moving.size() - 1)));
    position.put(moving.get(moving.size() - 1), position.get(movePosition));
    position.put(movePosition, moving.get(0));
  }
}
