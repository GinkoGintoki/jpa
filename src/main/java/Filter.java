import com.google.protobuf.MapEntry;
import entity.Option;
import entity.Product;
import entity.ProductOption;

import javax.persistence.*;
import java.util.*;

public class Filter {

    public static void main(String[] args) {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();

        int itemsPerPage = 1;
        int currentPage = 1;

        Long categoryId = 1L;
        String name = null;
        Double minPrice = null;
        Double maxPrice = null;

        Map<Long, String> options = new HashMap<>();
        options.put(2L, "8");
        options.put(3L, "AMD");

        String queryCategoryId = "p.category.id = " + categoryId;
        String queryMinPrice = "p.price >=" + minPrice;
        String queryMaxPrice = "p.price <= " + maxPrice;
        String queryName = "p.name like '%" + name + "%'";


        List<String> list = new ArrayList<>();

        if (categoryId != null) {
            list.add(queryCategoryId);
        }
        if (name != null) {
            list.add(queryName);
        }
        if (minPrice != null) {
            list.add(queryMinPrice);
        }
        if (maxPrice != null) {
            list.add(queryMaxPrice);
        }

        String finalString = " where ";

        finalString = finalString + String.join(" and ", list);

        if (list.size() == 0) {
            finalString = "";
        }
        int i = 0;
        for (Map.Entry<Long, String> map :
                options.entrySet()) {
            String mapString = " inner join ProductOption po_" + i + " on p.id = po_"+i+".product.id and po_" + i + ".option = " + map.getKey() + " and po_" + i + ".value = '" + map.getValue()+"'";
            i++;
            finalString = mapString + finalString;
        }
        System.out.println(finalString);

/*
        HashMap<Object, String> list = new LinkedHashMap<>() {
            {
                put(categoryId, queryCategoryId);
                put(minPrice, queryMinPrice);
                put(maxPrice, queryMaxPrice);
                put(name, queryName);
            }
        };
        for (Map.Entry<Object, String> map :
                list.entrySet()) {
            for (Map.Entry<Object, String> mapIn :
                    list.entrySet()) {
                if (map.getKey() == null) {
                    map.setValue(" ");
                    break;
                } else if (mapIn.getKey() == null || mapIn.getKey() == map.getKey()) {
                    if (mapIn.getKey() == map.getKey()) {
                        break;
                    }
                } else {
                    map.setValue(" and " + map.getValue());
                    break;
                }
            }
        }

        String finalString = "where ";

        for (Map.Entry<Object, String> map :
                list.entrySet()) {
            System.out.println(map.getKey() + " " + map.getValue());
            finalString += map.getValue();
        }*/



        /*for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < i; j++) {
                if(list.get(i) == null){
                    list.
                }
            }
        }

        if (categoryId == null) {
            queryCategoryId = " ";
        }
        if (minPrice == null) {
            queryMinPrice = " ";
        }else if(categoryId!=null){
            queryCategoryId += " and ";
        }
        if (maxPrice == null) {
            queryMaxPrice = " ";
        }else if(categoryId!=null || minPrice!=null){
            queryMaxPrice = " and " + queryMaxPrice;
        }
        if (name == null) {
            queryName = " ";
        }else if(categoryId!=null || minPrice!=null || maxPrice!=null){
            queryName = " and "+ queryName;
        }
        */
        TypedQuery<Product> typedQuery;
        typedQuery = manager.createQuery("select p from Product p "
                        + finalString,
                Product.class);

        typedQuery.setFirstResult((currentPage - 1) * itemsPerPage);
        typedQuery.setMaxResults(itemsPerPage);

        List<Product> products = typedQuery.getResultList();

        // name like '%Samsung%' (contains)
        // name like 'Samsung%' (startsWith)
        // name like '%Samsung' (endsWith)

        for (Product pr :
                products) {
            System.out.println(pr.getId() + " " + pr.getName() + " " + pr.getPrice());
        }
    }
}
