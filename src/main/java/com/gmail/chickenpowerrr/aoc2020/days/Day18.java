package com.gmail.chickenpowerrr.aoc2020.days;

import com.gmail.chickenpowerrr.aoc2020.framework.Day;
import com.gmail.chickenpowerrr.aoc2020.helper.file.FileHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
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
            parseExpression(line, Map.of("*", (a, b) -> a * b, "+", Long::sum)))
        .sum());
  }

  @Override
  public void partTwo() {

  }

  private long parseExpression(String line,
      Map<String, BinaryOperator<Long>> operators) {
    if (!line.contains("(")) {
      return parseSimpleLine(line, operators);
    }

    String nextLine = line;
    List<String> parts = parseParts(line);

    for (String part : parts) {
      if (!part.contains("(")) {
        long simpleLine = parseSimpleLine(part, operators);
        nextLine = nextLine.replace("(" + part + ")", Long.toString(simpleLine));
      }
    }

    return parseExpression(nextLine, operators);
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

    if (!parts.contains(line)) {
      parts.add(line);
    }

    return parts;
  }

  private static class CombinedExpression implements ExpressionPart {
    private final BinaryOperator<Long> operator;
    private final ExpressionPart first;
    private final ExpressionPart second;

    public CombinedExpression(BinaryOperator<Long> operator,
        ExpressionPart first, ExpressionPart second) {
      this.operator = operator;
      this.first = first;
      this.second = second;
    }

    @Override
    public long getValue() {
      return this.operator.apply(first.getValue(), second.getValue());
    }
  }

  private static class Value implements ExpressionPart {

    private final long value;

    public Value(long value) {
      this.value = value;
    }

    @Override
    public long getValue() {
      return this.value;
    }
  }

  private interface ExpressionPart {
    long getValue();
  }
}
