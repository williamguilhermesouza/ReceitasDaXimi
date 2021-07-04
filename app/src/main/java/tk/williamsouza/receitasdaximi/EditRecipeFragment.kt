package tk.williamsouza.receitasdaximi

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import tk.williamsouza.receitasdaximi.models.Recipe

class EditRecipeFragment : Fragment() {

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

        editButton.setOnClickListener {
            recipeTitle.isEnabled = true
            recipeIngredients.isEnabled = true
            recipePreparation.isEnabled = true
        }


        return view
    }
}