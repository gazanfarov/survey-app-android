package com.surveyapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.surveyapp.R
import com.surveyapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val binding: ActivityMainBinding by viewBinding(
        ActivityMainBinding::bind,
        R.id.mainRoot
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpNavigationComponent()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setUpNavigationComponent() {
        navController = (supportFragmentManager.findFragmentByTag(
            resources.getString(R.string.mainNavigationFragment)
        ) as NavHostFragment).navController
        binding.mainToolbar.changeToolbarFont()
        appBarConfiguration = AppBarConfiguration.Builder(setOf(R.id.intro))
            .setFallbackOnNavigateUpListener {
                false
            }.build()
        setSupportActionBar(binding.mainToolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    fun Toolbar.changeToolbarFont() {
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            if (view is TextView && view.text == this.title) {
                view.typeface = ResourcesCompat.getFont(context, R.font.roboto_bold_font)
                break
            }
        }
    }
}