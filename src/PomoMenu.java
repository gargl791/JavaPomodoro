
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.event.MenuListener;

public class PomoMenu {

    private JMenu settings, help;
    private JMenuBar menuBar;
    
    public PomoMenu() {
        PomoMenuDesign();
    }

    public void PomoMenuDesign(){
        menuBar = new JMenuBar();
        settings = new JMenu("Settings");
        help = new JMenu("Help");
        settings.addMenuListener(new MenuListener() {
            public void menuSelected(javax.swing.event.MenuEvent e) {
                System.out.println("Settings");
            }
            public void menuDeselected(javax.swing.event.MenuEvent e) {
                
            }
            public void menuCanceled(javax.swing.event.MenuEvent e) {
                
            }
        });
        help.addMenuListener(new MenuListener() {
            public void menuSelected(javax.swing.event.MenuEvent e) {
                System.out.println("Help");
            }
            public void menuDeselected(javax.swing.event.MenuEvent e) {
                
            }
            public void menuCanceled(javax.swing.event.MenuEvent e) {
                
            }
        });
        menuBar.add(settings);
        menuBar.add(help);
    }

    public JMenuBar getPomoMenu(){
        return menuBar;
    }

}
