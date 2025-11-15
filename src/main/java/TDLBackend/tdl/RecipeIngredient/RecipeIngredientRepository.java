//package TDLBackend.tdl.RecipeIngredient;
//
//import TDLBackend.tdl.Recipe.RecipeEntity;
//import TDLBackend.tdl.ingredient.IngredientEntity;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient,Integer> {
//
//	@Query("insert into recipe_ingredient (recipe_id,ingredient_id,quantity) VALUES (:recipe_id,:ingredient_id,:quantity)")
//	default void insertRecipeIngredient(@Param("recipe_id") int recipeId, @Param("ingredient_id") int ingredientId) {}
//}
//
////fetch recipe in frontend
////what happens?
//// fetch all ingredients with recipe id,
////fetch all instructions with recipe id
//// fetch recipe name with recipe id