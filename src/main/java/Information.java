import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class Information {
    FileInputStream korean;
	BufferedReader inKorean;

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
        titles = new ArrayList<String>();
        infos = new ArrayList<String>();

        readFile();

        initListView();
        initTextArea();

        borderPane.setLeft(listView);
        borderPane.setCenter(textArea);

    }

    public void initListView() throws IOException {
        listView = new ListView<String>(FXCollections.observableArrayList(titles));

        listView.getSelectionModel().selectedItemProperty().addListener(
            (a, b, newValue) -> {
                int ndx = listView.getSelectionModel().getSelectedIndex();
                textArea.setText(infos.get(ndx));
            });

    }

    public void initTextArea() {
        textArea = new TextArea(infos.get(0));
        textArea.setWrapText(true);
    }

    public void readFile() throws IOException {
        FileInputStream korean;
        BufferedReader inKorean;
        korean = new FileInputStream(fileName);
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
