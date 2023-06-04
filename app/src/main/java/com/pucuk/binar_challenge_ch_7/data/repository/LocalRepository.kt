package com.pucuk.binar_challenge_ch_7.data.repository

import com.pucuk.binar_challenge_ch_7.data.local.database.FavoriteDao
import com.pucuk.binar_challenge_ch_7.data.local.database.FavoriteEntity
import javax.inject.Inject

interface LocalRepository {
    suspend fun getFavoriteTickets(uuid_user: String): List<FavoriteEntity>
    suspend fun isFavorite(id_movie: Int, uuid_user: String): Boolean
    suspend fun addFavorite(favoriteEntity: FavoriteEntity)
    suspend fun deleteFavorite(id_movie: Int, uuid_user: String)
}

class LocalRepositoryImpl @Inject constructor(private val favoriteDao: FavoriteDao) :
    LocalRepository {
    override suspend fun getFavoriteTickets(uuid_user: String): List<FavoriteEntity> =
        favoriteDao.getFavoriteTickets(uuid_user)

    override suspend fun isFavorite(id_movie: Int, uuid_user: String): Boolean =
        favoriteDao.isFavorite(id_movie, uuid_user)

    override suspend fun addFavorite(favoriteEntity: FavoriteEntity) =
        favoriteDao.addFavorite(favoriteEntity)

    override suspend fun deleteFavorite(id_movie: Int, uuid_user: String) =
        favoriteDao.deleteFavorite(id_movie, uuid_user)

}