package com.ericshim.versecopier;

import com.ericshim.bible.Bible;
import com.ericshim.bible.KoreanBible;
import com.ericshim.bible.NkjvBible;
import com.ericshim.helper.Printer;
import com.ericshim.lib.ClipboardTransfer;
import com.ericshim.versecopier.controller.CopyHistoryViewController;

import java.io.IOException;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * 2016/08/10
 * @author Eric Shim
 */
public class VerseCopier extends Application {

  private ClipboardTransfer clipper = new ClipboardTransfer();
  private VerseFormatter formatter;
  private Bible korBible;
  private Bible engBible;
  private Information info;

  // ChoiceBox<String> beforeOrAfter; (took out to have radio buttons instead)
  private ToggleGroup recOrReadGroup;
  private RadioButton rb3;
  private RadioButton rb4;
  private ToggleGroup befAftGroup;
  private RadioButton rb1;
  private RadioButton rb2;
  private TextField bookName;
  private TextField chapNum;
  private TextField verse1;
  private TextField verse2;
  private Label status;
  private Button copyButton;

  private CopyHistoryViewController historyViewController;

  private Stage infoStage;

  private int book = 0;
  private int chapter = 1;
  private int beginningVerse = 1;
  private int endingVerse = 1;

  @Override
  public void start(Stage stage) {
    try { //initialize bible, formatter, and info
      korBible = new KoreanBible();
      engBible = new NkjvBible();
      if (!checkIfBiblesEqual(korBible, engBible)) throw new IOException();
      formatter = new VerseFormatter(korBible, engBible);
      info = new Information("Information.txt");
    } catch (IOException e) {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setTitle("FILE NOT FOUND");
      alert.setHeaderText(e.getMessage());
      alert.showAndWait();
    }

    historyViewController = new CopyHistoryViewController(null);

    // set up BorderPane with other nodes
    BorderPane bPane = new BorderPane();
    bPane.setLeft(getVBoxLabels());
    bPane.setCenter(getVBoxTextField());
    bPane.setBottom(getHBox());
    bPane.setRight(historyViewController.getHistoryView());

    bPane.setPadding(new Insets(10));

    Scene scene = new Scene(bPane);
    stage.setScene(scene);
    stage.setTitle("Verse Copier");
    stage.show();

    //initialize info stage
    initializeInfo();
  }

  // initializes label and copyButton
  private HBox getHBox() {
    HBox hbox = new HBox();
    hbox.setPadding(new Insets(5));
    hbox.setSpacing(10);

    status = new Label("Start Typing");
    status.setPrefSize(150, 30);

    copyButton = new Button("Copy Verse");
    copyButton.setDefaultButton(true);
    copyButton.setOnAction(event -> {
      if (getRecitedOrRead()) {
        clipper.setClipboardContents(
            formatter.formatVerses(getBook(), chapter, beginningVerse, endingVerse,
                getBeforeOrAfter()));
        rb1.requestFocus();
      } else {
        clipper.setClipboardHtmlContents(
            formatter.getHtmlNumberedVerses(getBook(), chapter, beginningVerse, endingVerse)
        );
        rb3.requestFocus();
      }
      historyViewController.addHistoryItem(formatter.getBookIndex(getBook()),
          chapter, beginningVerse, endingVerse - beginningVerse);
      status.setText("COPIED");
    });
    copyButton.setDisable(true);
    hbox.getChildren().addAll(status, copyButton);

    return hbox;
  }

  // initializes text fields
  private VBox getVBoxTextField() {
    VBox vbox1 = new VBox();
    vbox1.setPadding(new Insets(5));
    vbox1.setSpacing(5);

    /* Replaced by radiobuttons
    beforeOrAfter = new ChoiceBox<String>();
    beforeOrAfter.getItems().addAll("Before", "After");
    beforeOrAfter.getSelectionModel().selectFirst(); // have default value
    beforeOrAfter.setPrefSize(70, 30);
    */

    /* implement ToggleGroup and RadioButtons */
    recOrReadGroup = new ToggleGroup();
    rb3 = new RadioButton("Recited");
    rb3.setUserData("Recited");
    rb3.setToggleGroup(recOrReadGroup);
    rb3.setSelected(true);
    rb4 = new RadioButton("Read");
    rb4.setUserData("Read");
    rb4.setToggleGroup(recOrReadGroup);
    recOrReadGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
      public void changed(ObservableValue<? extends Toggle> ov,
          Toggle old_toggle, Toggle new_toggle) {
        if (recOrReadGroup.getSelectedToggle() != null) {
          if (recOrReadGroup.getSelectedToggle().getUserData().toString().equals("Read")) {
            rb1.setDisable(true);
            rb2.setDisable(true);
          } else {
            rb1.setDisable(false);
            rb2.setDisable(false);
          }
        }
      }
    });
    /* ******************************************/

    /* implement ToggleGroup and RadioButtons */
    befAftGroup = new ToggleGroup();
    rb1 = new RadioButton("Before");
    rb1.setUserData("Before");
    rb1.setToggleGroup(befAftGroup);
    rb1.setSelected(true);
    rb2 = new RadioButton("After");
    rb2.setUserData("After");
    rb2.setToggleGroup(befAftGroup);
    /* ******************************************/

    // have radio buttons and info icon together
    HBox hbox2 = new HBox(rb3, rb4, makeInfoIcon());
    hbox2.setSpacing(26);

    // have radio buttons and info icon together
    HBox hbox = new HBox(rb1, rb2);
    hbox.setSpacing(30);

    // initialize text fields
    bookName = new TextField();
    chapNum = new TextField();
    verse1 = new TextField();
    verse2 = new TextField();

    bookName.setPrefSize(130, 30);
    chapNum.setPrefSize(130, 30);
    verse1.setPrefSize(130, 30);
    verse2.setPrefSize(130, 30);

    bookName.textProperty().addListener((a, b, newValue) -> { updateState(); });
    chapNum.textProperty().addListener((a, b, newValue) -> { updateState(); });
    verse1.textProperty().addListener((a, b, newValue) -> { updateState(); });
    verse2.textProperty().addListener((a, b, newValue) -> { updateState(); });

    // add the textfields
    vbox1.getChildren().addAll(
        hbox2, hbox, bookName, chapNum, verse1, verse2
    );

    // event of enter key ()
    /* made unnecessary by copyButton.setDefaultButton(true);
    for (Node f : vbox1.getChildren()) {
        f.setOnKeyReleased(event -> {
                if (event.getCode() == KeyCode.ENTER){
                    copyButton.fire();
                }
            });
    }
    */
    return vbox1;
  }

  private VBox getVBoxLabels() {
    VBox vbox3 = new VBox();

    vbox3.setPadding(new Insets(5));
    vbox3.setSpacing(5);

    Label recOrRead = new Label("Recited or Read:");
    Label befAft = new Label("Bef or Aft:");
    Label book = new Label("Book:");
    Label chapter = new Label("Chapter:");
    Label beginning = new Label("Beginning V:");
    Label end = new Label("Ending V:");

    befAft.setPrefSize(70, 30);
    book.setPrefSize(70, 30);
    chapter.setPrefSize(70, 30);
    beginning.setPrefSize(70, 30);
    end.setPrefSize(70, 30);

    vbox3.getChildren().addAll(
        recOrRead, befAft, book, chapter, beginning, end);

    /*
    for (Node f : vbox3.getChildren()) {
        Label l = (Label) f;
        l.setTextAlignment(TextAlignment.RIGHT);
    } */

    return vbox3;
  }

  public StackPane makeInfoIcon() {
    StackPane stack = new StackPane();
    Circle icon = new Circle(10);
    icon.setFill(Color.WHITE);
    icon.setStroke(Color.GRAY);
    icon.setStrokeWidth(2);

    Text txt = new Text("i");
    txt.setFont(Font.font("Times New Romans", FontWeight.BOLD, 15));
    txt.setFill(Color.GRAY);

    stack.getChildren().addAll(icon, txt);

    stack.setOnMouseClicked(event -> {
      infoStage.show();
    });

    return stack;
  }

  private void initializeInfo() {
    infoStage = new Stage();
    Scene scene = new Scene(info.getView());
    infoStage.setScene(scene);
    infoStage.setTitle("Verse Copier Information");
  }

  private void updateState() {
    boolean shouldDisable = true;
    String msg;
    if (!checkBook()) {
      msg = "Invalid book name.";
    } else if (!checkChapter(book)){
      msg = "Invalid chapter number.";
    } else if (!checkVerseNumbers(book, chapter)) {
      msg = "Invalid verse numbers.";
    } else {
      shouldDisable = false;
      msg = "Copy Enabled.";
    }
    copyButton.setDisable(shouldDisable);
    status.setText(msg);
  }

  /********* Get Methods ***********/
  private boolean getRecitedOrRead() {
    // return beforeOrAfter.getValue().equals("Before");
    return recOrReadGroup.getSelectedToggle().getUserData().equals("Recited");
  }

  private boolean getBeforeOrAfter() {
    // return beforeOrAfter.getValue().equals("Before");
    return befAftGroup.getSelectedToggle().getUserData().equals("Before");
  }

  private String getBook() {
    return bookName.getText();
  }

  private int getBookNumber() {
    return formatter.getBookIndex(bookName.getText());
  }

  private int getChapterNumber() {
    try {
      return Integer.parseInt(chapNum.getText());
    } catch (Exception e) {
      return -1;
    }
  }

  private int getBeginningVerseNumber() {
    return getVerseNumberFromVerseField(verse1);
  }

  private int getEndingVerseNumber() {
    return getVerseNumberFromVerseField(verse2);
  }

  private int getVerseNumberFromVerseField(TextField verseField) {
    try {
      String input = verseField.getText();
      if (input.trim().length() == 0) return -2;
      return Integer.parseInt(input);
    } catch (Exception e) {
      return -1;
    }
  }

  /********** Methods to check validity *************/
  private boolean checkBook() {
    book = getBookNumber();
    return book >= 0;
  }

  private boolean checkChapter(int book) {
    chapter = getChapterNumber();
    return formatter.validChapter(book, chapter);
  }

  private boolean checkVerseNumbers(int book, int chapter) {
    int beginning = getBeginningVerseNumber();
    int ending = getEndingVerseNumber();

    boolean validBeginning = formatter.validVerse(book, chapter, beginning);
    boolean validEnding = formatter.validVerse(book, chapter, ending);

    if (validBeginning && ending == -2) {
      beginningVerse = beginning;
      endingVerse = beginning;
      return true;
    } else if (beginning == -2 && validEnding) {
      beginningVerse = ending;
      endingVerse = ending;
      return true;
    } else if (validBeginning && validEnding && (beginning <= ending)) {
      beginningVerse = beginning;
      endingVerse = ending;
      return true;
    }
    return false;
  }


  /**
   * Static void main method
   * @param args arguments
   */
  public static void main(String[] args) {
    launch(args);
  }

  public static boolean checkIfBiblesEqual(Bible bible1, Bible bible2) {
    String[][][] b1 = bible1.getFullIndexedBible();
    String[][][] b2 = bible2.getFullIndexedBible();
    if (b1.length != b2.length) {
      Printer.out.println("Number of books different!");
      return false;
    }
    boolean equal = true;
    for (int i = 0; i < b1.length; i++) {
      if (b1[i].length != b2[i].length) {
        Printer.out.println("Number of chapters different for book: " + i);
        equal = false;
      }
    }
    if (!equal) return false;

    for (int i = 0; i < b1.length; i++) {
      for (int j = 0; j < b1[i].length; j++) {
        if (b1[i][j].length != b2[i][j].length) {
          Printer.out.println("Number of verses different for: " + i + "," + j);
          equal = false;
        }
      }
    }
    return equal;
  }


}
