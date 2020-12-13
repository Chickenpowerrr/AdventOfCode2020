package com.gmail.chickenpowerrr.aoc2020.helper;

public class Util {

  // a * b = c (mod d)
  // https://courses.cs.washington.edu/courses/cse311/15au/documents/ModularEquivalences.pdf
  public static long solveModularEquation(long a, long c, long d) {
    return (modInverse(a, d) * c) % d;
  }

  // https://www.geeksforgeeks.org/multiplicative-inverse-under-modulo-m/
  public static long modInverse(long a, long m) {
    long m0 = m;
    long y = 0, x = 1;

    if (m == 1) {
      return 0;
    }

    while (a > 1) {
      long q = a / m;

      long t = m;

      m = a % m;
      a = t;
      t = y;

      y = x - q * y;
      x = t;
    }

    if (x < 0) {
      x += m0;
    }

    return x;
  }
}
