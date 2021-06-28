package tk.williamsouza.receitasdaximi.room

import androidx.room.Database
import androidx.room.RoomDatabase
import tk.williamsouza.receitasdaximi.models.Recipe

@Database(entities = [Recipe::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}