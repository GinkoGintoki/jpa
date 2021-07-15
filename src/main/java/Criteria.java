import entity.Product;
import entity.ProductOption;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ConstantConditions")
public class Criteria {

    public static void main(String[] args) {
        Long categoryId = null;
        String name = null;
        Double minPrice = null;
        Double maxPrice = 555555555555555D;
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        HashMap<Long, String> map = new HashMap<>();
        map.put(2L, "8");
        map.put(3L, "AMD");

        for (Map.Entry<Long,String> m:
             map.entrySet()) {
            Join<Product, ProductOption> join = root.join("productOptions", JoinType.INNER);
            join.on(builder.equal(join.get("option").get("id"), m.getKey()), builder.equal(join.get("value"), m.getValue()));
        }

        List<Predicate> predicateList = new ArrayList<>();

        if (categoryId != null) {
            predicateList.add(builder.equal(root.get("category").get("id"), categoryId));
        }
        if (name != null) {
            predicateList.add(builder.like(root.get("name"), name));
        }	
        if (minPrice != null) {
            predicateList.add(builder.greaterThanOrEqualTo(root.get("price"), minPrice));
        }
        if (maxPrice != null) {
            predicateList.add(builder.lessThanOrEqualTo(root.get("price"), maxPrice));
        }


        query.where(predicateList.toArray(new Predicate[0]));
        List<Product> list = manager.createQuery(query).getResultList();
        for (Product product : list) {
            System.out.println(product.getName());
        }
    }
}
