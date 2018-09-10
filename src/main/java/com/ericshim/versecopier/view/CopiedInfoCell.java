package com.ericshim.versecopier.view;

import com.ericshim.versecopier.model.CopiedInfo;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class CopiedInfoCell extends ListCell<CopiedInfo> {
  private static final String copyIconFile = "/outline-file_copy-black-48/1x/"
      + "outline_file_copy_black_48dp.png";
  private static final String restoreIconFile = "/outline-undo-black-48/1x/"
      + "outline_undo_black_48dp.png";

  private final VBox vBox = new VBox();
  private final HBox hBox = new HBox();
  private final TextArea textArea = new TextArea();
  private final GridPane buttonGridPane = new GridPane();

  private final Button copyButton = new Button();
  private final Button restoreButton = new Button();

  private ButtonGridCallback buttonGridCallback = null;

  public CopiedInfoCell() {
    super();
    textArea.setWrapText(false);
    textArea.setEditable(false);
    textArea.setPrefRowCount(2);
    textArea.setPrefColumnCount(1);

    initializeButtonGridPane();

    HBox.setHgrow(textArea, Priority.ALWAYS);
    hBox.getChildren().addAll(buttonGridPane, textArea);
    hBox.setSpacing(5);
  }

  @Override
  protected void updateItem(CopiedInfo item, boolean empty) {
    super.updateItem(item, empty);
    if (empty || item == null) {
      setGraphic(null);
      return;
    }
    String name = item.toString();
    textArea.setText(item.getFullText());
    setGraphic(hBox);
  }

  private void initializeButtonGridPane() {
    double fitWidth = 10;

    Image copyImage = new Image(CopiedInfoCell.class.getResourceAsStream(copyIconFile));
    ImageView copyImageView = new ImageView(copyImage);
    copyImageView.setFitWidth(fitWidth);
    copyImageView.setPreserveRatio(true);
    copyButton.setGraphic(copyImageView);
    copyButton.setOnMouseClicked(event -> {
      CopiedInfo info = getItem();
      if (buttonGridCallback != null && info != null) buttonGridCallback.copy(info);
    });

    Image restoreImage = new Image(CopiedInfoCell.class.getResourceAsStream(restoreIconFile));
    ImageView restoreImageView = new ImageView(restoreImage);
    restoreImageView.setFitWidth(fitWidth);
    restoreImageView.setPreserveRatio(true);
    restoreButton.setGraphic(restoreImageView);
    restoreButton.setOnMouseClicked(event -> {
      CopiedInfo info = getItem();
      if (buttonGridCallback != null && info != null) buttonGridCallback.restore(info);
    });

    buttonGridPane.add(copyButton, 0, 0);
    buttonGridPane.add(restoreButton, 0, 1);

    buttonGridPane.setVgap(5);
  }

  public void setButtonGridCallback(
      ButtonGridCallback buttonGridCallback) {
    this.buttonGridCallback = buttonGridCallback;
  }

  public interface ButtonGridCallback {
    void copy(CopiedInfo copiedInfo);
    void restore(CopiedInfo copiedInfo);
  }
}
