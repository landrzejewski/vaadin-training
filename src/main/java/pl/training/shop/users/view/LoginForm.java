package pl.training.shop.users.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import pl.training.shop.MainView;
import pl.training.shop.users.model.SecurityService;

public class LoginForm extends VerticalLayout {

    private TextField loginField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private HorizontalLayout buttonsLayout = new HorizontalLayout();
    private Button loginButton = new Button();
    private Label loginError = new Label();

    private final SecurityService securityService;

    public LoginForm(SecurityService securityService) {
        this.securityService = securityService;
        initFields();
        initButtons();
        add(buttonsLayout);
        add(loginError);
    }

    private void initFields() {
        loginField.setLabel(getTranslation("user.login"));
        passwordField.setLabel(getTranslation("user.password"));
        add(loginField, passwordField);
    }

    private void initButtons() {
        loginButton.setText(getTranslation("login"));
        loginButton.addClickListener(event -> login());
        buttonsLayout.add(loginButton);
    }

    private void login() {
        String login = loginField.getValue();
        String password = passwordField.getValue();
        if (securityService.authenticate(login, password)) {
            UI.getCurrent().navigate(MainView.class);
        } else {
            loginError.setText(getTranslation("invalidLoginOrPassword"));
        }
    }

}
