package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;

/**
 *This dialog is created dynamically to get user inputs
 * Each parameter (key) in the template creates a corresponding input field
 * When the user presses OK the entered values are returned as a Map<String, Object>.
 */
public class PluginConfigDialog extends Dialog<Map<String, Object>> {

    private Map<String, TextField> fieldMap;

    /**
     * Constructs the dialog using the provided parameter template.
     * The template keys are used as labels, and any non-null default values are pre-populated.
     * 
     * @param parameterTemplate A Map where keys are parameter names and values are default values (or null).
     */
    public PluginConfigDialog(Map<String, Object> parameterTemplate) {
        super();
        
        this.setTitle("Plugin Configuration");
        
        // Initialize the field map to store TextFields for each parameter.
        fieldMap = new HashMap<String, TextField>();
        
        // Create a grid pane layout for the form.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        
        // Loop over all parameter keys in the template
        int row = 0;
        for (String key : parameterTemplate.keySet()) {
            // Create a Label for the parameter
            Label label = new Label(key + ":");
            
            // Create a textField for user input
            TextField textField = new TextField();
            // If a default value is provided set it as the text of the TextField
            Object defaultValue = parameterTemplate.get(key);
            if (defaultValue != null) {
                textField.setText(defaultValue.toString());
            }
            
            // Add the Label and TextField to the grid pane on separate columns
            grid.add(label, 0, row);
            grid.add(textField, 1, row);
            
            // Store the TextField in the map for later use of the value
            fieldMap.put(key, textField);
            
            row = row + 1;
        }
        
        //have the grindpane to set the contents
        this.getDialogPane().setContent(grid);
        
        //OK and Cancel buttons for the dialog
        ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        this.getDialogPane().getButtonTypes().addAll(okButtonType, cancelButtonType);
        
        // If OK is pressed we pass the input in the map
        this.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                Map<String, Object> resultMap = new HashMap<String, Object>();
                // Loop over each key to get the corresponding value from the TextField
                for (String param : fieldMap.keySet()) {
                	TextField tf = fieldMap.get(param);
                    String input = tf.getText();
                	 Object defaultValue = fieldMap.get(param);
                    // later maybe we change the string to the needed class like Int etc
                    Object convertedValue = input; // default to input string
                    
                    if (defaultValue instanceof Integer) { 
                        try {
                            convertedValue = Integer.parseInt(input);
                        } catch (NumberFormatException nfe) {
                            convertedValue = defaultValue; // fallback to default if parsing fails
                        }
                    } else if (defaultValue instanceof Double) { 
                        try {
                            convertedValue = Double.parseDouble(input);
                        } catch (NumberFormatException nfe) {
                            convertedValue = defaultValue;
                        }
                    } else if (defaultValue instanceof Boolean) { 
                        // Convert true/false strings to Boolean.
                        convertedValue = Boolean.parseBoolean(input); 
                    }
                    // Add the converted parameter value to the result map.
                    resultMap.put(param, convertedValue);
                }
                return resultMap;
            }
            return null;
        });
    }
}
