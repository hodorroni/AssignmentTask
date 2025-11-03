package com.rony.assignment.features.notes.data

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.content.getSystemService
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.rony.assignment.features.notes.domain.Location
import com.rony.assignment.features.notes.domain.LocationObserver
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf


//Basically if user walks and is creating a note, we will get updates on his location
//every interval time we set from outside!
class LocationObserverImpl(
    private val context: Context
): LocationObserver {

    val client = LocationServices.getFusedLocationProviderClient(context)

    override fun observeLocation(interval: Long): Flow<Location> {
        return callbackFlow {
            val locationManager = context.getSystemService<LocationManager>()!!
            val isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if(!isEnabled) {
                return@callbackFlow
            }
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                close()
            } else {
                client.lastLocation.addOnSuccessListener {
                    it?.let { location ->
                        trySend(Location(
                            latitude = location.latitude,
                            longitude = location.longitude
                        ))
                    }
                }

                val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, interval)
                    .build()

                val locationCallback = object : LocationCallback() {
                    override fun onLocationResult(result: LocationResult) {
                        super.onLocationResult(result)
                        result.locations.lastOrNull()?.let { location ->
                            trySend(Location(
                                latitude = location.latitude,
                                longitude = location.longitude
                            ))
                        }
                    }
                }
                client.requestLocationUpdates(request, locationCallback, Looper.getMainLooper())
                awaitClose {
                    client.removeLocationUpdates(locationCallback)
                }
            }
        }
    }
}