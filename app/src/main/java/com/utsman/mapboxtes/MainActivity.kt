package com.utsman.mapboxtes

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.Style
import com.utsman.smartmarker.location.LocationListener
import com.utsman.smartmarker.location.LocationWatcher
import com.utsman.smartmarker.mapbox.MarkerOptions
import com.utsman.smartmarker.mapbox.addMarker
import com.utsman.smartmarker.mapbox.toLatLngMapbox
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TOKEN = "pk.eyJ1IjoidXRzbWFuLWFwYSIsImEiOiJjazYzZWhldDMwOWpkM3BuMG05c2pwNWEzIn0.i-_9M10MbJycd3e6sXFP5w"
    private val monasLatLng = LatLng(-6.175392, 106.827153)

    private lateinit var locationWatcher: LocationWatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(this, TOKEN)
        setContentView(R.layout.activity_main)

        locationWatcher = LocationWatcher(this)

        map_view.getMapAsync { mapbox ->
            mapbox.setStyle(Style.MAPBOX_STREETS) { style ->

                mapbox.animateCamera(CameraUpdateFactory.newLatLngZoom(monasLatLng, 13.0))

                btn_get_location.setOnClickListener {
                    locationWatcher.getLocation(this, object : LocationListener {
                        override fun location(location: Location) {
                            val latLng = location.toLatLngMapbox()

                            val markerOption = MarkerOptions.Builder()
                                .setId("m-location")
                                .setIcon(R.drawable.mapbox_marker_icon_default)
                                .setPosition(latLng)
                                .build(this@MainActivity)

                            mapbox.addMarker(markerOption)

                            mapbox.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13.0))
                        }

                    })
                }
            }
        }
    }
}
