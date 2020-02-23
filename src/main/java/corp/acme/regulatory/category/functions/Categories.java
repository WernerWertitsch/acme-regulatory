package corp.acme.regulatory.category.functions;

import corp.acme.common.domain.Category;
import corp.acme.regulatory.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.function.Supplier;

public class Categories implements Supplier<List<Category>> {
    @Autowired
    CategoryService categoryService;

    @Override
    public List<Category> get() {
        return this.categoryService.getAllCategories();
    }
}
