package com.gmail.chickenpowerrr.aoc2020.days;

import com.gmail.chickenpowerrr.aoc2020.framework.Day;
import com.gmail.chickenpowerrr.aoc2020.helper.file.FileHelper;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day14 implements Day {

  private static final Pattern MASK_PATTERN = Pattern.compile("mask = (.+)");
  private static final Pattern MEMORY_PATTERN = Pattern.compile("mem\\[(\\d+)] = (\\d+)");

  private final FileHelper fileHelper;

  public Day14() {
    this.fileHelper = new FileHelper();
  }

  @Override
  public void partOne() {
    System.out.println(loadMemory().values().stream().mapToLong(l -> l).sum());
  }

  @Override
  public void partTwo() {
    System.out.println(loadMemoryWithAddressTranslation().values().stream()
        .mapToLong(l -> l).sum());
  }

  private Map<Integer, Long> loadMemory() {
    Map<Integer, Long> memory = new HashMap<>();
    Mask mask = null;

    for (String line : this.fileHelper.readFileLines("day14/input")
        .collect(Collectors.toList())) {
      if (line.startsWith("mask")) {
        Matcher matcher = MASK_PATTERN.matcher(line);
        matcher.find();

        String targetString = matcher.group(1);

        boolean[] enabled = new boolean[targetString.length()];
        String[] values = new String[targetString.length()];

        for (int i = 0; i < targetString.length(); i++) {
          String target = targetString.substring(i, i + 1);
          enabled[i] = !target.equalsIgnoreCase("X");
          values[i] = target;
        }

        mask = new Mask(enabled, values);
      } else {
        Matcher matcher = MEMORY_PATTERN.matcher(line);
        matcher.find();

        memory.put(Integer.parseInt(matcher.group(1)), mask.applyValue(
            Long.toBinaryString(Long.parseLong(matcher.group(2)))));
      }
    }

    return memory;
  }

  private Map<Long, Long> loadMemoryWithAddressTranslation() {
    Map<Long, Long> memory = new HashMap<>();
    Mask mask = null;

    for (String line : this.fileHelper.readFileLines("day14/input")
        .collect(Collectors.toList())) {
      if (line.startsWith("mask")) {
        Matcher matcher = MASK_PATTERN.matcher(line);
        matcher.find();

        String targetString = matcher.group(1);

        boolean[] enabled = new boolean[targetString.length()];
        String[] values = new String[targetString.length()];

        for (int i = 0; i < targetString.length(); i++) {
          String target = targetString.substring(i, i + 1);
          enabled[i] = !target.equalsIgnoreCase("X");
          values[i] = target;
        }

        mask = new Mask(enabled, values);
      } else {
        Matcher matcher = MEMORY_PATTERN.matcher(line);
        matcher.find();

        long value = Long.parseLong(matcher.group(2));

        mask.getAddresses(mask.applyAddress(
            Long.toBinaryString(Long.parseLong(matcher.group(1)))), -1)
            .forEach(address -> memory.put(Long.parseLong(address, 2), value));
      }
    }

    return memory;
  }

  private static class Mask {
    private final boolean[] enabled;
    private final String[] values;

    public Mask(boolean[] enabled, String[] values) {
      this.enabled = enabled;
      this.values = values;
    }

    public boolean[] getEnabled() {
      return enabled;
    }

    public String[] getValues() {
      return values;
    }

    public long applyValue(String string) {
      string = "0".repeat(this.enabled.length - string.length()) + string;

      StringBuilder target = new StringBuilder();
      for (int i = 0; i < string.length(); i++) {
        if (this.enabled[i]) {
          target.append(this.values[i]);
        } else {
          target.append(string.charAt(i));
        }
      }

      return Long.parseLong(target.toString(), 2);
    }

    public String applyAddress(String string) {
      string = "0".repeat(this.enabled.length - string.length()) + string;

      StringBuilder target = new StringBuilder();
      for (int i = 0; i < string.length(); i++) {
        if (this.values[i].equals("1")) {
          target.append("1");
        } else {
          target.append(string.charAt(i));
        }
      }

      return target.toString();
    }

    public Set<String> getAddresses(String string, int lastApplied) {
      Set<String> result = new HashSet<>();

      int firstIndex = -1;
      for (int i = lastApplied + 1; i < string.length(); i++) {
        if (this.values[i].equalsIgnoreCase("X")) {
          firstIndex = i;
          break;
        }
      }

      if (firstIndex < 0) {
        return Set.of(string);
      } else {
        result.addAll(getAddresses(string.substring(0, firstIndex) + "1"
            + string.substring(firstIndex + 1), firstIndex));
        result.addAll(getAddresses(string.substring(0, firstIndex) + "0"
            + string.substring(firstIndex + 1), firstIndex));

        return result;
      }
    }
  }
}
