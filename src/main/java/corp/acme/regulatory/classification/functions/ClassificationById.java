package corp.acme.regulatory.classification.functions;

import corp.acme.common.domain.Classification;
import corp.acme.regulatory.classification.ClassificationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Function;

public class ClassificationById implements Function<String, Classification> {
    @Autowired
    ClassificationService classificationService;

    @Override
    public Classification apply(String id) {
        return this.classificationService.byId(id);
    }
}
