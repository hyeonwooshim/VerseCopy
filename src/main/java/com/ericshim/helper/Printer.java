package com.ericshim.helper;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class Printer {
  public static PrintStream out;

  static {
    try {
      out = new PrintStream(System.out, true, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }
}
