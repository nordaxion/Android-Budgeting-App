package com.example.budgetingapp.model;

import androidx.room.*;

import java.util.List;

@Dao
public interface TransactionDao {
    @Insert
    void insertTransaction(Transaction transaction);

    @Delete
    void deleteTransaction(Transaction transaction);

    @Query("SELECT * FROM transactions ORDER BY date, transaction_id DESC")
    List<Transaction> getTransactions();
}
