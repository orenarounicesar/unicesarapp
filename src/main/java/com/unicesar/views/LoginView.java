package com.unicesar.views;

import com.unicesar.beans.Usuario;
import com.unicesar.utils.Enrutador;
import com.unicesar.utils.VariablesSesion;
import com.unicesar.utils.SeveralProcesses;
import com.unicesar.utils.Views;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class LoginView extends VerticalLayout implements View {
    
    private TextField txtLogin;  
    private PasswordField txtPassword;
    private Button btnIngresar;
    private FormLayout layoutLogin;
    private Panel panelLogin;
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.setWidth("100%");
        this.setHeight("100%");
        this.setMargin(true);

        setWidth("100.0%");
        setHeight("100.0%");

        setStyleName("fondoaplicacion");

        panelLogin = new Panel();
        panelLogin.setCaption("Acceso a Unicesar App");
        panelLogin.setSizeUndefined();
        panelLogin.setStyleName("panelverdeancho", true);
        panelLogin.setStyleName("bordeverde", true);

        layoutLogin = new FormLayout();
        layoutLogin.setImmediate(false);
        layoutLogin.setWidth("450px");
        layoutLogin.setHeight("100.0%");
        layoutLogin.setMargin(true);
        layoutLogin.setSpacing(true);

        txtLogin = new TextField();
        txtLogin.setRequired(true);
        txtLogin.setCaption("Usuario:");
        txtLogin.setWidth("100%");
        txtLogin.setIcon(VaadinIcons.USER);
        txtLogin.focus();
        layoutLogin.addComponent(txtLogin);

        txtPassword = new PasswordField();
        txtPassword.setRequired(true);
        txtPassword.setCaption("Contraseña:");
        txtPassword.setImmediate(false);
        txtPassword.setWidth("100%");
        txtPassword.setHeight("-1px");
        txtPassword.setIcon(VaadinIcons.KEY);
        layoutLogin.addComponent(txtPassword);

        btnIngresar = new Button("Ingresar" , VaadinIcons.CHECK);
        btnIngresar.setImmediate(true);
        btnIngresar.setWidth("100%");
        btnIngresar.setHeight("-1px");
        btnIngresar.addStyleName("primary");
        btnIngresar.setClickShortcut( KeyCode.ENTER );
        btnIngresar.addClickListener(e -> {
            if (SeveralProcesses.isComponentRequired(layoutLogin)) {
                validarLogin();
            }
        });
        layoutLogin.addComponent(btnIngresar);
        layoutLogin.setComponentAlignment(btnIngresar, new Alignment(20));

        panelLogin.setContent(layoutLogin);

        this.addComponent(panelLogin);
        this.setComponentAlignment(panelLogin, Alignment.MIDDLE_CENTER);
        
//        try {
//            GraphQLResponseEntity<AsignaturaGraphql> responseEntity = SeveralProcesses.callGraphQLService(
//                    "http://localhost:5000/graphql", "{classById(id:1){codigoAsignatura,nombreAsignatura}}"
//            );
//            System.out.println(responseEntity.getResponse().getClassById().toString());
//        } catch (IOException ex) {
//            Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
    }
    
    private void validarLogin() {
        Usuario usuario = Enrutador.getLogin(txtLogin.getValue(), txtPassword.getValue());
        if ( usuario.getCodigoUsuario() == 0 ) {
            Notification.show("Usuario y/o Contraseña Inconrectos", Notification.Type.HUMANIZED_MESSAGE);
        } else {
            UI.getCurrent().getSession().setAttribute(VariablesSesion.CODIGO_USUARIO, usuario.getCodigoUsuario());
            UI.getCurrent().getSession().setAttribute(VariablesSesion.LOGIN, usuario.getLogin());
            UI.getCurrent().getSession().setAttribute(VariablesSesion.NOMBRE_USUARIO, usuario.getNombreUsuario());
//            if ( usuario.getCodigoDocente() > 0 )
                UI.getCurrent().getSession().setAttribute(VariablesSesion.CODIGO_DOCENTE, usuario.getCodigoDocente());
//            if ( usuario.getCodigoEstudiante() > 0 )
                UI.getCurrent().getSession().setAttribute(VariablesSesion.CODIGO_ESTUDIANTE, usuario.getCodigoEstudiante());
            
            UI.getCurrent().getNavigator().navigateTo(Views.MAIN);
        }
    }
}
