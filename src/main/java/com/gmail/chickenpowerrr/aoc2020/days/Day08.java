package com.gmail.chickenpowerrr.aoc2020.days;

import com.gmail.chickenpowerrr.aoc2020.framework.Day;
import com.gmail.chickenpowerrr.aoc2020.helper.file.FileHelper;
import java.util.List;
import java.util.stream.Collectors;

public class Day08 implements Day {

  private final FileHelper fileHelper;

  public Day08() {
    this.fileHelper = new FileHelper();
  }

  @Override
  public void partOne() {
    List<Instruction> instructions = this.fileHelper.readFileLines("day08/input")
        .map(line -> new Instruction(line.split(" ")[0],
            Integer.parseInt(line.split(" ")[1])))
        .collect(Collectors.toList());

    int counter = 0;

    boolean[] visited = new boolean[instructions.size()];

    for (int cursor = 0; cursor < instructions.size(); cursor++) {
      if (visited[cursor]) {
        System.out.println(counter);
        return;
      }

      visited[cursor] = true;
      Instruction instruction = instructions.get(cursor);

      switch (instruction.getName()) {
        case "acc":
          counter += instruction.getArgument();
          break;
        case "jmp":
          cursor += instruction.getArgument() - 1;
          break;
      }
    }
  }

  @Override
  public void partTwo() {

  }

  private static class Instruction {
    private final String name;
    private final int argument;

    public Instruction(String name, int argument) {
      this.name = name;
      this.argument = argument;
    }

    public String getName() {
      return name;
    }

    public int getArgument() {
      return argument;
    }

    @Override
    public String toString() {
      return "Instruction{" +
          "name='" + name + '\'' +
          ", argument=" + argument +
          '}';
    }
  }
}
