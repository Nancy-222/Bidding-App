import view.HomePage;
import controller.AppController;
public class Run {
    public static void main(String[] args) {
        AppController c = new AppController();
        new HomePage(c);
    }
}
