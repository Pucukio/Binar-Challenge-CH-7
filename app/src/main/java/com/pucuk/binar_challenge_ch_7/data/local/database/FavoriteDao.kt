package com.pucuk.binar_challenge_ch_7.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.pucuk.binar_challenge_ch_7.data.local.database.FavoriteEntity

@Dao
interface FavoriteDao {

    @Query("SELECT * from favorite_table where uuid_user = :uuid_user")
    suspend fun getFavoriteTickets(uuid_user: String): List<FavoriteEntity>

    @Query("SELECT EXISTS(SELECT * FROM favorite_table WHERE id_movie = :id_movie AND uuid_user = :uuid_user)")
    suspend fun isFavorite(id_movie: Int, uuid_user: String): Boolean

    @Insert
    suspend fun addFavorite(favoriteEntity: FavoriteEntity)

    @Query("DELETE FROM favorite_table WHERE id_movie=:id_movie AND uuid_user=:uuid_user")
    suspend fun deleteFavorite(id_movie: Int, uuid_user: String)

}