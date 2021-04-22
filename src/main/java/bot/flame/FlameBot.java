package bot.flame;

import bot.flame.config.Configuration;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public class FlameBot {

    private final Configuration config = new Configuration();
    private final ExecutorService executor = Executors.newFixedThreadPool(2);
    private final JDA jda;

    public FlameBot() throws Exception {
        jda = JDABuilder
                .createDefault(config.getValue("token").getValue())
                .setActivity(Activity.of(
                        Activity.ActivityType.valueOf(config.getValue("activityType").getValue().toUpperCase()),
                        config.getValue("activityStatus").getValue()))
                .setStatus(
                        OnlineStatus.valueOf(config.getValue("statusType").getValue().toUpperCase()))

                .build();
    }

    public static void main(String[] args) throws Exception {
        new FlameBot();
    }
}
