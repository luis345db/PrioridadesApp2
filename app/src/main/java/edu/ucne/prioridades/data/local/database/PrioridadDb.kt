package edu.ucne.prioridades.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.ucne.prioridades.data.local.dao.PrioridadDao
import edu.ucne.prioridades.data.local.dao.TicketDao
import edu.ucne.prioridades.data.local.entities.PrioridadEntity
import edu.ucne.prioridades.data.local.entities.TicketEntity

@Database(
    entities = [
        PrioridadEntity::class
        ,TicketEntity::class
    ],
    version = 4,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class PrioridadDb : RoomDatabase() {
    abstract fun prioridadDao(): PrioridadDao
    abstract fun ticketDao(): TicketDao
}