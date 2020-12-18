package com.gmail.chickenpowerrr.aoc2020.days;

import com.gmail.chickenpowerrr.aoc2020.framework.Day;
import com.gmail.chickenpowerrr.aoc2020.helper.file.FileHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

public class Day18 implements Day {

  private final FileHelper fileHelper;

  public Day18() {
    this.fileHelper = new FileHelper();
  }

  @Override
  public void partOne() {
    System.out.println(this.fileHelper.readFileLines("day18/input")
        .mapToLong(line ->
            parseExpression(line, Map.of("*", (a, b) -> a * b, "+", Long::sum),
                this::parseSimpleLine))
        .sum());
  }

  @Override
  public void partTwo() {
    System.out.println(this.fileHelper.readFileLines("day18/input")
        .mapToLong(line ->
            parseExpression(line, Map.of("*", (a, b) -> a * b, "+", Long::sum),
                this::parseAdvancedLine))
        .sum());
  }

  private long parseExpression(String line,
      Map<String, BinaryOperator<Long>> operators,
      BiFunction<String, Map<String, BinaryOperator<Long>>, Long> simpleExpressionSolver) {
    if (!line.contains("(")) {
      return simpleExpressionSolver.apply(line, operators);
    }

    String nextLine = line;
    List<String> parts = parseParts(line);

    for (String part : parts) {
      if (!part.contains("(")) {
        long simpleLine = simpleExpressionSolver.apply(part, operators);
        nextLine = nextLine.replace("(" + part + ")", Long.toString(simpleLine));
      }
    }

    return parseExpression(nextLine, operators, simpleExpressionSolver);
  }

  private long parseSimpleLine(String line,
      Map<String, BinaryOperator<Long>> operators) {
    String[] parts = line.split(" ");

    long result = Long.parseLong(parts[0]);
    for (int i = 0; i < parts.length - 1; i += 2) {
      result = operators.get(parts[i + 1]).apply(result, Long.parseLong(parts[i + 2]));
    }

    return result;
  }

  private long parseAdvancedLine(String line, Map<String, BinaryOperator<Long>> operators) {
    String[] parts = line.split(" ");

    for (int i = 0; i < parts.length - 1; i += 2) {
      if (parts[i + 1].equals("+")) {
        String value = Long.toString(Long.parseLong(parts[i]) + Long.parseLong(parts[i + 2]));
        String nextLine = (String.join(" ",
            Arrays.copyOf(parts, i)) + " " + value + " "
            + String.join(" ", Arrays.copyOfRange(parts, i + 3, parts.length)))
            .strip();

        return parseAdvancedLine(nextLine, operators);
      }
    }

    return parseSimpleLine(line, operators);
  }

  private List<String> parseParts(String line) {
    Stack<Integer> openingBrackets = new Stack<>();
    List<String> parts = new ArrayList<>();

    for (int i = 0; i < line.length(); i++) {
      if (line.charAt(i) == '(') {
        openingBrackets.push(i);
      } else if (line.charAt(i) == ')') {
        int start = openingBrackets.pop();
        parts.add(line.substring(start + 1, i));
      }
    }

    return parts;
  }
}
