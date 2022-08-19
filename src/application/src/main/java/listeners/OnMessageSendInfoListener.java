package listeners;

import config.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

public class OnMessageSendInfoListener extends ListenerAdapter {
    private final String STAGING_AREA_NAME;
    public OnMessageSendInfoListener(Config config){
        this.STAGING_AREA_NAME = config.getTicketSystem().getStagingArea();
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        TextChannel textChannel = event.getTextChannel();
        Message message = event.getMessage();
        EmbedBuilder embedBuilder;
        if (message.getContentRaw().equals("!ticket") && textChannel.getId().equals(STAGING_AREA_NAME)){
            embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Collaboration Request");
            embedBuilder.addField("Thank you for your interest in collaborating with us!",
                    "Please provide all your social media's (Twitter, Discord " +
                            "and describe your desired way of collaboration!", false);
            embedBuilder.setFooter("To create a ticket react below!");

            textChannel.sendMessageEmbeds(embedBuilder.build()).setActionRow(
                    Button.secondary("ticket1", "Create Ticket").withEmoji(Emoji.fromUnicode("✉"))
            ).queue();


            embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Claim Roles");
            embedBuilder.setDescription("If you have completed all the steps for Mint List, or are " +
                    "coming from a collaboration, react below!");
            embedBuilder.setFooter("To create a ticket react below!");

            textChannel.sendMessageEmbeds(embedBuilder.build()).setActionRow(
                    Button.secondary("ticket2", "Create Ticket").withEmoji(Emoji.fromUnicode("✉"))
            ).queue();


            embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Support");
            embedBuilder.setDescription("Before creating a ticket, please make sure you have already" +
                    " read carefully the channels in the server!");
            embedBuilder.setFooter("To create a ticket react below!");

            textChannel.sendMessageEmbeds(embedBuilder.build()).setActionRow(
                    Button.secondary("ticket3", "Create Ticket").withEmoji(Emoji.fromUnicode("✉"))
            ).queue();
        }
    }
}
