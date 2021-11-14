package es.christianbegines.wonkamanagement.remote.models

import es.christianbegines.wonkamanagement.models.OompaLoompa

data class OommpaLoompaResponse (
    val results: List<OompaLoompa>,
    val total: Int,
    val current: Int
)