package edu.ucne.prioridades.data.dl

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.prioridades.data.local.database.PrioridadDb
import edu.ucne.prioridades.data.remote.ClienteApi
import edu.ucne.prioridades.data.remote.PrioridadesApi
import edu.ucne.prioridades.data.remote.SistemaApi
import edu.ucne.prioridades.data.remote.TicketsApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Singleton



@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    const val BASE_URL = "https://prioridadesapp2.azurewebsites.net/"

    @Singleton
    @Provides
    fun providesMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(DateAdapter())
            .build()

    @Provides
    @Singleton
    fun providesClienteApi(moshi: Moshi): ClienteApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ClienteApi::class.java)
    }

    @Provides
    @Singleton
    fun providesPrioridadApi(moshi: Moshi): PrioridadesApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(PrioridadesApi::class.java)
    }

    @Provides
    @Singleton
    fun providesSistemaApi(moshi: Moshi): SistemaApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(SistemaApi::class.java)
    }

    @Provides
    @Singleton
    fun providesTicketApi(moshi: Moshi): TicketsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TicketsApi::class.java)
    }

    @Provides
    @Singleton
    fun providePrioridadDb(@ApplicationContext appContext: Context)=
        Room.databaseBuilder(
            appContext,
            PrioridadDb::class.java,
            "Prioridad.db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun providePrioridadDao(prioridadDb: PrioridadDb) =prioridadDb.prioridadDao()

    @Provides
    @Singleton
    fun provideTicketDao(prioridadDb: PrioridadDb) = prioridadDb.ticketDao()

    class DateAdapter {
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)

        @ToJson
        fun toJson(value: Date?): String? {
            return value?.let { dateFormat.format(it) }
        }

        @FromJson
        fun fromJson(value: String?): Date? {
            return value?.let { dateFormat.parse(it) }
        }
    }
}