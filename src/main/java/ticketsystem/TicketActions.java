package ticketsystem;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.utils.TimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TicketActions {
    private static final Logger logger = LoggerFactory.getLogger(TicketActions.class);
    private final TextChannel id;
    private final Category category;
    private final String TRANSCRIPT_TICKET_ID;
    private final Guild guild;

    private TicketActions(Builder builder){
        this.id = builder.id;
        this.category = builder.category;
        this.guild = builder.guild;
        this.TRANSCRIPT_TICKET_ID = builder.transcriptId;
    }

    public void deleteChannel(){
        id.delete().reason("Deleting ticket").queue();
    }

    public TicketActions moveChannel(){
        String message;
        message = id.getLatestMessageId();
        id.deleteMessageById(message).queue();
        id.getManager().setParent(category).submit();
        return this;
    }

    public void getAndSendTranscript(){
        List<String> transcript = new ArrayList<>();
        ArrayList<String> usersInTicket = new ArrayList<>();
        id.getIterableHistory().reverse().takeAsync(1000).thenAccept(messages -> {
            for(Message message: messages){
                if(message.getAuthor().isBot()){
                    continue;
                }
                if(!usersInTicket.contains(message.getAuthor().getAsTag())){
                    usersInTicket.add(message.getAuthor().getAsTag());
                }
                String build = message.getAuthor().getAsTag() + ": " +
                        message.getContentRaw();
                transcript.add(build);
            }
            try{
                FileWriter fileWrite = new FileWriter("transcript.txt");
                for(String i:transcript){
                    fileWrite.write((i) + "\n");
                }
                fileWrite.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Transcript logs " + TimeFormat.DATE_TIME_SHORT.now());
            eb.setDescription("**Users in log:** " + "\n");
            for(String i: usersInTicket){
                eb.appendDescription(i +"\n");
            }
            logger.info("Creating transcript...");
            Objects.requireNonNull(guild.getTextChannelById(TRANSCRIPT_TICKET_ID)).sendMessageEmbeds(eb.build()).addFile(new File("transcript.txt")).queue();
        });
    }
    public void disregard(){
        List<Message> messageHistory = new ArrayList<>();
        id.getIterableHistory().cache(false).forEach(messageHistory::add);
        Collections.reverse(messageHistory);
        try{
            for(int i = 2; i < messageHistory.size(); i++){
                Message message = messageHistory.get(i);
                if(message.getAuthor().isBot()){
                    id.deleteMessageById(message.getId()).queue();
                }
            }
        } catch(IndexOutOfBoundsException e){
            logger.error("While deleting redundant message, index out of bounds");
        }
    }
    public static class Builder{
        private final TextChannel id;
        private Category category;
        private String transcriptId;
        private Guild guild;

        public Builder(TextChannel id){
            this.id = id;
        }
        public Builder category(Category category){
            this.category = category;
            return this;
        }
        public Builder guild(Guild guild){
            this.guild = guild;
            return this;
        }
        public Builder transcriptId(String transcriptId){
            this.transcriptId = transcriptId;
            return this;
        }
        public TicketActions build(){
            return new TicketActions(this);
        }
    }
}
