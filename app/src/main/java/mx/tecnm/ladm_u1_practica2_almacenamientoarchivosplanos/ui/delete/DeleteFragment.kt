package mx.tecnm.ladm_u1_practica2_almacenamientoarchivosplanos.ui.delete

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import mx.tecnm.ladm_u1_practica2_almacenamientoarchivosplanos.CustomAdapter
import mx.tecnm.ladm_u1_practica2_almacenamientoarchivosplanos.databinding.FragmentDeleteBinding
import mx.tecnm.ladm_u1_practica2_almacenamientoarchivosplanos.ui.read.ReadViewModel
import java.io.*
import java.lang.Exception

class DeleteFragment : Fragment() {

    private var _binding: FragmentDeleteBinding? = null

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
        val slideshowViewModel =
            ViewModelProvider(this).get(ReadViewModel::class.java)

        _binding = FragmentDeleteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //Consiguramos el elemento delete
        val recycleView = binding.recyvlerView
        adapter = CustomAdapter(plates,object: CustomAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Delete Plate?")
                    .setMessage("Are you sure you want to delete '${plates[position]}'")
                    .setPositiveButton("Yes") { d, i ->
                        deletePlate(position)
                        d.dismiss()
                    }
                    .setNegativeButton("Close",{d,i->d.dismiss()})
                    .show()
            }

        })

        readInFile()

        recycleView.layoutManager = LinearLayoutManager(requireContext())
        recycleView.adapter = adapter

        return root
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
                .setTitle("Error deleting")
                .setMessage(e.message.toString())
                .show()
        }
    }

    private fun deletePlate(i:Int) {
        var s = ""
        plates.removeAt(i)
        plates.forEach{
            s+=it+"\n"
        }

        val fileName = "/data/data/mx.tecnm.ladm_u1_practica2_alamacenamientoarchivosplanos/files/plates.txt"
        var file = File(fileName)
        file.delete()

        val newFile = OutputStreamWriter(requireContext().openFileOutput("plates.txt", 0))
        newFile.write(s)
        newFile.flush()
        newFile.close()
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}