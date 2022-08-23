import botcore.Core;
import config.Config;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.HoldTickets;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    private static final String CONFIGJSON = "config.json";
    public static void main(String[] args){
        InputStream inputStream = Application.class.getClassLoader().getResourceAsStream(CONFIGJSON);
        Config config;
        try{
            config = Config.load(inputStream);
        } catch (IOException e) {
            logger.error("Unable to load the configuration file!");
            return;
        }
        try{
            runBot(config);
        } catch (Exception e) {
            logger.error("Error while trying to initially start the bot!");
        }
        int MINUTES = 20;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                logger.warn("Clearing rate limiting map....");
                HoldTickets.rateLimit.clear();
            }
        }, 0, 1000 * 60 * MINUTES);
    }

    public static void runBot(Config config){
        logger.info("Starting the bot");
        try{
            JDA jda = JDABuilder.createLight(config.getToken(),GatewayIntent.DIRECT_MESSAGES,GatewayIntent.GUILD_MESSAGES,GatewayIntent.GUILD_EMOJIS_AND_STICKERS)
                    .build();
            jda.addEventListener(new Core(jda,config));
            jda.awaitReady();
            logger.info("Bot is now ready");
        } catch (LoginException e) {
            logger.error("Failed to start the JDA instance!");
        } catch (InterruptedException e) {
            logger.error("Error occurred while waiting for JDA to finish initializing...");
        }
    }
}
