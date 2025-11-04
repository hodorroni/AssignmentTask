package com.rony.assignment.features.notes.data

import android.content.Context
import android.location.Geocoder
import com.rony.assignment.features.notes.domain.AddressResolverRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

class AddressResolver(
    private val context: Context
): AddressResolverRepository {

    override suspend fun getAddressFromCoordinates(
        lat: Double?,
        long: Double?
    ): String? {
        return withContext(Dispatchers.IO) {
            try {
                if(lat == null || long == null) return@withContext null

                val geoCoder = Geocoder(context, Locale.getDefault())
                val addresses = geoCoder.getFromLocation(lat, long, 1)
                addresses?.firstOrNull()?.let { address ->
                    val city = address.locality ?: address.subAdminArea
                    val street = address.thoroughfare
                    listOfNotNull(city, street).joinToString(", ")
                }
            } catch (e: Exception) {
                null
            }
        }
    }
}