package TDLBackend.tdl.Instruction;

import TDLBackend.tdl.Recipe.RecipeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "instruction")
public class Instruction {
	Instruction(){}
	@SequenceGenerator(
			name = "instruction_id_seq",
			sequenceName = "instruction_id_seq",
			allocationSize = 1
	)
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "instruction_id_seq"
	)
	@Column(name= "id")
	private int id;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getStepNumber() {
		return stepNumber;
	}
	
	public void setStepNumber(int stepNumber) {
		this.stepNumber = stepNumber;
	}
	
	public RecipeEntity getRecipeId() {
		return recipeId;
	}
	
	public void setRecipeId(RecipeEntity recipeId) {
		this.recipeId = recipeId;
	}
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "step_number")
	private int stepNumber;
	
	@JoinColumn(name = "recipe_id")
	@ManyToOne
	@JsonIgnore
	private RecipeEntity recipeId;
}
