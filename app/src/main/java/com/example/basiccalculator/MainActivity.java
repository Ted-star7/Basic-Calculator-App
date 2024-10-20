package com.example.basiccalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText displayScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayScreen = findViewById(R.id.display_screen);

        // Initialize buttons
        Button button0 = findViewById(R.id.button_0);
        Button button1 = findViewById(R.id.button_1);
        Button button2 = findViewById(R.id.button_2);
        Button button3 = findViewById(R.id.button_3);
        Button button4 = findViewById(R.id.button_4);
        Button button5 = findViewById(R.id.button_5);
        Button button6 = findViewById(R.id.button_6);
        Button button7 = findViewById(R.id.button_7);
        Button button8 = findViewById(R.id.button_8);
        Button button9 = findViewById(R.id.button_9);
        Button buttonAdd = findViewById(R.id.button_add);
        Button buttonSubtract = findViewById(R.id.button_sub);
        Button buttonMultiply = findViewById(R.id.button_mul);
        Button buttonDivide = findViewById(R.id.button_div);
        Button buttonClear = findViewById(R.id.button_clear);
        Button buttonEquals = findViewById(R.id.button_equals);

        // Set button click listeners using lambdas
        button0.setOnClickListener(v -> updateDisplay(getString(R.string.button_0)));
        button1.setOnClickListener(v -> updateDisplay(getString(R.string.button_1)));
        button2.setOnClickListener(v -> updateDisplay(getString(R.string.button_2)));
        button3.setOnClickListener(v -> updateDisplay(getString(R.string.button_3)));
        button4.setOnClickListener(v -> updateDisplay(getString(R.string.button_4)));
        button5.setOnClickListener(v -> updateDisplay(getString(R.string.button_5)));
        button6.setOnClickListener(v -> updateDisplay(getString(R.string.button_6)));
        button7.setOnClickListener(v -> updateDisplay(getString(R.string.button_7)));
        button8.setOnClickListener(v -> updateDisplay(getString(R.string.button_8)));
        button9.setOnClickListener(v -> updateDisplay(getString(R.string.button_9)));

        buttonAdd.setOnClickListener(v -> updateDisplay(getString(R.string.button_add)));
        buttonSubtract.setOnClickListener(v -> updateDisplay(getString(R.string.button_sub)));
        buttonMultiply.setOnClickListener(v -> updateDisplay(getString(R.string.button_mul)));
        buttonDivide.setOnClickListener(v -> updateDisplay(getString(R.string.button_div)));

        buttonEquals.setOnClickListener(v -> calculateResult());
        buttonClear.setOnClickListener(v -> displayScreen.setText(R.string.display_initial_value));
    }

    private void updateDisplay(String value) {
        String currentText = displayScreen.getText().toString();
        if (currentText.equals(getString(R.string.display_initial_value))) {
            displayScreen.setText(value);
        } else {
            displayScreen.setText(currentText + value);
        }
    }

    private void calculateResult() {
        String currentText = displayScreen.getText().toString();
        try {
            double result = eval(currentText);
            displayScreen.setText(String.valueOf(result));
        } catch (Exception e) {
            displayScreen.setText(R.string.error_message);
        }
    }

    private double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double v = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return v;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double v = parseTerm();
                while (true) {
                    if (eat('+')) {
                        v += parseTerm(); // addition
                    } else if (eat('-')) {
                        v -= parseTerm(); // subtraction
                    } else {
                        break;
                    }
                }
                return v;
            }

            double parseTerm() {
                double v = parseFactor();
                while (true) {
                    if (eat('*')) {
                        v *= parseFactor(); // multiplication
                    } else if (eat('/')) {
                        v /= parseFactor(); // division
                    } else {
                        break;
                    }
                }
                return v;
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double v;
                int startPos = this.pos ;
                if (eat('(')) { // parentheses
                    v = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    v = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    v = parseFactor();
                    if (func.equals("sqrt")) v = Math.sqrt(v);
                    else if (func.equals("sin")) v = Math.sin(Math.toRadians(v));
                    else if (func.equals("cos")) v = Math.cos(Math.toRadians(v));
                    else if (func.equals("tan")) v = Math.tan(Math.toRadians(v));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }

                return v;
            }
        }.parse();
    }
}