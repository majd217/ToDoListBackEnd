package TDLBackend.tdl.Recipe;

import TDLBackend.tdl.RecipeIngredient.RecipeIngredient;
import TDLBackend.tdl.ingredient.IngredientEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import TDLBackend.tdl.Instruction.Instruction;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "recipe")
public class RecipeEntity {
	@SequenceGenerator(
			name = "recipe_id_seq",
			sequenceName = "recipe_id_seq",
			allocationSize = 1
	)
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "recipe_id_seq"
	)
	@Column(name = "id")
	private Integer id;
	@Column(name = "name")
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recipeId", fetch = FetchType.EAGER)
	@OrderBy("stepNumber ASC")
	private List<Instruction> instructions;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recipeId", fetch = FetchType.EAGER)
	private List<RecipeIngredient> ingredients;
	RecipeEntity(){}
	public RecipeEntity(String name, List<RecipeIngredient> ingredients, List<Instruction> instructions){
		this.name = name;
		this.ingredients = ingredients;
		this.instructions = instructions;
	}
	
	public RecipeEntity(int id, String name, List<RecipeIngredient> ingredients, List<Instruction> instructions){
		this.id = id;
		this.name = name;
		this.ingredients = ingredients;
		this.instructions = instructions;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Instruction> getInstructions() {
		return instructions;
	}
	
	public void setInstructions(List<Instruction> instructions) {
		this.instructions = instructions;
	}
	
	public List<RecipeIngredient> getIngredients() {
		return ingredients;
	}
	
	public void setIngredients(List<RecipeIngredient> ingredients) {
		this.ingredients = ingredients;
	}
}
