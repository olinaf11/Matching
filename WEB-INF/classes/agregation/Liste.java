package agregation;

/* Classe pour les fonctions d'agregation Généralisée
 *         (sommer, min, max, sort, avg);
 */
public class Liste {

    public static double sommer(Object[] objects, String function) throws Exception {
        double somme = 0;
        for (int i = 0; i < objects.length; i++)
            somme = somme + (double) objects[i].getClass().getMethod(function).invoke(objects[i]);
        return somme;
    }

    public static Object min(Object[] objects, String function) throws Exception {
        double min = (double) objects[0].getClass().getMethod(function).invoke(objects[0]);
        Object minimum = objects[0];
        for (int i = 0; i < objects.length; i++) {
            if ((double) objects[i].getClass().getMethod(function).invoke(objects[i]) < min) {
                min = (double) objects[i].getClass().getMethod(function).invoke(objects[i]);
                minimum = objects[i];
            }
        }
        return minimum;
    }

    public static Object max(Object[] objects, String function) throws Exception {
        double max = (double) objects[0].getClass().getMethod(function).invoke(objects[0]);
        Object maximum = objects[0];
        for (int i = 0; i < objects.length; i++) {
            if ((double) objects[i].getClass().getMethod(function).invoke(objects[i]) > max) {
                max = (double) objects[i].getClass().getMethod(function).invoke(objects[i]);
                maximum = objects[i];
            }
        }
        return maximum;
    }

    public static void sort(Object[] objects, String function, String order) throws Exception {
        boolean check;
        for (int i=0; i<objects.length-1; i++) {
            int index = i;  
            for (int j = i + 1; j < objects.length; j++) {
                check=(order == "DESC") ? (double) objects[i].getClass().getMethod(function).invoke(objects[j]) > (double) objects[i].getClass().getMethod(function).invoke(objects[index]) 
                : (double) objects[i].getClass().getMethod(function).invoke(objects[j]) < (double) objects[i].getClass().getMethod(function).invoke(objects[index]);
                if(check)
                    index = j;
            }
            Object min = objects[index];
            objects[index] = objects[i];
            objects[i] = min;
        }
    }

    //AVG
    public static double avg(Object[] objects, String function) throws Exception {
        return sommer(objects, function) / objects.length;
    }
}