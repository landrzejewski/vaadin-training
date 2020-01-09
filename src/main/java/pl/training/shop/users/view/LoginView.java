package pl.training.shop.users.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import pl.training.shop.users.model.SecurityService;

@Route("login")
public class LoginView extends VerticalLayout {

    public LoginView(SecurityService securityService) {
        LoginForm loginForm = new LoginForm(securityService);
        add(loginForm);
    }

}
