package com.sometime.rickandmorty.domain.entities

data class Person(
    val id: Int,
    val name: String,
    val status: String,
    val gender: String,
    val image: String,
    val species: String
)
