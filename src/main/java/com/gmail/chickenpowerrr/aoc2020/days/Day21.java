package com.gmail.chickenpowerrr.aoc2020.days;

import com.gmail.chickenpowerrr.aoc2020.framework.Day;
import com.gmail.chickenpowerrr.aoc2020.helper.file.FileHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day21 implements Day {

  private final FileHelper fileHelper;

  public Day21() {
    this.fileHelper = new FileHelper();
  }

  @Override
  public void partOne() {
    System.out.println(getFreeIngredients().size());
  }

  @Override
  public void partTwo() {

  }

  private List<String> getFreeIngredients() {
    List<String> result = getAllIngredients();
    getPossibleAllergensCauses().values().forEach(possibleCauses ->
        possibleCauses.forEach(possibleCause ->
            result.removeIf(target -> target.equals(possibleCause))));
    return result;
  }

  private List<String> getAllIngredients() {
    return getFoodEntries().stream()
        .map(FoodEntry::getIngredients)
        .flatMap(List::stream)
        .collect(Collectors.toList());
  }

  private Map<String, List<String>> getPossibleAllergensCauses() {
    Map<String, List<String>> possibleCauses = new HashMap<>();
    for (FoodEntry foodEntry : getFoodEntries()) {
      for (String allergen : foodEntry.getAllergens()) {
        possibleCauses.computeIfAbsent(allergen, a -> new ArrayList<>(foodEntry.getIngredients()));
        possibleCauses.get(allergen).removeIf(entry -> !foodEntry.getIngredients().contains(entry));
      }
    }

    return convergePossibleAllergenCauses(possibleCauses);
  }

  private Map<String, List<String>> convergePossibleAllergenCauses(
      Map<String, List<String>> possibleAllergenCauses) {
    boolean changed = true;
    while (changed) {
      changed = false;

      for (List<String> possibleAllergen : possibleAllergenCauses.values()) {
        if (possibleAllergen.size() == 1) {
          String converged = possibleAllergen.get(0);
          for (List<String> targetRemoval : possibleAllergenCauses.values()) {
            if (targetRemoval.size() > 1 && targetRemoval.contains(converged)) {
              changed = true;
              targetRemoval.remove(converged);
            }
          }
        }
      }
    }

    return possibleAllergenCauses;
  }

  private List<FoodEntry> getFoodEntries() {
    return this.fileHelper.readFileLines("day21/input")
        .map(FoodEntry::fromString)
        .collect(Collectors.toList());
  }

  private static class FoodEntry {
    public static FoodEntry fromString(String string) {
      List<String> ingredients = Arrays.asList(string.split(" \\(")[0].split(" "));
      List<String> allergens = string.contains("contains") ?
          Arrays.asList(string.substring(0, string.length() - 1)
              .split("contains ")[1].split(", ")) : new ArrayList<>();

      return new FoodEntry(ingredients, allergens);
    }

    private final List<String> ingredients;
    private final List<String> allergens;

    private FoodEntry(List<String> ingredients, List<String> allergens) {
      this.ingredients = ingredients;
      this.allergens = allergens;
    }

    public List<String> getIngredients() {
      return ingredients;
    }

    public List<String> getAllergens() {
      return allergens;
    }
  }
}
