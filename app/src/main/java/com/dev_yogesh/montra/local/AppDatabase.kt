package com.dev_yogesh.montra.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dev_yogesh.montra.local.TransactionDao
import com.dev_yogesh.montra.model.Transaction

@Database(
    entities = [Transaction::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getTransactionDao(): TransactionDao
}
