package pizzashop.repository;

import pizzashop.model.MenuPizza;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MenuRepository {
    private static final String FILENAME = "data/menu.txt";
    private List<MenuPizza> menu;

    public MenuRepository(){
        this.readMenu();
    }

    private void readMenu(){
        ClassLoader classLoader = MenuRepository.class.getClassLoader();
        File file = new File(classLoader.getResource(FILENAME).getFile());
        this.menu = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = null;
            while((line=br.readLine())!=null){
                MenuPizza menuPizza = parseLineToPizza(line);
                menu.add(menuPizza);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MenuPizza parseLineToPizza(String line){
        MenuPizza menuPizza =null;
        if (line==null|| line.equals("")) return null;
        StringTokenizer st=new StringTokenizer(line, ",");
        String name= st.nextToken();
        double price = Double.parseDouble(st.nextToken());
        menuPizza = new MenuPizza(name, price);
        return menuPizza;
    }

    public List<MenuPizza> getMenu(){
        return menu;
    }

}
