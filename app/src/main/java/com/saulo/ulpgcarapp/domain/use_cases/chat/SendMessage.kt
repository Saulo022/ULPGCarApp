package com.saulo.ulpgcarapp.domain.use_cases.chat

import com.saulo.ulpgcarapp.domain.model.Message
import com.saulo.ulpgcarapp.domain.model.Response
import com.saulo.ulpgcarapp.domain.repository.ChatRepository
import java.util.Calendar
import javax.inject.Inject

class SendMessage @Inject constructor(private val repository: ChatRepository){

    suspend operator fun invoke(userId: String,msg:String): Response<Boolean> {
        val calendar = Calendar.getInstance()

        val hour = calendar.get(Calendar.HOUR)
        val min = calendar.get(Calendar.MINUTE)
        val sec = calendar.get(Calendar.SECOND)
        val milsec = calendar.get(Calendar.MILLISECOND)

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val message = Message(
            userId = userId,
            contenido = msg,
            hora = "$hour:$min:$sec:$milsec",
            fecha = "$day/$month/$year"
        )

        return repository.sendMessage(message)
    }

}