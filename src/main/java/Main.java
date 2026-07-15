import controller.Controller;
import gui.Login;

import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        Login login = new Login(controller);
    }
}