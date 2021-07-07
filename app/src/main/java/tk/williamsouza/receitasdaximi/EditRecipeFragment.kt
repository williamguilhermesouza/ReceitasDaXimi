package tk.williamsouza.receitasdaximi

import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tk.williamsouza.receitasdaximi.models.Recipe
import tk.williamsouza.receitasdaximi.room.AppDatabase
import java.time.LocalDateTime

class EditRecipeFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_recipe, container, false)

        val recipe = arguments?.get("recipe") as Recipe

        val recipeTitle = view.findViewById<EditText>(R.id.titleInput)
        val recipeIngredients = view.findViewById<EditText>(R.id.ingredientsInput)
        val recipePreparation = view.findViewById<EditText>(R.id.preparationInput)

        recipeTitle.text = SpannableStringBuilder(recipe.title)
        recipeIngredients.text = SpannableStringBuilder(recipe.ingredients)
        recipePreparation.text = SpannableStringBuilder(recipe.instructions)

        val editButton = view.findViewById<Button>(R.id.editButton)
        val deleteButton = view.findViewById<Button>(R.id.deleteButton)
        val saveButton = view.findViewById<Button>(R.id.saveButton)

        val db = Room.databaseBuilder(
            requireContext().applicationContext,
            AppDatabase::class.java, "recipe"
        ).build()

        editButton.setOnClickListener {
            recipeTitle.isEnabled = true
            recipeIngredients.isEnabled = true
            recipePreparation.isEnabled = true
        }

        deleteButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.recipeDao().delete(recipe.id)
                withContext(Dispatchers.Main) {
                    view.findNavController().navigate(R.id.action_editRecipeFragment_to_mainFragment)
                }
            }
        }

        saveButton.setOnClickListener {
            if (recipeTitle.isEnabled) {
                CoroutineScope(Dispatchers.IO).launch {
                    db.recipeDao().update(
                        recipe.id,
                        recipeTitle.text.toString(),
                        recipeIngredients.text.toString(),
                        recipePreparation.text.toString(),
                        LocalDateTime.now().toString(),
                    )
                    withContext(Dispatchers.Main) {
                        view.findNavController().navigate(R.id.action_editRecipeFragment_to_recipesListFragment2)
                    }
                }
            }
        }




        return view
    }
}