package pl.training.shop.view;

import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Log
@RequiredArgsConstructor
@VaadinSessionScope
@Service
public class DisposableEventBus {

    @NonNull
    private EventBus eventBus;
    private List<Registration> registrations = new ArrayList<>();

    public void on(Class<? extends EventBus.Event> eventType, EventBus.EventListener<? extends EventBus.Event> listener) {
        registrations.add(eventBus.on(eventType, listener));
    }

    public <T extends EventBus.Event> void fireEvent(T event) {
        eventBus.fireEvent(event);
    }

    @PreDestroy
    private void onClose() {
        registrations.forEach(Registration::remove);
    }

}
