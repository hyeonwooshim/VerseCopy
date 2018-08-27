package com.ericshim.versecopier.controller;

import com.ericshim.versecopier.model.CopiedInfo;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class CopyHistoryViewController {
  private ObservableList<CopiedInfo> history;
  private final ListView<CopiedInfo> historyView = new ListView<>();

  public CopyHistoryViewController(List<CopiedInfo> history) {
    if (history == null) {
      this.history = FXCollections.observableArrayList();
    } else {
      this.history = FXCollections.observableArrayList(history);
    }
    historyView.setItems(this.history);
    historyView.setOrientation(Orientation.VERTICAL);
    historyView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    historyView.setPrefSize(300, 400);
  }

  public ListView<CopiedInfo> getHistoryView() {
    return historyView;
  }

  public void addHistoryItem(int bookIndex, int chapter, int verse, int offset) {
    CopiedInfo info = new CopiedInfo(bookIndex, chapter, verse, offset);
    if (history.isEmpty() || !history.get(0).equals(info)) history.add(0, info);
  }
}
