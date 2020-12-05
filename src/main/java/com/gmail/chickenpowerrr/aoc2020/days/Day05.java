package com.gmail.chickenpowerrr.aoc2020.days;

import com.gmail.chickenpowerrr.aoc2020.framework.Day;
import com.gmail.chickenpowerrr.aoc2020.helper.file.FileHelper;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day05 implements Day {

  private final FileHelper fileHelper;

  public Day05() {
    this.fileHelper = new FileHelper();
  }

  @Override
  public void partOne() {
    System.out.println(this.fileHelper.readFileLines("day05/input")
        .map(BoardingPass::fromBinarySpace)
        .mapToInt(BoardingPass::getSeatId)
        .max().getAsInt());
  }

  @Override
  public void partTwo() {
    Set<Integer> foundPasses = this.fileHelper.readFileLines("day05/input")
        .map(BoardingPass::fromBinarySpace)
        .map(BoardingPass::getSeatId)
        .collect(Collectors.toSet());

    IntStream.range(30, 90)
        .flatMap(i -> IntStream.rangeClosed(1, 7).map(j -> i * 8 + j))
        .boxed().filter(id -> !foundPasses.contains(id)).forEach(System.out::println);
  }

  private static class BoardingPass {
    private final int row;
    private final int column;

    public static BoardingPass fromBinarySpace(String binarySpace) {
      return new BoardingPass(decodeIntFromBinary(toBinary(binarySpace.substring(0, 7))),
          decodeIntFromBinary(toBinary(binarySpace.substring(7))));
    }

    private static boolean[] toBinary(String input) {
      boolean[] result = new boolean[input.length()];

      for (int i = 0; i < input.length(); i++) {
        result[i] = input.charAt(i) == 'B' || input.charAt(i) == 'R';
      }

      return result;
    }

    private static int decodeIntFromBinary(boolean[] binary) {
      int lower = 0;
      int higher = (int) Math.pow(2, binary.length) - 1;

      for (boolean entry : binary) {
        int reductionSize = (int) Math.ceil((higher - lower) / 2D);
        if (entry) {
          lower += reductionSize;
        } else {
          higher -= reductionSize;
        }
      }

      return lower;
    }

    public BoardingPass(int row, int column) {
      this.row = row;
      this.column = column;
    }

    public int getSeatId() {
      return this.row * 8 + this.column;
    }
  }
}
