package TDLBackend.tdl.Item;

import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.*;

@Entity(name = "item")
public class Item {

    public Item(){}

    @SequenceGenerator(
            name = "item_id_seq",
            sequenceName = "item_id_seq",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "item_id_seq"
    )
    @Column(name= "id")
    private int id;

//    @Column(name="itemID")
//    private int itemID;

    @Column(name= "label")
    private String label;

    @Column(name= "checked")
    private boolean checked;

Item(String label, boolean checked){
        this.label = label;
        this.checked =checked;
    }
    
    public int getId() {
        return id;
    }
    
    public String getLabel() {
        return label;
    }
    
    public boolean isChecked() {
        return checked;
    }
}
