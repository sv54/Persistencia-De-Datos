package com.example.pantallapreferences

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class DrawerUtil {
    fun setupDrawer(activity: AppCompatActivity) {
        val drawerLayout: DrawerLayout = activity.findViewById(R.id.drawerLayout)
        val navigationView: NavigationView = activity.findViewById(R.id.navigationView)


        val toggle = ActionBarDrawerToggle(
            activity,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_item_1 -> {
                        val intent = Intent(activity, MainActivity::class.java)
                        activity.startActivity(intent)
                        activity.finish()
                        drawerLayout.closeDrawer(GravityCompat.START)

                    true
                }
                R.id.menu_item_2 -> {
                    val intent = Intent(activity, Settings::class.java)
                    activity.startActivity(intent)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }
        }
    }

    fun onOptionsItemSelected(item: MenuItem, drawerLayout: DrawerLayout): Boolean {
        if (item.itemId == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
            return true
        }
        return false
    }

    fun isActivityInStack(context: Context, cls: Class<*>): Boolean {
        val intent = Intent(context, cls)
        val activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)

        return activities.isNotEmpty()
    }
}