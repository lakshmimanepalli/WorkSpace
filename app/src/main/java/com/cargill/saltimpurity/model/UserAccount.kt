package com.cargill.saltimpurity.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_account")
data class UserAccount(

    @SerializedName("id")
    @Expose
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var pk: Int,

    @SerializedName("email")
    @Expose
    @ColumnInfo(name = "email") var email: String,

    @SerializedName("username")
    @Expose
    @ColumnInfo(name = "username") var username: String
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) return false

        other as UserAccount

        if (pk != other.pk) return false
        if (email != other.email) return false
        if (username != other.username) return false

        return true
    }

}
