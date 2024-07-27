package com.example.ecommercetask

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.ecommercetask.data.remote.firestore.Product
import com.example.ecommercetask.databinding.ActivityMainBinding
import com.example.ecommercetask.domain.use_cases.LogoutUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    @Inject
    lateinit var logoutUseCase: LogoutUseCase

    lateinit var binding: ActivityMainBinding


    @Inject
    lateinit var auth: FirebaseAuth

    val mainActivityViewModel by viewModels<MainActivityViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost =
            supportFragmentManager.findFragmentById(R.id.main_fragment_container_view) as NavHostFragment
        navController = navHost.navController

        navController.addOnDestinationChangedListener(object :
            NavController.OnDestinationChangedListener {
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {

            }
        })



        binding.bottomNavMenu.setupWithNavController(navController)
    }

        override fun onStart() {
            super.onStart()
            auth.addAuthStateListener {
                Log.d("AUTH_STATE", "I AM WORK")
                val currentUser = auth.currentUser
                if (currentUser != null ) {
                    mainActivityViewModel.setCurrentUserValue(currentUser)
                } else {
                    mainActivityViewModel.setCurrentUserValue(null)
                }
            }

            mainActivityViewModel.currentUser.observe(this) {
                if (it != null) {
                    navController.setGraph(R.navigation.main_nav_graph)
                } else {
                    navController.setGraph(R.navigation.auth_nav_graph)
                }
            }

        }
    }
