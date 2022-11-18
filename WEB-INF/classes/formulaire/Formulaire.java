package formulaire;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Formulaire extends JPanel {
    
    JFrame frame;
    Object object;
    Champ[] listeChamp;
    Vector<Button> buttons = new Vector<Button>();

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
    
    public Champ[] getListeChamp() {
        return listeChamp;
    }

    public Object getObject() {
        return object;
    }

    public void setListeChamp(Champ[] listeChamp) {
        this.listeChamp = listeChamp;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void setButtons(Vector<Button> buttons) {
        this.buttons = buttons;
    }

    public void addButtons(Button[] buttons) {
        for(Button button : buttons)
            this.buttons.add(button);
    }

    public void addButton(Button button) {
        this.buttons.add(button);
    }
    
    protected Formulaire(Champ[] liste, Object object) {
        super(null);
        setListeChamp(liste);
        setObject(object);
        setBackground(new Color(92, 133, 214));
    }

    /**
     * <p>Function for creating Formulaire of this obj<p>
     * <p>Arguments are fields of this Formulaire<p>
     * @param obj : object to create formulaire
     * @return {@code Formulaire} an formulaire with fields of obj attributes
     * @throws Exception
     */
    public static Formulaire createFormulaire(Object obj) throws Exception {
        Field[] fields = obj.getClass().getDeclaredFields();
        Champ[] champs = new Champ[fields.length];
        if (fields.length == 0) throw new Exception("No argument in obj");
        for (int i = 0; i < fields.length; i++) {
            champs[i] = new Champ(new JTextField(), fields[i]);
            if (fields[i].getType() == boolean.class) {
                String[] value = {"true", "false"};
                champs[i].changeToDrop(value, value);
            }
        }
        return new Formulaire(champs, obj);
    }


    /**
     * <p>Validation of our update about champ of this formulaire<p>
     * <p>And make champs and buttons into this formulaire<p>
     */
    public void setPosition(JFrame frame) throws Exception {
        int p = 0;
        int dx = 0;
        for(Champ champ : this.listeChamp) {
            if(champ.isVisible()) {
                champ.getLabel().setBounds(20, 20 + p * 40, 400, 40);
                champ.getChamp().setBounds(160, 20 + p * 40, 300, 40);
                this.add(champ.getLabel());
                this.add(champ.getChamp());
                p++;
            }
        }
        if (buttons.size() == 0) throw new Exception("Any button is added in this formulaire");
        for (Button button : buttons) {
            button.setBounds(160 + dx, 20 + p * 40, button.getWidth(), button.getHeight());
            add(button);
            dx += 100;
        }
        initFrame(frame);
    }

    /**
     * <p>Set this formulaire into frame<p>
     * <p>This is useful to use after {@code setPosition()}<p>
     * @param frame
     */
    public void initFrame(JFrame frame) {
        setFrame(frame);
        frame.add(this);
        frame.setSize(600, getListeChamp().length * 50);
        frame.setResizable(false);
        frame.setLocation(100, 100);
        frame.setVisible(true);
    }

    public void disposeFrame() {
        frame.dispose();
    }


    //Check this function
    public String getHTMLString(String action, String method) {
        String html = "<form action=\"" + action + "\" method=\"" + method + "\">";
        for(Champ champ : this.listeChamp) {
            if(champ.getChamp() instanceof JTextField && champ.isVisible()) {
                html += "<div class=\"mb-3\"><label for=\"input\" class=\"mb-2\">" + champ.getLabel().getText() + "</label><input type=\"" + champ.getType() + "\" class=\"form-control\" id=\"input\" name=\"" + champ.getAttribut().getName() + "\"></div>";
            } else if(champ.getChamp() instanceof DropDown && champ.isVisible()) {
                html += "<label for=\"" + champ.getAttribut().getName() + "\" class=\"form-label\">" + champ.getLabel().getText() + "</label>";
                DropDown down = (DropDown) champ.getChamp();
                html += "<select class=\"form-select\" aria-label=\"Default select example\" id=\"" + champ.getAttribut().getName() + "\" name=\"" + champ.getAttribut().getName() + "\">";
                for (int j = 0; j < down.getValue().length; j++) {
                    html += "<option value=\"" + down.getValue()[j] + "\">" + down.getData()[j] + "</option>\n";
                }
                html += "</select></p>\n";
            }
        }
        html += "<div class=\"row mt-4\"><input type=\"submit\" value=\"OK\" class=\"btn btn-primary mt-3\"></div>";
        html += "</form>";
        return html;
    }

    /**
     * <p>Create an order into fields formulaire<p>
     * @param orders
     */
    public void setOrdre(String[] orders) {
        Champ[] liste = new Champ[orders.length];
        for (int i = 0; i < orders.length; i++) {
            for (Champ champ : this.listeChamp) {
                if (orders[i].compareTo(champ.getAttribut().getName()) == 0) liste[i] = champ;
            }
        }
        this.listeChamp = liste;
    }

    /**
     * <p>Reset order to origin arguments location<p>
     * @return {@code Champ[]}
     */
    public Champ[] resetOrder() {
        Field[] fields = this.object.getClass().getDeclaredFields();
        String[] order = new String[fields.length];
        for(int i = 0; i < fields.length; i++)
            order[i] = fields[i].getName();
        Formulaire form = new Formulaire(this.listeChamp, this.object);
        form.setOrdre(order);
        return form.getListeChamp();
    }
    
    /**
     * <p>Reset to null all fields (JTextField)<p>
     * 
     */
    public void resetform() {
        Vector<Champ> fields = getTextField();
        for(Champ field : fields) {
            if(field.isVisible()) field.setDefault("");
        }
    }
    
    /**
     * Check if one of this fields is Empty (JTextField) or not
     * @return true if empty else false
     */
    public boolean isEmpty() {
        Vector<Champ> fields = getTextField();
        for(Champ field : fields) {
            JTextField text = (JTextField) field.getChamp();
            if(field.isVisible() && text.getText().isEmpty()) return true;
        }
        return false;
    }

    /**
     * Get text of champs (JTextField or Dropdown)
     * @return value of formulaire
     */
    public String[] getText() {
        Champ[] champs = this.resetOrder();
        String[] fieldsStrings = new String[champs.length];
        for(int i = 0; i < champs.length; i++) {
            if(champs[i].getChamp() instanceof JTextField) {
                JTextField field = (JTextField) champs[i].getChamp();
                fieldsStrings[i] = field.getText();
            } else if(champs[i].getChamp() instanceof DropDown) {
                DropDown box = (DropDown) champs[i].getChamp();
                fieldsStrings[i] = box.getSelectedValue();
            }
        }
        return fieldsStrings;
    }

    /**
     * Get Components that are JTextField 
     * @return Champ with JTextField for Component
     */
    public Vector<Champ> getTextField() {
        Champ[] champs = this.resetOrder();
        Vector<Champ> fields = new Vector<Champ>();
        for(Champ champ : champs) {
            if(champ.getChamp() instanceof JTextField)
                fields.add(champ);
        }
        return fields;
    }
}