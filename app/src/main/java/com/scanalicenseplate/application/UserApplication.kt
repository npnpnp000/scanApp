package com.mytaskgame.application

import android.app.Application
import com.mytaskgame.model.Repository
import com.mytaskgame.model.database.MemoryRoomDatabase

/**
 * A application class where we can define the variable scope to use through out the application.
 */
class UserApplication() : Application() {

    private val roomDatabase by lazy { MemoryRoomDatabase.getDatabase(this@UserApplication) }

    // A variable for repository.
    val repository by lazy { Repository(roomDatabase.memoryDou()) }
}