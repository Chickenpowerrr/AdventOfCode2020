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
    System.out.println(calculateWinningScore(simulateGame()));
  }

  @Override
  public void partTwo() {
    List<Integer> result = Arrays.stream(simulateRecursiveGame(getDecks()))
        .filter(list -> !list.isEmpty()).findFirst().get();
    System.out.println(calculateWinningScore(result));
  }

  @SuppressWarnings("unchecked")
  private LinkedList<Integer>[] simulateRecursiveGame(LinkedList<Integer>[] startingDecks) {
    List<LinkedList<Integer>[]> previousOrders = new ArrayList<>();

    LinkedList<Integer>[] decks = Arrays.stream(startingDecks)
        .map(LinkedList::new)
        .toArray(LinkedList[]::new);

    while (Arrays.stream(decks).noneMatch(List::isEmpty)) {
      for (LinkedList<Integer>[] order : previousOrders) {
        for (int i = 0; i < order.length; i++) {
          if (order[i].equals(decks[i])) {
            return decks;
          }
        }
      }

      previousOrders.add(Arrays.stream(decks)
          .map(LinkedList::new)
          .toArray(LinkedList[]::new));

      int highestIndex = -1;
      int highestScore = -1;
      boolean shouldRecurse = true;

      List<Integer> drawnCards = new ArrayList<>();
      for (LinkedList<Integer> deck : decks) {
        int target = deck.pop();
        drawnCards.add(target);

        if (shouldRecurse) {
          shouldRecurse = target <= deck.size();
        }

        if (highestScore < target) {
          highestScore = target;
          highestIndex = drawnCards.size() - 1;
        }
      }

      if (shouldRecurse) {
        LinkedList<Integer>[] nextDecks = new LinkedList[decks.length];
        for (int i = 0; i < decks.length; i++) {
          nextDecks[i] = new LinkedList<>(decks[i].subList(0, drawnCards.get(i)));
        }

        LinkedList<Integer>[] recursiveResult = simulateRecursiveGame(nextDecks);
        for (int i = 0; i < recursiveResult.length; i++) {
          if (!recursiveResult[i].isEmpty()) {
            highestIndex = i;

            if (i == 1) {
              Collections.reverse(drawnCards);
            }

            break;
          }
        }
      } else {
        drawnCards.sort(Collections.reverseOrder());
      }

      decks[highestIndex].addAll(drawnCards);
    }

    return decks;
  }

  private int calculateWinningScore(List<Integer> gameResult) {
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
