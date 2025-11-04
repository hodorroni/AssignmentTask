package com.rony.assignment.features.notes.domain

interface AddressResolverRepository {
    suspend fun getAddressFromCoordinates(lat: Double?, long: Double?): String?
}