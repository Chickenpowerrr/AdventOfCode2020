package com.gmail.chickenpowerrr.aoc2020.days;

import com.gmail.chickenpowerrr.aoc2020.framework.Day;
import com.gmail.chickenpowerrr.aoc2020.helper.file.FileHelper;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day07 implements Day {

  private final FileHelper fileHelper;

  public Day07() {
    this.fileHelper = new FileHelper();
  }

  private Map<String, Map<String, Integer>> getContainers() {
    Map<String, Map<String, Integer>> containers = new HashMap<>();
    for (String line : this.fileHelper.readFileLines("day07/input")
        .collect(Collectors.toList())) {

      String target = line.split(" bags contain")[0].strip();
      String content = line.split(" bags contain ")[1];

      containers.putIfAbsent(target, new HashMap<>());
      Map<String, Integer> contentBags = containers.get(target);

      for (String contentBag : content.split(" bags?,?\\.? ?")) {
        try {
          int amount = Integer.parseInt(contentBag.split(" ")[0]);
          String contentBagName = contentBag.split("\\d+")[1].strip();
          contentBags.put(contentBagName, amount);
        } catch (NumberFormatException e) {
          // No content
        }
      }
    }

    return containers;
  }

  private Map<String, Set<String>> getParents(Map<String, Map<String, Integer>> containers) {
    Map<String, Set<String>> parents = new HashMap<>();

    containers.forEach((parent, children) -> children.keySet().forEach(child -> {
      parents.putIfAbsent(child, new HashSet<>());
      parents.get(child).add(parent);
    }));

    return parents;
  }

  private Set<String> getFamilyTree(String target, Map<String, Set<String>> parents) {
    Set<String> currentParents = new HashSet<>(parents.getOrDefault(target, new HashSet<>()));
    int lastSize = 0;

    while (lastSize != currentParents.size()) {
      lastSize = currentParents.size();

      for (String currentParent : new HashSet<>(currentParents)) {
        currentParents.addAll(parents.getOrDefault(currentParent, new HashSet<>()));
      }
    }

    return currentParents;
  }

  @Override
  public void partOne() {
    System.out.println(getFamilyTree("shiny gold", getParents(getContainers())).size());
  }

  @Override
  public void partTwo() {

  }
}
