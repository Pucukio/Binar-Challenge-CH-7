package com.pucuk.binar_challenge_ch_7.data.local.repository

import com.pucuk.binar_challenge_ch_7.data.local.database.FavoriteDao
import com.pucuk.binar_challenge_ch_7.data.local.database.FavoriteEntity
import com.nhaarman.mockitokotlin2.mock
import com.pucuk.binar_challenge_ch_7.data.repository.LocalRepository
import com.pucuk.binar_challenge_ch_7.data.repository.LocalRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class LocalRepositoryImplTest{

    @Mock
    private lateinit var favoriteDao : FavoriteDao

    private lateinit var localRepository: LocalRepository

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        localRepository = LocalRepositoryImpl(favoriteDao)

    }

    @Test
    fun testGetFavoriteTickets() = runBlocking {
        val uuidUser = "12345"
        val favoriteTickets = listOf(FavoriteEntity(1, "zxczxczx", "zxxzc", "zcxzx"))

        val favoriteDao = mock<FavoriteDao>()
        `when`(favoriteDao.getFavoriteTickets(uuidUser)).thenReturn(favoriteTickets)
        val localRepository = LocalRepositoryImpl(favoriteDao)
        val result = localRepository.getFavoriteTickets(uuidUser)
        assertEquals(favoriteTickets, result)
    }


    @Test
    fun testDeleteFavorite() = runBlocking {
        val idMovie = 123
        val uuidUser = "12345"
        val result = localRepository.deleteFavorite(idMovie, uuidUser)
        assertNotNull(result)

    }

}