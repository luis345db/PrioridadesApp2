package edu.ucne.prioridades.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import edu.ucne.prioridades.data.local.entities.TicketEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TicketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ticket: TicketEntity)

    @Update
    suspend fun update(ticket: TicketEntity)

    @Upsert
    suspend fun save(ticket: TicketEntity)

    @Delete
    suspend fun delete(ticket: TicketEntity)

    @Query("SELECT * FROM Tickets WHERE ticketId = :id LIMIT 1")
    suspend fun find(id: Int): TicketEntity?

    @Query("SELECT * FROM Tickets WHERE cliente = :cliente LIMIT 1")
    suspend fun findCliente(cliente: String): TicketEntity?

    @Query("SELECT * FROM Tickets WHERE asunto = :asunto LIMIT 1")
    suspend fun findAsunto(asunto: String): TicketEntity?

    @Query("SELECT * FROM Tickets WHERE descripcion = :descripcion LIMIT 1")
    suspend fun findDescripcion(descripcion: String): TicketEntity?

    @Query("SELECT * FROM Tickets")
    fun getAll(): Flow<List<TicketEntity>>
}

