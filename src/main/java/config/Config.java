package config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public final class Config{
    private final String token;
    private final List<String> staffPrivilege;
    private final TicketSystem ticketSystem;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    private Config(@JsonProperty("token") String token,
                   @JsonProperty("staff privilege") List<String> staffPrivilege,
                   @JsonProperty("ticketSystem") TicketSystem ticketSystem){
        this.token = token;
        this.staffPrivilege = staffPrivilege;
        this.ticketSystem = ticketSystem;
    }

    public static Config load(InputStream path) throws IOException {
        return new ObjectMapper().registerModule(new JavaTimeModule())
                .readValue(path,Config.class);
    }

    public String getToken() {
        return token;
    }

    public List<String> getStaffPrivilege() {
        return staffPrivilege;
    }

    public TicketSystem getTicketSystem() {
        return ticketSystem;
    }
}
