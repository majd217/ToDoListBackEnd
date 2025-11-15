package TDLBackend.tdl.RecipeIngredient;
import TDLBackend.tdl.Recipe.RecipeEntity;
import TDLBackend.tdl.ingredient.IngredientEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "recipe_ingredient")
public class RecipeIngredient {
	
	RecipeIngredient(){}
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public RecipeEntity getRecipeId() {
		return recipeId;
	}
	
	public void setRecipeId(RecipeEntity recipeId) {
		this.recipeId = recipeId;
	}
	
	public IngredientEntity getIngredient() {
		return ingredient;
	}
	
	public void setIngredient(IngredientEntity ingredient) {
		this.ingredient = ingredient;
	}
	
	@SequenceGenerator(
			name = "recipe_ingredient_id_seq",
			sequenceName = "recipe_ingredient_id_seq",
			allocationSize = 1
	)
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "recipe_ingredient_id_seq"
	)
	@Column(name= "id")
	private int id;
	
	@JoinColumn(name = "recipe_id")
	@ManyToOne
	@JsonIgnore
	private RecipeEntity recipeId;
	
	@JoinColumn(name = "ingredient_id")
	@ManyToOne
	private IngredientEntity ingredient;
	
	
	
}
