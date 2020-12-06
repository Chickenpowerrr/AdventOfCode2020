package com.gmail.chickenpowerrr.aoc2020;

import com.gmail.chickenpowerrr.aoc2020.framework.Day;
import com.gmail.chickenpowerrr.aoc2020.framework.DayDetector;
import java.util.function.Consumer;

public class Main {

  private static final int DAY = 6;

  public static void main(String[] args) throws Throwable {
    Main main = new Main();
    main.run(DAY);
  }

  private final DayDetector dayDetector;

  public Main() {
    this.dayDetector = new DayDetector();
  }

  public void run(int dayNumber) throws Throwable {
    System.out.printf("Running Day %d\n\n", dayNumber);

    Day day = this.dayDetector.getDay(dayNumber);

    boolean dayOneEnabled = !day.disabledParts().contains(1);
    boolean dayTwoEnabled = !day.disabledParts().contains(2);

    if (dayOneEnabled) {
      runPart(1, a -> day.partOne());
    }

    if (dayTwoEnabled) {
      runPart(2, b -> day.partTwo());
    }

    System.out.printf("Finished Day %d\n", dayNumber);
  }

  private void runPart(int part, Consumer<Void> partExecution) {
    System.out.println("================================");
    System.out.printf("Running part %d...\n", part);

    long startTime = System.currentTimeMillis();
    partExecution.accept(null);
    long endTime = System.currentTimeMillis();

    System.out.printf("Finished part %d in %dms\n", part, endTime - startTime);
    System.out.println("================================");
    System.out.println();
  }
}
