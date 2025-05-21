package TDLBackend.tdl.Recipe;

import jakarta.persistence.*;

import java.util.ArrayList;

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
	@Column
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "instructions")
	private ArrayList<String> instructions;
	
	@Column(name = "ingredients")
	private ArrayList<String> ingredients;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public ArrayList<String> getInstructions() {
		return instructions;
	}
	
	public void setInstructions(ArrayList<String> instructions) {
		this.instructions = instructions;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<String> getIngredients() {
		return ingredients;
	}
	
	public void setIngredients(ArrayList<String> ingredients) {
		this.ingredients = ingredients;
	}
	
	
}
