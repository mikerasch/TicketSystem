package listeners;

import config.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ticketsystem.TicketActions;
import java.util.List;
import java.util.Objects;

public class OnCloseTicketListener extends ListenerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(OnCloseTicketListener.class);
    private final Config config;
    private final List<String> ADMIN_PRIVILEGE;
    public OnCloseTicketListener(Config config){
        this.config = config;
        ADMIN_PRIVILEGE = config.getStaffPrivilege();
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event){
        Member member = event.getMember();
        TextChannel textChannel = event.getTextChannel();
        EmbedBuilder eb = new EmbedBuilder();
        if(Objects.requireNonNull(event.getButton().getId()).equals("close")){
            eb.setTitle("Destination of Ticket?");
            boolean hasRole = hasRole(Objects.requireNonNull(member));
            if(hasRole){
                eb.setDescription("""
                    Delete: Permanently Deletes Ticket\s
                    Move Ticket: Sends Transcript and Closes Ticket""");
                event.getTextChannel().sendMessageEmbeds(eb.build()).setActionRow(
                        Button.primary("delete","Delete").withEmoji(Emoji.fromUnicode("\uD83D\uDDD1")),
                        Button.primary("move","Move Ticket").withEmoji(Emoji.fromUnicode("✈")),
                        Button.primary("disregard","Cancel")
                ).queue();
            }
            else{
                eb.setDescription("""
                    Move Ticket: Sends Transcript and Closes Ticket""");
                event.getTextChannel().sendMessageEmbeds(eb.build()).setActionRow(
                        Button.primary("move","Move Ticket").withEmoji(Emoji.fromUnicode("✈")),
                        Button.primary("disregard","Cancel")
                ).queue();
            }
            event.deferEdit().queue();
        }
        if(event.getButton().getId().equals("delete")){
            logger.warn("Deleting ticket: " + textChannel.getName());
            new TicketActions.Builder(textChannel).build()
                    .deleteChannel();
        }
        else if(event.getButton().getId().equals("move")){
            logger.info("Moving ticket: " + textChannel.getName() + "and sending transcript...");
            new TicketActions.Builder(textChannel).category(Objects.requireNonNull(event.getGuild()).getCategoryById(config.getTicketSystem().getClosedTicketCat()))
                    .transcriptId(config.getTicketSystem().getTranscriptTicketChan())
                    .guild(event.getGuild()).build().moveChannel().getAndSendTranscript();
            String channelName = textChannel.getName().substring(7);
            Objects.requireNonNull(event.getGuild().getTextChannelById(textChannel.getId())).getManager().setName("Closed " + channelName).queue();
            event.getTextChannel().sendMessage("Ticket closed by: " + Objects.requireNonNull(member).getUser().getAsMention()).queue();
        }
        else if(event.getButton().getId().equals("disregard")){
            new TicketActions.Builder(textChannel).build()
                    .disregard();
        }
    }

    public boolean hasRole(Member member){
        List<Role> roles = member.getRoles();
        if(roles.isEmpty()){
            return false;
        }
        for(Role role: roles){
            if(ADMIN_PRIVILEGE.contains(role.getId())){
                return true;
            }
        }
        return false;
    }
}
