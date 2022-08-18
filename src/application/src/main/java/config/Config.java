package config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Config{
    private final String token;
    private final String staffPrivilege;
    private final TicketSystem ticketSystem;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    private Config(@JsonProperty("token") String token,
                   @JsonProperty("staff privilege") String staffPrivilege,
                   @JsonProperty("ticketSystem") TicketSystem ticketSystem){
        this.token = token;
        this.staffPrivilege = staffPrivilege;
        this.ticketSystem = ticketSystem;
    }
}
