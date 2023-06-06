import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pucuk.binar_challenge_ch_7.data.di.LocalModule
import com.pucuk.binar_challenge_ch_7.data.local.database.AppDatabase
import com.pucuk.binar_challenge_ch_7.data.local.database.FavoriteDao
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
class LocalModuleTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var favoriteDao: FavoriteDao

    @Before
    fun setup() {
        val context: Context = ApplicationProvider.getApplicationContext()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        favoriteDao = appDatabase.favoriteDao()
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    @Test
    fun testProvidesDatabaseShouldReturnNonNullAppDatabaseInstance() {
        val context: Context = ApplicationProvider.getApplicationContext()
        val database = LocalModule.providesDatabase(context)

        assertNotNull(database)
    }

    @Test
    fun testProvideFavoriteDaoShouldReturnNonNullFavoriteDaoInstance() {
        val dao = LocalModule.provideFavoriteDao(appDatabase)

        assertNotNull(dao)
    }
}
