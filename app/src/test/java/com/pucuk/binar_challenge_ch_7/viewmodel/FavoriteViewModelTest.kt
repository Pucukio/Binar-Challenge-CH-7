package com.example.challangechap6_fikrihaikal.viewmodel

import CoroutineTestRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.pucuk.binar_challenge_ch_7.data.local.database.FavoriteEntity
import com.pucuk.binar_challenge_ch_7.data.repository.LocalRepository
import com.google.firebase.auth.FirebaseUser
import com.nhaarman.mockitokotlin2.verify
import com.pucuk.binar_challenge_ch_7.ui.viewmodel.FavoriteViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@ExperimentalCoroutinesApi
class FavoriteViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private lateinit var viewModel: FavoriteViewModel

    @Mock
    private lateinit var localRepository: LocalRepository

    @Mock
    private lateinit var favoriteObserver: Observer<List<FavoriteEntity>>

    @Mock
    private lateinit var userObserver: Observer<FirebaseUser?>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = FavoriteViewModel(localRepository)
        viewModel.favorite.observeForever(favoriteObserver)
        viewModel.user.observeForever(userObserver)
    }

    @After
    fun tearDown() {
        viewModel.favorite.removeObserver(favoriteObserver)
        viewModel.user.removeObserver(userObserver)
    }



    @Test
    fun `test getAllFavorites`() = coroutineRule.testDispatcher.runBlockingTest {
        // Arrange
        val uuid = "example-uuid"
        val favoriteList = listOf(
            FavoriteEntity(1, "photo1", "apahayo1", "kocak1"),
            FavoriteEntity(2, "Example 2", "tittle 2", "kocak2")
        )
        Mockito.`when`(localRepository.getFavoriteTickets(uuid)).thenReturn(favoriteList)

        // Act
        viewModel.getAllFavorites(uuid)

        // Assert
        verify(favoriteObserver).onChanged(favoriteList)
    }
}