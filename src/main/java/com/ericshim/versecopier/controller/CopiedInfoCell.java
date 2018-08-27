package com.ericshim.versecopier.controller;

import com.ericshim.versecopier.model.CopiedInfo;
import javafx.scene.control.ListCell;

public class CopiedInfoCell extends ListCell<CopiedInfo> {

  @Override
  protected void updateItem(CopiedInfo item, boolean empty) {
    super.updateItem(item, empty);

    int index = getIndex();
    String name = null;
    if (item != null && !empty) {
      name = item.toString();
    }
    setText(name);
    setGraphic(null);
  }
}
