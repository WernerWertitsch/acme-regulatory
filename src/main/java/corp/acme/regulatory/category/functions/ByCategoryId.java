package corp.acme.regulatory.category.functions;

import corp.acme.common.domain.Category;
import corp.acme.regulatory.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Function;

public class ByCategoryId implements Function<String, Category> {
    @Autowired
    CategoryService categoryService;

    @Override
    public Category apply(String id) {
        return this.categoryService.getById(id);
    }
}
