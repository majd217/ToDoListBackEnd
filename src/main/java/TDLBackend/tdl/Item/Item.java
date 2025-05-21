package TDLBackend.tdl.Item;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import TDLBackend.tdl.Store.Store;
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

    @Column(name= "label")
    private String label;

    @Column(name= "checked")
    private boolean checked;
    
    @Column(name= "storeid")
    @JsonIgnore
    private int storeid;

    Item(String label, boolean checked, int storeid)
    {
        this.label = label;
        this.checked = checked;
        this.storeid = storeid;
    }
    
    public Integer getId() {
        return id;
    }
    
    public String getLabel() {
        return label;
    }
    
    public boolean isChecked() {
        return checked;
    }
    
    @JsonIgnore
    public int getStoreid() {
        return this.storeid;
    }

    @JsonProperty
    public void setStoreid(int storeid) {
        this.storeid = storeid;
    }

}
