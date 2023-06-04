package com.pucuk.binar_challenge_ch_7.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase



@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class  AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}