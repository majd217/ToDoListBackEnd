package TDLBackend.tdl.ingredient;

import jakarta.persistence.*;

@Entity
@Table(name = "ingredient")
public class IngredientEntity {
	
	IngredientEntity(){}
	@SequenceGenerator(
			name = "ingredient_id_seq",
			sequenceName = "ingredient_id_seq",
			allocationSize = 1
	)
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "ingredient_id_seq"
	)
	@Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
