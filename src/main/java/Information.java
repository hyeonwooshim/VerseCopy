import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class Information {
  private BorderPane borderPane;
  private ScrollPane scrollPane;
  private ListView<String> listView;
  private TextArea textArea;

  private String fileName;
  private ArrayList<String> titles;
  private ArrayList<String> infos;


  public Information(String fileName) throws IOException {
    this.fileName = fileName;
    borderPane = new BorderPane();
    titles = new ArrayList<>();
    infos = new ArrayList<>();

    readFile();

    initListView();
    initTextArea();

    borderPane.setLeft(listView);
    borderPane.setCenter(textArea);

  }

  public void initListView() throws IOException {
    listView = new ListView<>(FXCollections.observableArrayList(titles));

    listView.getSelectionModel().selectedItemProperty().addListener(
        (a, b, newValue) -> {
          int ndx = listView.getSelectionModel().getSelectedIndex();
          textArea.setText(infos.get(ndx));
        });

  }

  public void initTextArea() {
    textArea = new TextArea(infos.get(0));
    textArea.setWrapText(true);
    textArea.setEditable(false);
  }

  public void readFile() throws IOException {
    InputStream korean;
    BufferedReader inKorean;
    korean = Information.class.getResourceAsStream(fileName);
    inKorean = new BufferedReader(new InputStreamReader(korean, "UTF8"));

    String line = inKorean.readLine();
    line = line.substring(1); //this accounts for random ? character for first line

    while (line != null){
      String info = "";
      if (line.substring(0, 1).equals("@")) {
        titles.add(line.substring(1));
        while((line = inKorean.readLine()) != null) {
          if (line.isEmpty()){
            info += line + "\n";
          } else if (!line.substring(0, 1).equals("@")) {
            info += line + "\n";
          } else {
            break;
          }
        }
        infos.add(info);
      }
    }
  }

  public Pane getView() {
    return borderPane;
  }
}

