package corp.acme.regulatory.classification.functions;

import corp.acme.common.domain.Classification;
import corp.acme.regulatory.classification.ClassificationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.function.Supplier;

public class Classifications implements Supplier<List<Classification>> {
    @Autowired
    ClassificationService classificationService;

    @Override
    public List<Classification> get() {
        return this.classificationService.all();
    }
}
