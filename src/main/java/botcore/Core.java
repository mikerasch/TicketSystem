package botcore;

import config.Config;
import listeners.OnButtonInteractionCreateTicket;
import listeners.OnCloseTicketListener;
import listeners.OnMessageSendInfoListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public final class Core extends ListenerAdapter {
    public Core(JDA jda, Config config) {
        jda.addEventListener(new OnMessageSendInfoListener(config));
        jda.addEventListener(new OnButtonInteractionCreateTicket(config));
        jda.addEventListener(new OnCloseTicketListener(config));

    }


}
