package iti.android.wheatherappjetpackcompose.presentationLayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import iti.android.wheatherappjetpackcompose.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        val navController: NavController? = findNavController()

        navController?.let {
            NavigationUI.setupWithNavController(bottomNavigationView, it)

        }

    }

    private fun findNavController(): NavController? {
        val navHostFragment =
            (this as? MainActivity)?.supportFragmentManager?.findFragmentById(R.id.mainNavHostFragment) as? NavHostFragment
        return navHostFragment?.navController
    }
}

