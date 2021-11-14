package es.christianbegines.wonkamanagement.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import dagger.hilt.android.AndroidEntryPoint
import es.christianbegines.wonkamanagement.R


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setSupportActionBar(findViewById(R.id.main_toolbar))
    }

   //// Menu icons are inflated just as they were with actionbar
   //override fun onCreateOptionsMenu(menu: Menu?): Boolean {
   //    // Inflate the menu; this adds items to the action bar if it is present.
   //    menuInflater.inflate(R.menu.menu, menu)
   //    return true
   //}

   //override fun onSupportNavigateUp(): Boolean {
   //    onBackPressed()
   //    return true
   //}
}