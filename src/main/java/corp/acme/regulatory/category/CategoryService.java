package corp.acme.regulatory.category;

import corp.acme.common.domain.Category;
import corp.acme.common.domain.Classification;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    /* Important, this would usually be a no-sql or somethin repo! */
    private static final String[] CATEGORIES_ALL_THIS_SHOULD_BE_IN_A_REPO_OR_SMTHG = {
        "Toxic",
        "Hazardous",
        "Reactive",
        "Explosive",
    };

    Map<String, Category> byName = new HashMap<>();
    Map<String, Category> byId = new HashMap<>();
    List<Category> regularOrder = new ArrayList<>();


    @PostConstruct
    private void init() {
        this.createSampleData();
    }

    public List<Category> getAllCategories() {
        return this.regularOrder;
    };

    public Category getByName(String name) {
        return this.byName.get(name.toLowerCase());
    }

    public List<Category> getByNames(List<String> names) {
        return names.stream().map(n -> getByName(n)).filter(c -> c != null).collect(Collectors.toList());
    }

    public Category getById(String id) {
        return this.byId.get(id);
    }

    /* This is supposed to come from a (probably NoNSQL) Database */
    private void createSampleData() {
        int i=1;
        for(String cat: CATEGORIES_ALL_THIS_SHOULD_BE_IN_A_REPO_OR_SMTHG) {
            Category category = new Category();

            /*  The reason I am not using a uuid here is to demonstrate that refering to the categories by
                id (and updating the name on the client side controlled by the client)
             */
            category.setId(String.format("CATEGORY-%d", i));
            category.setName(cat);
            category.setDescription(String.format("Danger Level %d", i++));

            this.byName.put(category.getName().toLowerCase(), category);
            this.byId.put(category.getId(), category);
            this.regularOrder.add(category);
        }
    }


}
