package com.gmail.chickenpowerrr.aoc2020.days;

import com.gmail.chickenpowerrr.aoc2020.framework.Day;
import com.gmail.chickenpowerrr.aoc2020.helper.file.FileHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day19 implements Day {

  private final FileHelper fileHelper;

  public Day19() {
    this.fileHelper = new FileHelper();
  }

  @Override
  public void partOne() {
    Map<Integer, Rule> rules = getRules();
    Rule targetRule = rules.get(0);

    System.out.println(getStrings().stream()
        .filter(string -> targetRule.matchesForSizes(string).contains(string.length()))
        .count());
  }

  @Override
  public void partTwo() {

  }

  private Collection<String> getStrings() {
    return this.fileHelper.readFileLines("day19/input")
        .filter(string -> !string.contains(":") && !string.isEmpty())
        .collect(Collectors.toList());
  }

  private Map<Integer, Rule> getRules() {
    Map<Integer, EncodedRule> encodedRules = getEncodedRules();
    Map<Integer, Rule> rules = new HashMap<>();

    while (!encodedRules.isEmpty()) {
      for (Map.Entry<Integer, EncodedRule> entry : new HashMap<>(encodedRules).entrySet()) {
        if (entry.getValue().getDependencies().stream().allMatch(rules::containsKey)) {
          encodedRules.remove(entry.getKey());

          if (entry.getValue().getRule().contains("|")) {
            rules.put(entry.getKey(), OrRule.fromString(
                entry.getKey(), entry.getValue().getRule(), rules));
          } else if (entry.getValue().getRule().contains("\"")) {
            rules.put(entry.getKey(), PrimitiveRule.fromString(
                entry.getKey(), entry.getValue().getRule()));
          } else {
            rules.put(entry.getKey(), AndRule.fromString(
                entry.getKey(), entry.getValue().getRule(), rules));
          }
        }
      }
    }

    return rules;
  }

  private Map<Integer, EncodedRule> getEncodedRules() {
    return this.fileHelper.readFileLines("day19/input")
        .filter(string -> string.contains(":"))
        .map(EncodedRule::fromString)
        .collect(Collectors.toMap(EncodedRule::getId, e -> e));
  }

  private static class EncodedRule {
    private static EncodedRule fromString(String rule) {
      int id = Integer.parseInt(rule.split(":")[0]);
      Collection<Integer> dependencies = Arrays.stream(rule.split(" "))
          .filter(string -> string.matches("\\d+"))
          .map(Integer::parseInt)
          .collect(Collectors.toSet());

      return new EncodedRule(rule, id, dependencies);
    }

    private final String rule;
    private final int id;
    private final Collection<Integer> dependencies;

    public EncodedRule(String rule, int id, Collection<Integer> dependencies) {
      this.rule = rule;
      this.id = id;
      this.dependencies = dependencies;
    }

    public String getRule() {
      return rule;
    }

    public int getId() {
      return id;
    }

    public Collection<Integer> getDependencies() {
      return Collections.unmodifiableCollection(dependencies);
    }
  }

  private interface Rule {
    int getId();

    Set<Integer> matchesForSizes(String string);
  }

  private static class PrimitiveRule implements Rule {

    private static PrimitiveRule fromString(int id, String encoded) {
      encoded = encoded.substring(encoded.indexOf(":") + 3);
      return new PrimitiveRule(id, encoded.charAt(0));
    }

    private final int id;
    private final char target;

    public PrimitiveRule(int id, char target) {
      this.id = id;
      this.target = target;
    }

    @Override
    public int getId() {
      return id;
    }

    @Override
    public Set<Integer> matchesForSizes(String string) {
      return string.charAt(0) == target ? Set.of(1) : Set.of();
    }

    @Override
    public String toString() {
      return "PrimitiveRule{" + this.target + '}';
    }
  }

  private static class AndRule implements Rule {

    private static AndRule fromString(int id, String encoded, Map<Integer, Rule> rules) {
      encoded = encoded.substring(encoded.indexOf(":") + 2);

      List<Rule> targetRules = Arrays.stream(encoded.split(" "))
          .map(Integer::parseInt)
          .map(rules::get)
          .collect(Collectors.toList());

      return new AndRule(id, targetRules);
    }

    private final int id;
    private final List<Rule> rules;

    public AndRule(int id, List<Rule> rules) {
      this.id = id;
      this.rules = rules;
    }

    @Override
    public int getId() {
      return id;
    }

    @Override
    public Set<Integer> matchesForSizes(String string) {
      Set<Integer> sizes = new HashSet<>();
      sizes.add(0);

      for (Rule rule : this.rules) {
        Set<Integer> nextSizes = new HashSet<>();
        for (int previousSize : sizes) {
          for (int size : rule.matchesForSizes(string.substring(previousSize))) {
            nextSizes.add(previousSize + size);
          }
        }

        sizes = nextSizes;
      }

//      System.out.println("\"" + string + "\" " + toString() + " -> " + sizes);
      return sizes;
    }

    @Override
    public String toString() {
      return "AndRule{" + this.rules.stream().map(Rule::getId)
          .map(Object::toString)
          .collect(Collectors.joining(" ")) + '}';
    }
  }

  private static class OrRule implements Rule {

    private static OrRule fromString(int id, String encoded, Map<Integer, Rule> rules) {
      encoded = encoded.substring(encoded.indexOf(":") + 2);
      String[] parts = encoded.split(" \\| ");

      List<List<Rule>> targetRules = new ArrayList<>();

      for (String part : parts) {
        targetRules.add(Arrays.stream(part.split(" "))
            .map(Integer::parseInt)
            .map(rules::get)
            .collect(Collectors.toList()));
      }

      return new OrRule(id, targetRules);
    }

    private final int id;
    private final List<AndRule> rules;

    public OrRule(int id, List<List<Rule>> rules) {
      this.id = id;
      this.rules = rules.stream()
          .map(ands -> new AndRule(-1, ands))
          .collect(Collectors.toList());
    }

    @Override
    public int getId() {
      return this.id;
    }

    @Override
    public Set<Integer> matchesForSizes(String string) {
      Set<Integer> result = this.rules.stream()
          .flatMap(andRule -> andRule.matchesForSizes(string).stream())
          .collect(Collectors.toSet());

//      System.out.println("\"" + string + "\" " + toString() + " -> " + result);
      return result;
    }

    @Override
    public String toString() {
      return "OrRule{" +
          this.rules.stream().map(AndRule::toString).collect(Collectors.joining(" | ")) + '}';
    }
  }
}
