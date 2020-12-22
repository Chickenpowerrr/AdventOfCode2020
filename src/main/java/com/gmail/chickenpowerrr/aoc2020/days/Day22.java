package com.gmail.chickenpowerrr.aoc2020.days;

import com.gmail.chickenpowerrr.aoc2020.framework.Day;
import com.gmail.chickenpowerrr.aoc2020.helper.file.FileHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day22 implements Day {

  private final FileHelper fileHelper;

  public Day22() {
    this.fileHelper = new FileHelper();
  }

  @Override
  public void partOne() {
    System.out.println(getWinningScore());
  }

  @Override
  public void partTwo() {

  }

  private int getWinningScore() {
    List<Integer> gameResult = simulateGame();
    return IntStream.range(0, gameResult.size())
        .map(i -> (gameResult.size() - i) * gameResult.get(i)).sum();
  }

  private List<Integer> simulateGame() {
    LinkedList<Integer>[] decks = getDecks();

    while (Arrays.stream(decks).noneMatch(List::isEmpty)) {
      int highestIndex = -1;
      int highestScore = -1;

      List<Integer> drawnCards = new ArrayList<>();
      for (LinkedList<Integer> deck : decks) {
        int target = deck.pop();
        drawnCards.add(target);

        if (highestScore < target) {
          highestScore = target;
          highestIndex = drawnCards.size() - 1;
        }
      }

      drawnCards.sort(Collections.reverseOrder());
      decks[highestIndex].addAll(drawnCards);
    }

    return Arrays.stream(decks).filter(list -> !list.isEmpty()).findFirst().get();
  }

  @SuppressWarnings("unchecked")
  private LinkedList<Integer>[] getDecks() {
    LinkedList<Integer>[] decks = new LinkedList[2];
    int index = -1;

    for (String line : this.fileHelper.readFileLines("day22/input")
        .filter(line -> !line.isEmpty()).collect(Collectors.toList())) {
      if (line.startsWith("Player")) {
        index++;
        decks[index] = new LinkedList<>();
        continue;
      }

      decks[index].addLast(Integer.parseInt(line));
    }

    return decks;
  }
}
