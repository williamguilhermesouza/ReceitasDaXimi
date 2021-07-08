package tk.williamsouza.receitasdaximi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tk.williamsouza.receitasdaximi.models.Recipe
import tk.williamsouza.receitasdaximi.room.AppDatabase

class MainFragment : Fragment() {
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private var data: List<Recipe> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        // Inflate the layout for this fragment
        val recipeButton = view.findViewById<FloatingActionButton>(R.id.addRecipeButton)

        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        recipeButton?.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_newRecipeFragment)
        }

        val recipeSearch = view.findViewById<AutoCompleteTextView>(R.id.recipeSearch)

        recipeSearch.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val bundle = bundleOf("search" to recipeSearch.text.toString())
                requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

                view.findNavController().navigate(R.id.action_mainFragment_to_recipesListFragment, bundle)
                handled = true
            }

            handled
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addDataSet()
    }


    private fun setData(_data: List<Recipe>) {
        data = _data
    }

    private fun addDataSet() {
        val db = Room.databaseBuilder(
            requireContext().applicationContext,
            AppDatabase::class.java, "recipe"
        ).build()

        CoroutineScope(Dispatchers.IO).launch {
            val data = db.recipeDao().getAll()
            withContext(Dispatchers.Main) {
                setData(data)
                val autoTextView = view?.findViewById<AutoCompleteTextView>(R.id.recipeSearch)
                val recipeTitles = arrayListOf<String>()

                for (recipe in data) {
                    recipeTitles.add(recipe.title as String)
                }

                arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.select_dialog_item, recipeTitles)

                autoTextView!!.threshold = 1

                autoTextView.setAdapter(arrayAdapter)
                arrayAdapter.notifyDataSetChanged()
            }
        }
    }



}