package pl.training.shop.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.templatemodel.TemplateModel;
import lombok.extern.java.Log;

@Log
@Tag("app-hello")
@JsModule("./src/app-hello.js")
public class AppHello extends PolymerTemplate<AppHello.AppHelloModel> {

    public interface AppHelloModel extends TemplateModel {

        void setName(String name);

        String getName();

    }

    @Id("hello-btn")
    private Button helloButton;

    @EventHandler
    public void sayHello() {
        log.info("Hello JS");
        helloButton.setVisible(false);
        UI ui = UI.getCurrent();
        new Thread(() -> {
            try {
                Thread.sleep(3_000);
                ui.access(() -> getModel().setName("JS is the best!"));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

}
