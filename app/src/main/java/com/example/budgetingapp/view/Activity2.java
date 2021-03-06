package com.example.budgetingapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.budgetingapp.R;
import com.example.budgetingapp.model.Transaction;
import com.example.budgetingapp.viewmodel.ApplicationViewModel;

import java.util.List;

/**
 * Class containing functionality for activity 2.
 *
 * FEATURES:
 * - displays full list of stored transactions
 * - connects to activity 1, 3, and 4
 */
public class Activity2 extends AppCompatActivity {

    private ApplicationViewModel viewModel = new ApplicationViewModel();
    private List<Transaction> transactionList;
    private listAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
        getSupportActionBar().hide();
        // button for adding transactions -> go to activity 3
        ImageButton addButton = (ImageButton) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(Activity2.this, Activity3.class));
            }
        });

        // button for removing transactions -> go to activity 4
        ImageButton removeButton = (ImageButton) findViewById(R.id.removeButton);
        removeButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(Activity2.this, Activity4.class));
            }
        });

        // back button -> go back to activity 2
        ImageButton backButton = (ImageButton) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(Activity2.this, MainActivity.class));
            }
        });
        initRecyclerView();
        loadTransactions();
    }

    // initialize the recycler view for transaction list
    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        listAdapter = new listAdapter(this);
        recyclerView.setAdapter(listAdapter);
    }

    // get the transactions from the database to display in the recycler view
    private void loadTransactions() {
        transactionList = viewModel.getAllTransactions(this.getApplicationContext());
        listAdapter.setTransactionList(transactionList);
    }

}