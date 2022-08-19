package listeners;

import config.Config;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ticketsystem.Ticket;
import utility.HoldTickets;

import java.util.Objects;

public class OnButtonInteractionCreateTicket extends ListenerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(OnButtonInteractionCreateTicket.class);
    private final Config config;
    public OnButtonInteractionCreateTicket(Config config){
        this.config = config;
    }


    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event){
        Member member = event.getMember();
        String memberId = Objects.requireNonNull(member).getId();
        Guild guild = event.getGuild();
        Ticket ticket = null;
        if(event.getTextChannel().getId().equals(config.getTicketSystem().getStagingArea())){
            HoldTickets.rateLimit.putIfAbsent(memberId, 1);
            if(HoldTickets.rateLimit.get(memberId) > 3){
                event.reply("You are creating too many tickets!").setEphemeral(true).queue();
                logger.warn("User: " + member.getId() +" is trying to create too many tickets..");
                return;
            }

            if(Objects.requireNonNull(event.getButton().getId()).equals("ticket1")){
                ticket = new Ticket(guild,member,"#ticket " + member.getEffectiveName(),config.getTicketSystem().getCollabReqCat(),event,config);
            }
            else if(event.getButton().getId().equals("ticket2")){
                ticket = new Ticket(guild,member,"#ticket " + member.getEffectiveName(),config.getTicketSystem().getClaimRoleCat(),event,config);
            }
            else if(event.getButton().getId().equals("ticket3")){
                ticket = new Ticket(guild,member,"#ticket " + member.getEffectiveName(),config.getTicketSystem().getSupportRequestCat(),event,config);
            }
            Objects.requireNonNull(ticket).addChannel();
            HoldTickets.addTicket(ticket);
        }
    }
}
