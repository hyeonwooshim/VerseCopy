package com.ericshim.versecopier.view;

import com.ericshim.versecopier.model.CopiedInfo;
import com.ericshim.versecopier.view.CopiedInfoCell.ButtonGridCallback;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class CopiedInfoCellFactory implements Callback<ListView<CopiedInfo>, ListCell<CopiedInfo>> {
  private ButtonGridCallback buttonGridCallback = null;

  @Override
  public ListCell<CopiedInfo> call(ListView<CopiedInfo> param) {
    CopiedInfoCell cell = new CopiedInfoCell();
    cell.setButtonGridCallback(buttonGridCallback);
    return cell;
  }

  public void setButtonGridCallback(ButtonGridCallback buttonGridCallback) {
    this.buttonGridCallback = buttonGridCallback;
  }
}
