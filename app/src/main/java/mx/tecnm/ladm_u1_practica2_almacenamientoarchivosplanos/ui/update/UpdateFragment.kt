package mx.tecnm.ladm_u1_practica2_almacenamientoarchivosplanos.ui.update

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import mx.tecnm.ladm_u1_practica2_almacenamientoarchivosplanos.CustomAdapter
import mx.tecnm.ladm_u1_practica2_almacenamientoarchivosplanos.R
import mx.tecnm.ladm_u1_practica2_almacenamientoarchivosplanos.databinding.FragmentUpdateBinding
import mx.tecnm.ladm_u1_practica2_almacenamientoarchivosplanos.ui.read.ReadViewModel
import java.io.*
import java.lang.Exception

class UpdateFragment : Fragment() {

    private var _binding: FragmentUpdateBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val plates = arrayListOf<String>()
    private lateinit var adapter: CustomAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(ReadViewModel::class.java)

        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //Trigger para saber el platillo a actualizar
        val recyclerViewer = binding.recyclerView
        adapter = CustomAdapter(plates,object: CustomAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                updateInFile(plates[position], position)
                updateInFile(plates[position], position)
            }
        })

        readInFile()

        recyclerViewer.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewer.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun readInFile(){
        try {
            val file = InputStreamReader(requireActivity().openFileInput("plates.txt"))

            var contentlist = file.readLines()
            contentlist.forEach {
                plates.add(it)
            }
            file.close()

        }catch (e:Exception){
            AlertDialog.Builder(requireContext())
                .setTitle("Error Reading File")
                .setMessage(e.message.toString())
                .show()
        }
    }

    fun updateInFile(plate:String, i:Int) {

        val builder = AlertDialog.Builder(requireContext())
            .create()
        builder.setTitle("Update Dish")
        val view = layoutInflater.inflate(R.layout.add_plate,null)
        val  button = view.findViewById<Button>(R.id.add_dish)
        button.setText("Update")

        var plate_name = view.findViewById<EditText>(R.id.dish_name)
        var catego = view.findViewById<EditText>(R.id.dish_category)

        var allplates = plate.split(" ")
        plate_name.setText(allplates[0])

        catego.setText(allplates[1])

        builder.setView(view)
        button.setOnClickListener {
            var s = ""
            plates.forEach {
                s+=it+"\n"
            }
            plates[i] = plate_name.text.toString().trim() +" "+ catego.text.toString().trim()
            adapter.notifyDataSetChanged()
            SaveInFile()
            Toast.makeText(requireContext(),"Plate Updated", Toast.LENGTH_SHORT).show()
            builder.dismiss()
        }

        builder.show()
    }

    private fun SaveInFile(){
        try {
            var s = ""
            plates.forEach {
                s+=it+"\n"
            }

            val fileName = "/data/data/mx.tecnm.ladm_u1_practica2_almacenamientoarchivosplanos/files/plates.txt"
            var file = File(fileName)
            file.delete()

            val nFile = OutputStreamWriter(requireContext().openFileOutput("plates.txt", 0))
            nFile.write(s)
            nFile.flush()
            nFile.close()

        } catch (e: Exception) {
            AlertDialog.Builder(requireContext())
                .setTitle("Error Saving")
                .setMessage(e.message.toString())
                .show()
        }

    }
}