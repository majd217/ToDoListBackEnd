package TDLBackend.tdl.Meal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MealDate {
    @JsonProperty("year")
    private Integer year;

    @JsonProperty("calenderWeek")
    private Integer calenderWeek;

    MealDate(){}

    public MealDate(Integer year, Integer calenderWeek)
    {
        this.year = year;
        this.calenderWeek = calenderWeek;
    }

    public Integer getYear()
    {
        return this.year;
    }

    public Integer getCalenderWeek()
    {
        return this.calenderWeek;
    }
}
