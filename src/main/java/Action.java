import entity.Category;
import entity.Option;
import entity.Product;
import entity.ProductOption;

import javax.persistence.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Action {

    public static void add() throws IOException {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        TypedQuery<Category> categoryTypedQuery = manager.createQuery("select c from Category c", Category.class);
        List<Category> categoryList = categoryTypedQuery.getResultList();

        for (Category category :
                categoryList) {
            System.out.println("ID: " + category.getId() + " | Name: " + category.getName());
        }

        System.out.print("Введите ID категории: ");
        long categoryIdIn;
        while (true) {
            try {
                categoryIdIn = Long.parseLong(bufferedReader.readLine());
                break;
            } catch (NumberFormatException e) {
                System.out.print("Неверно введен ID категории! Пожалуйста введите снова: ");
            }
        }

        System.out.print("Введите название товара: ");
        String nameIn = bufferedReader.readLine();

        System.out.print("Введите стоимость товара: ");
        double priceIn;
        while (true) {
            try {
                priceIn = Double.parseDouble(bufferedReader.readLine());
                break;
            } catch (NumberFormatException e) {
                System.out.print("Неверно введена цена товара! Пожалуйста введите снова: ");
            }
        }

        try {
            manager.getTransaction().begin();
            Product product = new Product();

            Category category = manager.find(Category.class, categoryIdIn);
            product.setCategory(category);
            product.setName(nameIn);
            product.setPrice(priceIn);

            manager.persist(product);

            List<Option> optionList = category.getOptions();
            for (Option option : optionList) {
                ProductOption productOption = new ProductOption();
                System.out.print("Укажите " + option.getName() + " товара: ");
                String value = bufferedReader.readLine();
                productOption.setOption(option);
                productOption.setProduct(product);
                productOption.setValue(value);
                manager.persist(productOption);
            }

            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
        } finally {
            manager.close();
        }
    }

    public static void update() throws IOException {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        TypedQuery<Product> productTypedQuery = manager.createQuery("select p from Product p", Product.class);
        List<Product> productList = productTypedQuery.getResultList();

        for (Product product :
                productList) {
            System.out.println("ID: " + product.getId() +
                    " | Category: " + product.getCategory().getName() +
                    " | Name: " + product.getName() +
                    " | Price: " + product.getPrice());
        }

        System.out.print("Выберите ID продукта: ");
        long productIdIn;
        while (true) {
            try {
                productIdIn = Long.parseLong(bufferedReader.readLine());
                break;
            } catch (NumberFormatException e) {
                System.out.print("Неверно введен ID продукта! Пожалуйста введите снова: ");
            }
        }

        try {
            manager.getTransaction().begin();

            Product product = manager.find(Product.class, productIdIn);

            System.out.print("Введите название товара: ");
            String nameIn = bufferedReader.readLine();

            if (!nameIn.isEmpty()) {
                product.setName(nameIn);
            }

            System.out.print("Введите цену товара: ");
            String priceIn = bufferedReader.readLine();

            if (!priceIn.isEmpty()) {
                double productPrice;
                while (true) {
                    try {
                        productPrice = Double.parseDouble(priceIn);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.print("Неверно введена цена товара! Пожалуйста введите снова: ");
                    }
                }
                product.setPrice(productPrice);
            }

            List<ProductOption> productOptionList = product.getProductOptions();
            for (ProductOption productOption : productOptionList) {
                System.out.print("Введите " + productOption.getOption().getName() + " товара: ");
                String value = bufferedReader.readLine();
                if (!value.isEmpty()) {
                    productOption.setValue(value);
                }
            }

            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
        } finally {
            manager.close();
        }
    }

    public static void delete() throws IOException {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        TypedQuery<Product> productTypedQuery = manager.createQuery("select p from Product p", Product.class);
        List<Product> productList = productTypedQuery.getResultList();

        for (Product product :
                productList) {
            System.out.println("ID: " + product.getId() +
                    " | Category: " + product.getCategory().getName() +
                    " | Name: " + product.getName() +
                    " | Price: " + product.getPrice());
        }

        System.out.print("Выберите ID продукта: ");
        long productIdIn;
        while (true) {
            try {
                productIdIn = Long.parseLong(bufferedReader.readLine());
                break;
            } catch (NumberFormatException e) {
                System.out.print("Неверно введен ID продукта! Пожалуйста введите снова: ");
            }
        }

        try {
            manager.getTransaction().begin();

            Product product = manager.find(Product.class, productIdIn);

            manager.remove(product);

            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
        } finally {
            manager.close();
        }
    }
}
