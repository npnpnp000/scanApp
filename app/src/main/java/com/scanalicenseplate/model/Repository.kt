package com.mytaskgame.model

import android.util.Log
import androidx.annotation.WorkerThread
import com.mytaskgame.model.database.MemoryDou
import com.mytaskgame.model.entities.PlateNumber

class Repository(private val memoryDou: MemoryDou) {

    @WorkerThread
    suspend fun insertPlateNumberDataRoom(plateNumber: PlateNumber) {
        Log.e("insertUserDataRoom","start")
        memoryDou.insertNumber(plateNumber)
        Log.i("insertUserDataRoom",plateNumber.toString())
    }


}