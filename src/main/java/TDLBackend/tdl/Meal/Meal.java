package TDLBackend.tdl.Meal;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import jakarta.persistence.*;


@Entity
@Table(name="meal")
public class Meal {

    @SequenceGenerator(
            name = "meal_id_seq",
            sequenceName = "meal_id_seq",
            allocationSize = 1
    )

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "meal_id_seq"
    )

    @Column(name="id")
    private int id;

    @Column(name="label")
    private String label;

    @Column(name="mealdate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate mealDate;
    
    Meal(){}
    
    public Meal(String label, LocalDate mealDate) {
        this.label = label;
        this.mealDate = mealDate;
    }

    public Integer getId() {
        return this.id;
    }

    public String getLabel()
    {
        return this.label;
    }

    public LocalDate getMealDate()
    {
        return this.mealDate;
    }
}
