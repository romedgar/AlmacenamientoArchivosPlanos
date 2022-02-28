package mx.tecnm.ladm_u1_practica2_almacenamientoarchivosplanos.ui.create

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mx.tecnm.ladm_u1_practica2_almacenamientoarchivosplanos.databinding.FragmentCreateBinding
import mx.tecnm.ladm_u1_practica2_almacenamientoarchivosplanos.ui.read.ReadViewModel
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Exception


class CreateFragment : Fragment() {

    private var _binding: FragmentCreateBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    //Creamos un arreglo de platillos
    val plates = arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val ReadViewModel =
            ViewModelProvider(this).get(ReadViewModel::class.java)

        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //Guardamos al momento de crear el nuevo platillo
        binding.btnAdd.setOnClickListener {
            saveInFile()
        }

        return root
    }
    private fun saveInFile() {
        try {
            plates.clear()
            readInFile()
            var s = ""
            plates.forEach {
                s += it +"\n"
            }
            val file = OutputStreamWriter(requireActivity().openFileOutput("plates.txt",0))

            s += binding.plate.text.toString().trim()+" " +
                    binding.category.text.toString().trim()+"\n"

            file.write(s)
            file.flush()
            file.close()

            binding.plate.setText("")
            binding.category.setText("")

            AlertDialog.Builder(requireContext())
                .setMessage("Saved Correctly")
                .setPositiveButton("Ok",{d,i-> d.dismiss()})
                .show()
        }catch (e:Exception){
            AlertDialog.Builder(requireContext())
                .setTitle("Error on Saving")
                .setMessage(e.message.toString())
                .show()
        }
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}