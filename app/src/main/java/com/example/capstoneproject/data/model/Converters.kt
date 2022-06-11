package com.example.capstoneproject.data.model

import androidx.room.TypeConverter
import com.example.capstoneproject.data.model.user.Name

class Converters {

    @TypeConverter
    fun fromName(name: Name): String {
        return name.firstname
    }

    @TypeConverter
    fun toName(name: String): Name {
        return Name(name, name)
    }

}