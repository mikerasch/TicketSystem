package config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("ticketSystem")
public final class TicketSystem {
    private final String stagingArea;
    private final String genSupportCat;
    private final String collabReqCat;
    private final String reportReqCat;
    private final String closedTicketCat;
    private final String transcriptTicketChan;
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    private TicketSystem(@JsonProperty("stagingArea") String stagingArea,
                         @JsonProperty("genSupportCat")String genSupportCat,
                         @JsonProperty("collabReqCat")String collabReqCat,
                         @JsonProperty("reportReqCat")String reportReqCat,
                         @JsonProperty("closedTicketCat")String closedTicketCat,
                         @JsonProperty("transcriptTicketChan")String transcriptTicketChan){
        this.stagingArea = stagingArea;
        this.genSupportCat = genSupportCat;
        this.collabReqCat = collabReqCat;
        this.reportReqCat = reportReqCat;
        this.closedTicketCat = closedTicketCat;
        this.transcriptTicketChan = transcriptTicketChan;
    }

    public String getStagingArea() {
        return stagingArea;
    }

    public String getGenSupportCat() {
        return genSupportCat;
    }

    public String getCollabReqCat() {
        return collabReqCat;
    }

    public String getReportReqCat() {
        return reportReqCat;
    }

    public String getClosedTicketCat() {
        return closedTicketCat;
    }

    public String getTranscriptTicketChan() {
        return transcriptTicketChan;
    }
}
