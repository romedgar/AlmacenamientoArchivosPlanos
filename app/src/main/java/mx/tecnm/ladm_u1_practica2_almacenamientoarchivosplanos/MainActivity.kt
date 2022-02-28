package mx.tecnm.ladm_u1_practica2_almacenamientoarchivosplanos

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import mx.tecnm.ladm_u1_practica2_almacenamientoarchivosplanos.databinding.ActivityMainBinding
import java.io.File
import java.io.OutputStreamWriter

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fileName = "/data/data/mx.tecnm.ladm_u1_practica2_almacenamientoarchivosplanos/files/plates.txt"
        var file = File(fileName)
        var fileExists = file.exists()
        if(fileExists){
            AlertDialog.Builder(this)
                .setMessage("Using previous file...")
        } else {
            AlertDialog.Builder(this)
                .setMessage("Writing a new file...")
            //Agregamos platillos iniciales
            SaveInFile("Aguachile sea\nSushi asian\nTacos mexican")
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_read, R.id.nav_create, R.id.nav_update, R.id.nav_delete
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_settings->finish()
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun SaveInFile(plate:String){
        try {
            val file = OutputStreamWriter(this.openFileOutput("plates.txt", 0))

            file.write(plate)
            file.flush()
            file.close()

        } catch (e: Exception) {
            AlertDialog.Builder(this)
                .setTitle("Error Saving")
                .setMessage(e.message.toString())
                .show()
        }

    }

}