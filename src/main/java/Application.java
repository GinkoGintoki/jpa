import entity.Category;
import entity.Option;
import entity.Product;
import entity.ProductOption;

import javax.persistence.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;
import java.util.Queue;

public class Application {

    public static void main(String[] args) throws IOException {

/*        Category category = manager.find(Category.class,3L);
        for (Product product : category.getProducts()) {
            System.out.println(product.getName());
        }*/
/*
        TypedQuery<Category> query = manager.createQuery("select c from Category c order by c.id desc",Category.class);

        List<Category> list = query.getResultList();
        for (Category category : list) {
            System.out.println(category.getId() + " " + category.getName());
        }*/
/*
        TypedQuery<Long> query = manager.createQuery("select count(p.category) from Product p ",Long.class);
        System.out.println(query.getSingleResult());
*/
        /*try {
            manager.getTransaction().begin();

            Category category = new Category();
            category.setName("Материнские платы");
            manager.persist(category);
            Category category = manager.find(Category.class,6L);
            category.setName("nn");

            manager.getTransaction().commit();
        }catch (Exception e){
            manager.getTransaction().rollback();
        }*/

        /*BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String name = bufferedReader.readLine();

        TypedQuery<Long> query = manager.createQuery("SELECT count(c.id) from Category c where c.name = ?1",Long.class);
        query.setParameter(1,name);
        long count = query.getSingleResult();
        if (count==0) {
            try {
                manager.getTransaction().begin();

                Category category = new Category();

                category.setName(name);

                manager.persist(category);

                manager.getTransaction().commit();

            } catch (Exception e) {
                manager.getTransaction().rollback();
            }
        }else {
            System.out.println("Категория с таким именем уже существует.");
        }

    }*/

        System.out.println("Выберите действие:");
        System.out.println("1. Добавить");
        System.out.println("2. Изменить");
        System.out.println("3. Удалить");
        System.out.println("4. Выйти");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String actionIn = bufferedReader.readLine();

        while (true) {
            if (actionIn.matches("[1-4]")) {
                break;
            } else {
                System.out.print("Неверно введен номер действия! Пожалуйства введите снова: ");
                actionIn = bufferedReader.readLine();
            }
        }

        switch (actionIn) {
            case "1" -> Action.add();
            case "2" -> Action.update();
            case "3" -> Action.delete();
            default -> {
                System.out.println("До свидания!");
                System.exit(0);
            }
        }
    }
}
