package com.github.syafiqq.fitnesscounter.role.student.controller

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.github.syafiqq.fitnesscounter.role.student.R
import com.github.syafiqq.fitnesscounter.role.student.controller.auth.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import timber.log.Timber

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SplashScreen: AppCompatActivity()
{
    private val hideHandler = Handler()
    private val hideOperation = Runnable { hide() }
    private var mContentView: View? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        Timber.d("onCreate")

        super.onCreate(savedInstanceState)

        super.setContentView(R.layout.activity_splash_screen)

        this.mContentView = findViewById(R.id.fullscreen_content)
    }

    override fun onPostCreate(savedInstanceState: Bundle?)
    {
        Timber.d("onPostCreate")

        super.onPostCreate(savedInstanceState)

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        this.delayedHide(50)
    }

    override fun onPostResume()
    {
        Timber.d("onPostResume")

        super.onPostResume()
        FirebaseAuth.getInstance().signOut()

        this.dispatchOperation(FirebaseAuth.getInstance().currentUser)
    }

    private fun dispatchOperation(user: FirebaseUser?)
    {
        Handler(mainLooper).postDelayed({
            if (user != null)
                Timber.d("To Home")
            else
                this.redirectToLoginPage()
        }, 1000)
    }

    private fun redirectToLoginPage()
    {
        Timber.d("redirectToLoginPage")

        val intent = Intent(this, LoginActivity::class.java)
        super.startActivity(intent)
        super.finish()
    }

    override fun onDestroy()
    {
        Timber.d("onDestroy")

        super.onDestroy()
    }

    private fun hide()
    {
        Timber.d("hide")

        // Hide UI first
        val actionBar = supportActionBar
        actionBar?.hide()

        // Schedule a runnable to remove the status and navigation bar after a delay
        //this.hideHandler.postDelayed(this.hideCompletelyOperation, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [300] milliseconds, canceling any
     * previously scheduled calls.
     */
    private fun delayedHide(delayMillis: Int)
    {
        Timber.d("hideCompletely")

        this.hideHandler.removeCallbacks(this.hideOperation)
        this.hideHandler.postDelayed(this.hideOperation, delayMillis.toLong())
    }
}