package TDLBackend.tdl.Store;

import java.util.List;

import TDLBackend.tdl.Item.Item;
import jakarta.persistence.*;

@Entity(name = "store")
public class Store {
	
	public Store(){}
	
	@SequenceGenerator(
			name = "store_id_seq",
			sequenceName = "store_id_seq",
			allocationSize = 1
	)
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "store_id_seq"
	)
	@Column(name= "id")
	private int id;
	
	@Column(name= "name")
	private String name;
	
	@Column(name= "type")
	private StoreType type;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "storeid", fetch = FetchType.EAGER)
	private List<Item> items;

	public Store(int id, String name, StoreType type, List<Item> items) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.items = items;
	}
	
	
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
	
	public int getType() {
		return type.ordinal();
	}
	
	public void setType(StoreType type) {
		this.type = type;
	}

	public List<Item> getItems() {
		return this.items;
	}
	
	public void setType(List<Item> items) {
		this.items = items;
	}
}
