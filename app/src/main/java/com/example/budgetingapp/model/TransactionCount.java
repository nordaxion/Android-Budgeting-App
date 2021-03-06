package com.example.budgetingapp.model;

public class TransactionCount {
    private String transactionType;
    private int typeCount;

    /**
     * Constructor for the TransactionCount
     * These instances will be used to query the summaries for the
     * transactions
     * @param transactionType
     * @param typeCount
     */
    public TransactionCount(String transactionType, int typeCount) {
        this.transactionType = transactionType;
        this.typeCount = typeCount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public int getTypeCount() {
        return typeCount;
    }

    public void setTypeCount(int typeCount) {
        this.typeCount = typeCount;
    }
}
