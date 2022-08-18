package config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

@JsonRootName("ticketSystem")
public final class TicketSystem {
    private final String stagingArea;
    private final List<String> categories;
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    private TicketSystem(@JsonProperty("stagingArea") String stagingArea,
                         @JsonProperty("categories") List<String> categories){
        this.stagingArea = stagingArea;
        this.categories = categories;
    }

    public String getStagingArea() {
        return stagingArea;
    }

    public List<String> getCategories() {
        return categories;
    }
}
