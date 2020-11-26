package com.gmail.chickenpowerrr.aoc2020.framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileHandler {

  public Stream<String> readFileLines(String fileName) {
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
      String[] lines = bufferedReader.lines().toArray(String[]::new);
      return Stream.of(lines);
    } catch (IOException e) {
      return Util.sneakyThrow(e);
    }
  }

  public String readFileEntirely(String fileName) {
    return readFileLines(fileName).collect(Collectors.joining("\n"));
  }
}
