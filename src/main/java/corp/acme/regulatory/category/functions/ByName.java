package corp.acme.regulatory.category.functions;

import corp.acme.common.domain.Category;
import corp.acme.regulatory.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

public class ByName implements Function<String, Category> {
    @Autowired
    CategoryService categoryService;

    @Override
    public Category apply(String name) {
        return this.categoryService.getByName(name);
    }
}
