package romanarabicconversion;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.stage.StageStyle.UTILITY;
import static romanarabicconversion.Converter.ARABIC_PATTERN;
import static romanarabicconversion.Converter.ROMAN_PATTERN;

public class ConversionApp extends Application {
    /**
     * A root layout of an application.
     */
    private VBox root;

    /**
     * A label with the instruction text.
     */
    private Label instruction;

    /**
     * A text field for input.
     */
    private TextField input;

    /**
     * A button which does the conversion.
     */
    private Button convert;

    /**
     * A text field for output.
     */
    private TextField output;

    /**
     * A button which saves the output to the clipboard.
     */
    private Button copy;

    @Override
    public void init() {
        root = new VBox();
        instruction = new Label();
        input = new TextField();
        convert = new Button();
        output = new TextField();
        copy = new Button();
    }

    @Override
    public void start(final Stage window) {
        root.setId("root");

        instruction.setId("instruction");
        instruction.setText("Enter Roman or Arabic literal:");

        input.setId("input");

        convert.setId("convert");
        convert.setText("Convert!");
        convert.setOnAction(eh -> {
            final String input = this.input.getText().toUpperCase();

            if (input.matches(ARABIC_PATTERN) || input.matches(ROMAN_PATTERN)) {
                output.setText(String.valueOf(Converter.convert(input)));
            } else {
                Alert error = new Alert(ERROR);
                error.setTitle("Invalid input");
                error.setHeaderText
                ("You must enter the number in range of 1-3999 or I-MMMCMXCIX!");
                
                error.showAndWait();

                this.output.clear();
            }

            this.input.clear();
        });

        output.setId("output");
        output.setEditable(false);

        copy.setId("copy");
        copy.setText("Copy the output!");
        copy.setOnAction(eh -> {
            String output = this.output.getText();

            if (output != null && !output.isEmpty() && !output.isBlank()) {
                Clipboard clipboard =
                        Toolkit.getDefaultToolkit().getSystemClipboard();

                clipboard.setContents(new StringSelection(output), null);

                this.output.clear();
            }
        });

        root.getChildren().addAll(instruction, input, convert, output, copy);

        root.getStylesheets().add("ApplicationAppearance.css");

        Scene scene = new Scene(root);

        window.setScene(scene);
        window.setTitle("Roman-Arabic conversion");
        window.initStyle(UTILITY);
        window.setResizable(false);
        window.show();
    }
}