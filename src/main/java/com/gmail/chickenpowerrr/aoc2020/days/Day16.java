package com.gmail.chickenpowerrr.aoc2020.days;

import com.gmail.chickenpowerrr.aoc2020.framework.Day;
import com.gmail.chickenpowerrr.aoc2020.helper.file.FileHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day16 implements Day {

  private static final Pattern REQUIREMENT_PATTERN =
      Pattern.compile("(.+): (\\d+)-(\\d+) or (\\d+)-(\\d+)");

  private final FileHelper fileHelper;

  public Day16() {
    this.fileHelper = new FileHelper();
  }

  @Override
  public void partOne() {
    ParseResult parseResult = parseInput();

    System.out.println(parseResult.getNearbyTickets().stream()
        .flatMap(nearbyTicket ->
            nearbyTicket.getInvalidFields(parseResult.getRequirements()).stream())
        .mapToInt(i -> i)
        .sum());
  }

  @Override
  public void partTwo() {
    ParseResult parseResult = parseInput();
    int[] order = getOrder(getOrderRequirements(parseResult.getNearbyTickets(),
        parseResult.getRequirements()));

    System.out.println(IntStream.range(0, order.length)
        .filter(i -> parseResult.getRequirements().get(i).getName().startsWith("departure"))
        .map(i -> find(i, order))
        .map(i -> parseResult.getOwnTicket().getValues().get(i))
        .mapToLong(i -> i)
        .reduce(1, (i, j) -> i * j));
  }

  private int find(int target, int[] array) {
    for (int i = 0; i < array.length; i++) {
      if (array[i] == target) {
        return i;
      }
    }

    return -1;
  }

  private int[] getOrder(List<List<Integer>> orderRequirements) {
    int[] order = new int[orderRequirements.size()];
    Arrays.fill(order, -1);

    // 4 is not an option in 11
    for (int repeat = 0; repeat < orderRequirements.size(); repeat++) {
      Set<Integer> filled = new HashSet<>();
      for (int i = 0; i < orderRequirements.size(); i++) {
        if (orderRequirements.get(i).size() == 1) {
          int target = orderRequirements.get(i).get(0);
          filled.add(target);
          order[i] = target;
        }
      }

      orderRequirements.forEach(requirements -> requirements.removeAll(filled));
    }

    return order;
  }

  private List<List<Integer>> getOrderRequirements(List<Ticket> tickets,
      List<Requirement> requirements) {
    List<List<Integer>> orderRequirements = IntStream.range(0, requirements.size())
        .mapToObj(i ->
            IntStream.range(0, requirements.size()).boxed().collect(Collectors.toList()))
        .collect(Collectors.toList());

    for (Ticket ticket : tickets) {
      if (!ticket.isValid(requirements)) {
        continue;
      }

      for (int i = 0; i < requirements.size(); i++) {
        int value = ticket.getValues().get(i);
        for (int j = 0; j < requirements.size(); j++) {
          if (!requirements.get(j).matches(value)) {
            orderRequirements.get(i).remove((Integer) j);
          }
        }
      }
    }

    return orderRequirements;
  }

  private ParseResult parseInput() {
    List<Requirement> requirements = new ArrayList<>();
    Ticket ownTicket = null;
    List<Ticket> nearbyTickets = new ArrayList<>();
    int state = 0;

    for (String line : this.fileHelper.readFileLines("day16/input")
        .filter(line -> !line.isEmpty()).collect(Collectors.toList())) {
      if (line.equalsIgnoreCase("your ticket:")
          || line.equalsIgnoreCase("nearby tickets:")) {
        state++;
        continue;
      }

      switch (state) {
        case 0:
          Matcher matcher = REQUIREMENT_PATTERN.matcher(line);
          matcher.find();

          requirements.add(new Requirement(matcher.group(1),
              List.of(new Range(Integer.parseInt(matcher.group(2)),
                      Integer.parseInt(matcher.group(3))),
                  new Range(Integer.parseInt(matcher.group(4)),
                      Integer.parseInt(matcher.group(5))))));
          break;
        case 1:
          ownTicket = parseTicket(line);
          break;
        case 2:
          nearbyTickets.add(parseTicket(line));
          break;
      }
    }

    return new ParseResult(requirements, ownTicket, nearbyTickets);
  }

  private Ticket parseTicket(String line) {
    List<Integer> values = new ArrayList<>();

    for (String part : line.split(",")) {
      values.add(Integer.parseInt(part));
    }

    return new Ticket(values);
  }

  private static class ParseResult {

    private final List<Requirement> requirements;
    private final Ticket ownTicket;
    private final List<Ticket> nearbyTickets;

    public ParseResult(
        List<Requirement> requirements, Ticket ownTicket,
        List<Ticket> nearbyTickets) {
      this.requirements = requirements;
      this.ownTicket = ownTicket;
      this.nearbyTickets = nearbyTickets;
    }

    public Ticket getOwnTicket() {
      return ownTicket;
    }

    public List<Ticket> getNearbyTickets() {
      return nearbyTickets;
    }

    public List<Requirement> getRequirements() {
      return requirements;
    }
  }

  private static class Ticket {

    private final List<Integer> values;

    public Ticket(List<Integer> values) {
      this.values = values;
    }

    public List<Integer> getInvalidFields(List<Requirement> requirements) {
      return this.values.stream().filter(i ->
          requirements.stream().noneMatch(requirement -> requirement.matches(i)))
          .collect(Collectors.toList());
    }

    public List<Integer> getValues() {
      return values;
    }

    public boolean isValid(List<Requirement> requirements) {
      return getInvalidFields(requirements).isEmpty();
    }

    @Override
    public String toString() {
      return "Ticket{" +
          "values=" + values +
          '}';
    }
  }

  private static class Requirement {

    private final String name;
    private final List<Range> ranges;

    public Requirement(String name, List<Range> ranges) {
      this.name = name;
      this.ranges = ranges;
    }

    public String getName() {
      return name;
    }

    public boolean matches(int value) {
      return this.ranges.stream().anyMatch(range -> range.isInRange(value));
    }

    @Override
    public String toString() {
      return "Requirement{" +
          "name='" + name + '\'' +
          ", ranges=" + ranges +
          '}';
    }
  }

  private static class Range {

    private final int start;
    private final int end;

    public Range(int start, int end) {
      this.start = start;
      this.end = end;
    }

    public boolean isInRange(int value) {
      return this.start <= value && value <= this.end;
    }

    @Override
    public String toString() {
      return "[" + this.start + ", " + this.end + "]";
    }
  }
}
