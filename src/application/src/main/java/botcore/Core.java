package botcore;

import config.Config;
import listeners.OnMessageSendInfoListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Core extends ListenerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(Core.class);
    private final Config config;
    public Core(JDA jda, Config config) {
        this.config = config;
        jda.addEventListener(new OnMessageSendInfoListener(jda, config));
    }


}
