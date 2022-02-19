package com.sometime.rickandmorty.data.mappers

import com.sometime.rickandmorty.data.entities.RemotePerson
import com.sometime.rickandmorty.domain.entities.Person
import timber.log.Timber
import javax.inject.Inject

class RemoteMapper @Inject constructor() {

    fun toPersonList(remotePersons: List<RemotePerson>): List<Person> {
        Timber.e("remote person = ${remotePersons.first()}")
        return remotePersons.map {
            Person(
                id = it.id,
                gender = it.gender,
                name = it.name,
                status = it.status,
                image = it.image,
                species = it.species
            )
        }
    }

    fun toPerson(remotePerson: RemotePerson): Person {
        return Person(
            id = remotePerson.id,
            gender = remotePerson.gender,
            name = remotePerson.name,
            status = remotePerson.status,
            image = remotePerson.image,
            species = remotePerson.species
        )
    }
}