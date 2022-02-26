package com.dev_yogesh.montra.repo



import com.dev_yogesh.montra.local.AppDatabase
import javax.inject.Inject
import com.dev_yogesh.montra.model.Transaction

class TransactionRepo @Inject constructor(private val db: AppDatabase) {

    // insert transaction
    suspend fun insert(transaction: Transaction) = db.getTransactionDao().insertTransaction(
        transaction
    )


    // update transaction
    suspend fun update(transaction: Transaction) = db.getTransactionDao().updateTransaction(
        transaction
    )

    // delete transaction
    suspend fun delete(transaction: Transaction) = db.getTransactionDao().deleteTransaction(
        transaction
    )

    // get all transaction
    fun getAllTransactions() = db.getTransactionDao().getAllTransactions()

    // get single transaction type - Expense or Income or else overall
    fun getAllSingleTransaction(transactionType: String) = if (transactionType == "Overall") {
        getAllTransactions()
    } else {
        db.getTransactionDao().getAllSingleTransaction(transactionType)
    }

    // get transaction by ID
    fun getByID(id: Int) = db.getTransactionDao().getTransactionByID(id)

    // delete transaction by ID
    suspend fun deleteByID(id: Int) = db.getTransactionDao().deleteTransactionByID(id)
}
