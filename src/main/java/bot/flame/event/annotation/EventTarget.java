package bot.flame.event.annotation;

import bot.flame.event.Event;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EventTarget {

    Class<? extends Event> target = null;

    Class<? extends Event> target();

}
