package com.saulo.ulpgcarapp.domain.use_cases.chat

import android.os.Build
import androidx.annotation.RequiresApi
import com.saulo.ulpgcarapp.domain.model.Message
import com.saulo.ulpgcarapp.domain.model.Response
import com.saulo.ulpgcarapp.domain.repository.ChatRepository
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import javax.inject.Inject

class SendMessage @Inject constructor(private val repository: ChatRepository){

    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(userId: String, msg:String, publishId: String, photo:String, name: String): Response<Boolean> {
        /*val calendar = Calendar.getInstance()

        val hour = calendar.get(Calendar.HOUR)
        val min = calendar.get(Calendar.MINUTE)
        val sec = calendar.get(Calendar.SECOND)
        val milsec = calendar.get(Calendar.MILLISECOND)

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
         */

        val fechaActual: LocalDate = LocalDate.now()
        val formatoFecha: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val fechaFormateada: String = fechaActual.format(formatoFecha)

        val horaActual: LocalTime = LocalTime.now()
        val formatoHora: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        val horaFormateada: String = horaActual.format(formatoHora)

        val horaActualSimple: LocalTime = LocalTime.now()
        val formatoHoraSimple: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        val horaSimpleFormateada: String = horaActualSimple.format(formatoHoraSimple)

        val message = Message(
            userId = userId,
            contenido = msg,
            hora = horaFormateada,
            fecha = fechaFormateada,
            horaSimple = horaSimpleFormateada,
            imagen = photo,
            name = name
        )

        return repository.sendMessage(message, publishId, photo, name)
    }

}