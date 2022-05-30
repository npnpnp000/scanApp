package com.mytaskgame.model.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey




// Define the Table name
@Entity(tableName = "number_table")
data class PlateNumber(
    @ColumnInfo(name = "plate_number") val plate_number: String?,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun toString(): String {
        return "User(number='$plate_number', id=$id)"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(plate_number)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlateNumber> {
        override fun createFromParcel(parcel: Parcel): PlateNumber {
            return PlateNumber(parcel)
        }

        override fun newArray(size: Int): Array<PlateNumber?> {
            return arrayOfNulls(size)
        }
    }
}