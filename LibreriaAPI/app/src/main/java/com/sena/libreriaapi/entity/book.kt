package com.sena.libreriaapi.entity

// se crean los atributos del modelo
data class book(
    var id: Int,
    var titulo: String,
    var autor: String,
    var isbn: String,
    var genero: String,
    var num_ejem_disponible: Int,
    var num_ejem_ocupados: Int

)
