package config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("ticketSystem")
public final class TicketSystem {
    private final String stagingArea;
    private final String claimRoleCat;
    private final String collabReqCat;
    private final String supportRequestCat;
    private final String closedTicketCat;
    private final String transcriptTicketChan;
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    private TicketSystem(@JsonProperty("stagingArea") String stagingArea,
                         @JsonProperty("claimRoleCat")String claimRoleCat,
                         @JsonProperty("collabReqCat")String collabReqCat,
                         @JsonProperty("supportRequestCat")String supportRequestCat,
                         @JsonProperty("closedTicketCat")String closedTicketCat,
                         @JsonProperty("transcriptTicketChan")String transcriptTicketChan){
        this.stagingArea = stagingArea;
        this.claimRoleCat = claimRoleCat;
        this.collabReqCat = collabReqCat;
        this.supportRequestCat = supportRequestCat;
        this.closedTicketCat = closedTicketCat;
        this.transcriptTicketChan = transcriptTicketChan;
    }

    public String getStagingArea() {
        return stagingArea;
    }

    public String getClaimRoleCat() {
        return claimRoleCat;
    }

    public String getCollabReqCat() {
        return collabReqCat;
    }

    public String getTranscriptTicketChan() {
        return transcriptTicketChan;
    }

    public String getClosedTicketCat() {
        return closedTicketCat;
    }

    public String getSupportRequestCat() {
        return supportRequestCat;
    }
}
