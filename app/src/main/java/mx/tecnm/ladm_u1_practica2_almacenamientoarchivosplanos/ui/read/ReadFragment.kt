package mx.tecnm.ladm_u1_practica2_almacenamientoarchivosplanos.ui.read

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import mx.tecnm.ladm_u1_practica2_almacenamientoarchivosplanos.CustomAdapter
import mx.tecnm.ladm_u1_practica2_almacenamientoarchivosplanos.databinding.FragmentReadBinding
import java.io.InputStreamReader
import java.lang.Exception

class ReadFragment : Fragment() {

    private var _binding: FragmentReadBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val plates = arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(ReadViewModel::class.java)

        _binding = FragmentReadBinding.inflate(inflater, container, false)
        val root: View = binding.root

        ReadInFile()
        val recyvleViewer = binding.recyvlerView
        val adapter = CustomAdapter(plates, object : CustomAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
            }

        })
        recyvleViewer.layoutManager = LinearLayoutManager(requireContext())
        recyvleViewer.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun ReadInFile() {
        try {
            val file = InputStreamReader(requireActivity().openFileInput("plates.txt"))
            plates.removeAll(plates)
            var contentlist = file.readLines()
            contentlist.forEach {
                plates.add(it)
            }

            file.close()

        } catch (e: Exception) {
            AlertDialog.Builder(requireContext())
                .setTitle("Error reading File")
                .setMessage(e.message.toString())
                .show()
        }
    }
}