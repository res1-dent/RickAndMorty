package com.sometime.rickandmorty.data.mappers

import com.sometime.rickandmorty.data.entities.RemotePerson
import com.sometime.rickandmorty.domain.entities.Person
import javax.inject.Inject

fun RemotePerson.toPerson(): Person = Person(
    id = this.id,
    gender = this.gender,
    name = this.name,
    status = this.status,
    image = this.image,
    species = this.species
)