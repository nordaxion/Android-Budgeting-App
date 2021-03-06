package com.example.budgetingapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.budgetingapp.R;
import com.example.budgetingapp.model.BudgetingAppDatabase;
import com.example.budgetingapp.model.Transaction;
import com.example.budgetingapp.viewmodel.ApplicationViewModel;

import java.util.Date;

/**
 * Class containing functionality for activity 3.
 *
 * FEATURES:
 * - allows users to create new transactions by entering the amount, the name, and the type
 * - input validation implemented
 * - connects to activity 2
 */
public class Activity3 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ApplicationViewModel viewModel = new ApplicationViewModel();
    private Spinner spinner;
    private TextView amount;
    private EditText textName;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3);
        getSupportActionBar().hide();

        spinner = findViewById(R.id.transaction_menu);
        amount = findViewById(R.id.moneyAmount);
        textName = findViewById(R.id.transaction_name);

        // back button -> go back to activity 2
        ImageButton backButton = (ImageButton) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(Activity3.this, Activity2.class));
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.transaction_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // done button -> save transaction data to the database, check for valid info
        Button doneButton = (Button) findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String choice = spinner.getSelectedItem().toString();
                String s = (amount.getText().toString()).substring(1);
                double d = Double.valueOf(s);
                d = d*100;
                int transaction_amount = (int)d;
                String transaction_name = textName.getText().toString();

                if (!choice.equals("") && transaction_amount > 0 && transaction_name.length() > 0) {
                    saveData();
                    Toast.makeText(Activity3.this, "Transaction added!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Activity3.this, Activity2.class));
                }
                else if (transaction_amount == 0) {
                    Toast.makeText(Activity3.this, "Enter a transaction amount!", Toast.LENGTH_SHORT).show();
                }
                else if (choice.equals("")) {
                    Toast.makeText(Activity3.this, "Select a transaction type!", Toast.LENGTH_SHORT).show();
                }
                else if (transaction_name.length() == 0) {
                    Toast.makeText(Activity3.this, "Enter a transaction name!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void addNum(View v) {
        String amount = ((TextView) findViewById(R.id.moneyAmount)).getText().toString();
        if(amount.length() == 9) {
            Toast.makeText(Activity3.this, "No more digits can be entered!", Toast.LENGTH_SHORT).show();
            return;
        }

        String num = ((Button) v).getText().toString();
        StringBuilder sb = new StringBuilder();

        sb.append('$');
        if(amount.charAt(1) != '0') sb.append(amount.substring(1, amount.indexOf('.')));
        sb.append(amount.charAt(amount.indexOf('.') + 1));
        sb.append('.');
        sb.append(amount.charAt(amount.length() - 1));
        sb.append(num);
        ((TextView) findViewById(R.id.moneyAmount)).setText(sb.toString());

    }

    public void removeNum(View v) {

        String amount = ((TextView) findViewById(R.id.moneyAmount)).getText().toString();
        StringBuilder sb = new StringBuilder();
        StringBuilder sb_reverse = new StringBuilder();

        for(int i = amount.length() - 1; i >= 0; i--) sb_reverse.append(amount.charAt(i));

        sb.append(sb_reverse.charAt(1));
        sb.append(sb_reverse.charAt(3));
        sb.append('.');
        if(sb_reverse.charAt(4) == '$' && sb_reverse.charAt(3) != 0) sb.append("0$");
        else sb.append(sb_reverse.substring(4));

        sb.reverse();
        ((TextView) findViewById(R.id.moneyAmount)).setText(sb.toString());

    }

    // save data to database
    private void saveData() {
        spinner = findViewById(R.id.transaction_menu);
        amount = findViewById(R.id.moneyAmount);
        textName = findViewById(R.id.transaction_name);

        String transaction_type = spinner.getSelectedItem().toString();
        String transaction_name = textName.getText().toString();
        date = new Date();
        String s = (amount.getText().toString()).substring(1);
        double d = Double.valueOf(s);
        d = d*100;
        int transaction_amount = (int)d;
        if(!transaction_type.equals("") && transaction_amount > 0 && transaction_name != null) {
            Transaction transaction = new Transaction(date,transaction_amount,transaction_name,transaction_type);
            viewModel.insertToDatabase(transaction,getApplicationContext());
            //Toast.makeText(this,"DATA SAVED!",Toast.LENGTH_SHORT).show();
        }

    }

}