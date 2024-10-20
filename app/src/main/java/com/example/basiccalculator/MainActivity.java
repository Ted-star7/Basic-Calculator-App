package com.example.basiccalculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText result;
    private double num1;
    private char operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the result EditText
        result = findViewById(R.id.result);

        // Initialize buttons
        Button btn_0 = findViewById(R.id.btn_0);
        Button btn_1 = findViewById(R.id.btn_1);
        Button btn_2 = findViewById(R.id.btn_2);
        Button btn_3 = findViewById(R.id.btn_3);
        Button btn_4 = findViewById(R.id.btn_4);
        Button btn_5 = findViewById(R.id.btn_5);
        Button btn_6 = findViewById(R.id.btn_6);
        Button btn_7 = findViewById(R.id.btn_7);
        Button btn_8 = findViewById(R.id.btn_8);
        Button btn_9 = findViewById(R.id.btn_9);
        Button btn_add = findViewById(R.id.btn_add);
        Button btn_sub = findViewById(R.id.btn_sub);
        Button btn_mul = findViewById(R.id.btn_mul);
        Button btn_div = findViewById(R.id.btn_div);
        Button btn_equals = findViewById(R.id.btn_equals);

        // Set onClick listeners for number buttons
        btn_0.setOnClickListener(v -> appendToResult("0"));
        btn_1.setOnClickListener(v -> appendToResult("1"));
        btn_2.setOnClickListener(v -> appendToResult("2"));
        btn_3.setOnClickListener(v -> appendToResult("3"));
        btn_4.setOnClickListener(v -> appendToResult("4"));
        btn_5.setOnClickListener(v -> appendToResult("5"));
        btn_6.setOnClickListener(v -> appendToResult("6"));
        btn_7.setOnClickListener(v -> appendToResult("7"));
        btn_8.setOnClickListener(v -> appendToResult("8"));
        btn_9.setOnClickListener(v -> appendToResult("9"));

        // Set onClick listeners for operation buttons
        btn_add.setOnClickListener(v -> setOperation('+'));
        btn_sub.setOnClickListener(v -> setOperation('-'));
        btn_mul.setOnClickListener(v -> setOperation('*'));
        btn_div.setOnClickListener(v -> setOperation('/'));

        // Set onClick listener for equals button
        btn_equals.setOnClickListener(v -> calculateResult());

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right , systemBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
    }

    private void appendToResult(String value) {
        StringBuilder currentText = new StringBuilder(result.getText().toString());
        currentText.append(value);
        result.setText(currentText.toString());
    }

    private void setOperation(char operation) {
        this.operation = operation;
        num1 = Double.parseDouble(result.getText().toString());
        result.setText("");
    }

    @SuppressLint("SetTextI18n")
    private void calculateResult() {
        // These can remain as class fields
        double num2 = Double.parseDouble(result.getText().toString());
        double resultValue = 0;

        switch (operation) {
            case '+':
                resultValue = num1 + num2;
                break;
            case '-':
                resultValue = num1 - num2;
                break;
            case '*':
                resultValue = num1 * num2;
                break;
            case '/':
                if (num2 != 0) {
                    resultValue = num1 / num2;
                } else {
                    result.setText("Error");
                    return;
                }
                break;
        }

        result.setText(String.valueOf(resultValue));
    }
}