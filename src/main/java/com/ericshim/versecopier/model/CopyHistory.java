package com.ericshim.versecopier.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CopyHistory {
  private List<CopiedInfo> history = new ArrayList<>();
  private int capacity = 30;

  public CopyHistory() {}

  public CopyHistory(int capacity) {
    this.capacity = capacity;
  }

  public void recordItem(CopiedInfo... info) {
    history.addAll(Arrays.asList(info));
    maintainLength();
  }

  private void maintainLength() {
    while (history.size() > capacity) history.remove(0);
  }
}
