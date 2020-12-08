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

  private ExecutionResult executeProgram(List<Instruction> instructions) {
    int counter = 0;

    boolean[] visited = new boolean[instructions.size()];

    for (int cursor = 0; cursor < instructions.size(); cursor++) {
      if (visited[cursor]) {
        return new ExecutionResult(true, counter);
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

    return new ExecutionResult(false, counter);
  }

  @Override
  public void partOne() {
    List<Instruction> instructions = this.fileHelper.readFileLines("day08/input")
        .map(line -> new Instruction(line.split(" ")[0],
            Integer.parseInt(line.split(" ")[1])))
        .collect(Collectors.toList());

    System.out.println(executeProgram(instructions).getCounter());
  }

  @Override
  public void partTwo() {
    List<Instruction> instructions = this.fileHelper.readFileLines("day08/input")
        .map(line -> new Instruction(line.split(" ")[0],
            Integer.parseInt(line.split(" ")[1])))
        .collect(Collectors.toList());

    for (int i = 0; i < instructions.size(); i++) {
      Instruction previousInstruction = instructions.get(i);
      if (previousInstruction.getName().equals("jmp")) {
        instructions.set(i, new Instruction("nop", 0));
      } else if (previousInstruction.getName().equals("nop")) {
        instructions.set(i, new Instruction("jmp", previousInstruction.getArgument()));
      }

      ExecutionResult executionResult = executeProgram(instructions);
      if (!executionResult.isStuck()) {
        System.out.println(executionResult.getCounter());
        break;
      }

      instructions.set(i, previousInstruction);
    }
  }

  private static class ExecutionResult {
    private final boolean stuck;
    private final int counter;

    public ExecutionResult(boolean stuck, int counter) {
      this.stuck = stuck;
      this.counter = counter;
    }

    public int getCounter() {
      return counter;
    }

    public boolean isStuck() {
      return stuck;
    }
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
