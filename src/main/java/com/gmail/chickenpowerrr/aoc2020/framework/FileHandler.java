package com.gmail.chickenpowerrr.aoc2020.framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileHandler {

  public Stream<String> readFileLines(String fileName) {
    try (InputStream inputStream = getClass().getResourceAsStream(fileName);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
      return bufferedReader.lines();
    } catch (IOException e) {
      return Util.sneakyThrow(e);
    }
  }

  public String readFileEntirely(String fileName) {
    return readFileLines(fileName).collect(Collectors.joining("\n"));
  }
}
