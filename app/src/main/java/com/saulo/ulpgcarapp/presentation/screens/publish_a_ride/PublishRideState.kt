package com.saulo.ulpgcarapp.presentation.screens.publish_a_ride

import com.saulo.ulpgcarapp.domain.model.Publish

data class PublishRideState (
    var publications:List<Publish> = emptyList()
        )