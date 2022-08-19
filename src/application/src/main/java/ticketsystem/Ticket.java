package ticketsystem;

import config.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;
import utility.HoldTickets;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class Ticket {
    private final Guild guild;
    private final Member member;
    private final String ticketName;
    private final String catId;
    private final Config config;
    private final ArrayList<Long> myPermissions;
    private final ButtonInteractionEvent event;
    public Ticket(Guild guild, Member member, String ticketName, String catId, ButtonInteractionEvent event, Config config){
        this.guild = guild;
        this.member = member;
        this.ticketName = ticketName;
        this.catId = catId;
        this.event = event;
        this.config = config;
        myPermissions = new ArrayList<>();

        List<String> roleList = config.getStaffPrivilege();
        for(String role:roleList){
            myPermissions.add(Long.parseLong(role));
        }
    }

    public void addChannel(){
        EnumSet<Permission> permissions = EnumSet.of(Permission.VIEW_CHANNEL);
        Category category = guild.getCategoryById(catId);
        ChannelAction createTicket = guild.createTextChannel(ticketName,category);
        createTicket.addPermissionOverride(member,permissions,null);
        createTicket.addPermissionOverride(guild.getPublicRole(),null,permissions);
        for(long i: myPermissions){
            createTicket.addPermissionOverride(guild.getRoleById(i),permissions,null);
        }
        HoldTickets.rateLimit.merge(member.getId(),1,Integer::sum);
        createTicket.queue(channel -> {
            int first = channel.toString().indexOf("(");
            int last = channel.toString().lastIndexOf(")");
            String newChannelId = channel.toString().substring(first+1,last);
            event.reply("Ticket created:" +"<#" + newChannelId + ">").setEphemeral(true).queue();
            informDeleteChannel(newChannelId);
        });
    }

    private void informDeleteChannel(String newChannelId) {
        EmbedBuilder eb = new EmbedBuilder();
        TextChannel newChannel = guild.getTextChannelById(newChannelId);
        eb.setDescription("Support will be with you shortly. To close this ticket react below!");
        newChannel.sendMessageEmbeds(eb.build()).setActionRow(
                Button.secondary("close", "Close").withEmoji(Emoji.fromUnicode("\uD83D\uDD12"))
        ).queue();
        newChannel.sendMessage("<@" + member.getId() + ">").queue();
    }
}
