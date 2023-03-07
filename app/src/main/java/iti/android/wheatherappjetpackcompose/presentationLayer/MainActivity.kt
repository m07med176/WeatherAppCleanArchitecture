package iti.android.wheatherappjetpackcompose.presentationLayer

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import iti.android.wheatherappjetpackcompose.R
import iti.android.wheatherappjetpackcompose.utils.findNavController


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        val navController: NavController? = findNavController(this)

        navController?.let {
            NavigationUI.setupWithNavController(bottomNavigationView, it)

        }

        navController?.addOnDestinationChangedListener { _, navDestination, _ ->
            if (
                navDestination.id == R.id.splashFragment ||
                navDestination.id == R.id.mapFragment ||
                navDestination.id == R.id.details_menu
            ) {
                bottomNavigationView.visibility = View.GONE
            } else {
                bottomNavigationView.visibility = View.VISIBLE
            }
        }

    }


}

