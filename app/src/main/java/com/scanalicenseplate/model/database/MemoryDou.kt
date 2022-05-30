package com.mytaskgame.model.database


import androidx.room.Dao
import androidx.room.Insert
import com.mytaskgame.model.entities.PlateNumber


@Dao
interface MemoryDou {

    // add user to DB
    @Insert
    suspend fun insertNumber(plateNumber: PlateNumber )
//
//    // get all the users from DB
//    @Query("SELECT * FROM USERS_TABLE ORDER BY ID")
//    fun getAllUsersList() : Flow<List<User>>


}