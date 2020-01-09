package pl.training.shop.view;

import com.vaadin.flow.shared.Registration;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static java.util.Collections.emptyList;

@Service
public class EventBus {

    public interface EventListener<T extends Event> {

        void onEvent(T event);

    }

    public interface Event {
    }

    @Setter
    private Executor executor = Executors.newSingleThreadExecutor();
    @Setter
    private Map<Class<? extends Event>, List<EventListener>> listeners = new HashMap<>();

    public synchronized Registration on(Class<? extends Event> eventType, EventListener<? extends Event> listener) {
        listeners.putIfAbsent(eventType, new ArrayList<>());
        List<EventListener> currentListeners = listeners.get(eventType);
        currentListeners.add(listener);
        return () -> currentListeners.remove(listener);
    }

    public synchronized <T extends Event> void fireEvent(T event) {
        executor.execute(() -> notifyListeners(event));
    }

    private <T extends Event> void notifyListeners(T event) {
        listeners.getOrDefault(event.getClass(), emptyList()).forEach(listener -> listener.onEvent(event));
    }

}
