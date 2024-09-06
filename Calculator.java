import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator implements ActionListener {
    JFrame frame;
    JTextField displayTextField;
    JPanel panel;

    // Add "C" button for clear functionality
    String[] buttons = {
        "7", "8", "9", "/", 
        "4", "5", "6", "*", 
        "1", "2", "3", "-", 
        "0", ".", "C", "+", 
        "="
    };

    JButton[] buttonArray = new JButton[buttons.length];
    String displayText = "";

    public Calculator() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLayout(new BorderLayout());

        displayTextField = new JTextField();
        displayTextField.setPreferredSize(new Dimension(400, 50));
        displayTextField.setEditable(false);
        frame.add(displayTextField, BorderLayout.NORTH);

        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4)); // Update to 5 rows to accommodate the new button

        for (int i = 0; i < buttons.length; i++) {
            buttonArray[i] = new JButton(buttons[i]);
            buttonArray[i].addActionListener(this);
            panel.add(buttonArray[i]);
        }

        frame.add(panel);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.matches("[0-9]") || command.equals(".")) {
            // Append number or decimal point to the display
            displayText += command;
            displayTextField.setText(displayText);
        } else if (command.equals("+") || command.equals("-") || command.equals("*") || command.equals("/")) {
            // Append the operator to the display
            displayText += " " + command + " ";
            displayTextField.setText(displayText);
        } else if (command.equals("=")) {
            try {
                // Split the input and perform the calculation
                String[] parts = displayText.split(" ");
                
                // Ensure there are at least 3 parts: operand1, operator, operand2
                if (parts.length < 3) {
                    displayTextField.setText("Invalid Expression");
                    return;
                }

                double num1 = Double.parseDouble(parts[0]);
                String operator = parts[1];
                double num2 = Double.parseDouble(parts[2]);

                double result = 0;

                switch (operator) {
                    case "+":
                        result = num1 + num2;
                        break;
                    case "-":
                        result = num1 - num2;
                        break;
                    case "*":
                        result = num1 * num2;
                        break;
                    case "/":
                        if (num2 != 0) {
                            result = num1 / num2;
                        } else {
                            displayTextField.setText("Cannot divide by zero");
                            return;
                        }
                        break;
                    default:
                        displayTextField.setText("Unknown operator");
                        return;
                }

                // Update the display with the result (removing .0 if it's an integer)
                displayTextField.setText((result % 1 == 0) ? String.valueOf((int) result) : String.valueOf(result));
                displayText = "";  // Clear for next input

            } catch (NumberFormatException ex) {
                displayTextField.setText("Error");
                ex.printStackTrace();
            }
        } else if (command.equals("C")) {
            // Clear the display
            displayText = "";
            displayTextField.setText("");
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}
