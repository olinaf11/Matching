package formulaire;

import java.lang.reflect.Field;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Champ {
    JComponent champ;
    JLabel label;
    boolean isVisible = true;
    String defaultValue = "";
    String css;
    Field attribut;
    String type = "text";

    //GET
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public Field getAttribut() {
        return attribut;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public JComponent getChamp() {
        return champ;
    }

    public JLabel getLabel() {
        return label;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public void setLabel(String text) {
        this.label = new JLabel(text);
    }

    /**
     * <p>Function to set Visibilty to true or false<p>
     * @param isVisible
     * @param textdefault : hidden value
     */
    public void setVisible(boolean isVisible, String textdefault) {
        this.isVisible = isVisible;
        setDefault(textdefault);
    }

    /**
     * <p>Make default value in JTextField or Dropdown<p>
     * @param text
     */
    public void setDefault(String text) {
        if(this.champ instanceof JTextField) {
            this.defaultValue = text;
            JTextField field = (JTextField) this.champ;
            field.setText(this.defaultValue);
        } else if(this.champ instanceof DropDown) {
            this.defaultValue = text;
            DropDown box = (DropDown) this.champ;
            box.setSelectedItem(this.defaultValue);
        }
    }

    public void setChamp(JComponent champ) {
        this.champ = champ;
    }

    public void setAttribut(Field attribut) {
        this.attribut = attribut;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * <p>Function to change field to Dropdown<p>
     * @param data : to show in list o dropdown
     * @param value : to get value
     */
    public void changeToDrop(String[] data, String[] value) throws Exception {
        setChamp(new DropDown(data, value));
    }

    public Champ(JComponent component, Field field) {
        setChamp(component);
        setLabel(Champ.toUpperCasefisrtLetter(field.getName())+" :");
        setCss("");
        setAttribut(field);
    }

    public static String toUpperCasefisrtLetter(String name) {
        String firstLetter = name.substring(0, 1);
        String remainingLetters = name.substring(1);
        firstLetter = firstLetter.toUpperCase();
        name = firstLetter + remainingLetters;
        return name;
    }
}