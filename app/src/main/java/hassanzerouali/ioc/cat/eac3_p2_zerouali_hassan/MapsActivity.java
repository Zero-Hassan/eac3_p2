package hassanzerouali.ioc.cat.eac3_p2_zerouali_hassan;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private boolean mLocationPermissionGranted=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // las cordinadas para centrar el mapa al inicio
        LatLng eixmple = new LatLng(41.38, 2.161);

        // las cordinadas de la feina
        LatLng feina = new LatLng(41.37,2.15);

        // añadir el marker de la feina
        mMap.addMarker(new MarkerOptions().position(feina).title("Feina"));

        // centrar el mapa al eixmple con un zoom de 13
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eixmple,13f));


        // definir el tipo del la capa a terrain
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        // habilitar la localizacion del usuario
        enableMyLocation(mMap);

        // añadir los controles del zoom al mapa
        mMap.getUiSettings().setZoomControlsEnabled(true);

    }

    private void enableMyLocation(GoogleMap map) {

        // si hay permiso se habilita sino ha de pedir el permiso al usuario

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
            this.mLocationPermissionGranted=true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }
    private void enableMyLocation() {

        // si hay permiso se habilita sino ha de pedir el permiso al usuario

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // asegurar que el permiso de la localizacion est concidido
        // entonce hablitar la geolocalizacion del usuario
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocation();
                    break;
                }
        }
    }

    public void myLocation(View v){


            if (mLocationPermissionGranted) {
                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                @SuppressLint("MissingPermission") Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                String text = "La meva localització és "+location.getLongitude()+" + "+location.getLatitude()+".";
                Toast.makeText(this,text, Toast.LENGTH_LONG).show();
        }

    }

}
