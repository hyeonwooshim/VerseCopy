package com.ericshim.versecopier.controller;

import com.ericshim.versecopier.model.CopiedInfo;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class CopiedInfoCellFactory implements Callback<ListView<CopiedInfo>, ListCell<CopiedInfo>> {

  @Override
  public ListCell<CopiedInfo> call(ListView<CopiedInfo> param) {
    return new CopiedInfoCell();
  }
}
