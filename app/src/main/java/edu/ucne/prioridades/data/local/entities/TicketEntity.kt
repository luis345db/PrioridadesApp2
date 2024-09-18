package edu.ucne.prioridades.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "Tickets",
    foreignKeys = [
        ForeignKey(
            entity = PrioridadEntity::class,
            parentColumns = ["prioridadId"],
            childColumns = ["prioridadId"]
        )
    ],
    indices = [Index("prioridadId")]
)


data class TicketEntity(
    @PrimaryKey(autoGenerate = true)
    val ticketId: Int? = null,
    val fecha: Date? = null,
    val prioridadId: Int,
    val cliente: String = "",
    val asunto: String = "",
    val descripcion: String= ""
)
