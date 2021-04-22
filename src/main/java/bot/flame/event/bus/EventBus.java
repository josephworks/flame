package bot.flame.event.bus;

import bot.flame.event.Event;
import bot.flame.event.annotation.EventTarget;
import bot.flame.event.listener.EventListener;
import lombok.SneakyThrows;
import org.atteo.classindex.ClassIndex;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventBus {

    private final Map<EventListener, List<Method>> methods = new HashMap<>();

    @SneakyThrows
    public void initializeListeners() {
        for (Class<? extends EventListener> clazz : ClassIndex.getSubclasses(EventListener.class, Thread.currentThread().getContextClassLoader())) {
            if (Modifier.isAbstract(clazz.getModifiers())) {
                continue;
            }
            EventListener listener = clazz.newInstance();
            List<Method> targetMethods = Stream.of(clazz.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(EventTarget.class))
                    .collect(Collectors.toList());
            methods.put(listener, targetMethods);
        }
    }

    @SneakyThrows
    public void postEvent(Event event) {
        for (Map.Entry<EventListener, List<Method>> entry : methods.entrySet()) {
            EventListener listener = entry.getKey();
            List<Method> methods = entry.getValue();
            methods.stream().filter(method -> {
                EventTarget annotation = method.getDeclaredAnnotation(EventTarget.class);
                return annotation.target().equals(event.getClass());
            }).forEach(method -> {
                try {
                    method.invoke(listener, event);
                } catch (InvocationTargetException | IllegalAccessException exc) {
                    exc.printStackTrace();
                }
            });
        }
    }
}
