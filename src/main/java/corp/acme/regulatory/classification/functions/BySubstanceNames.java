package corp.acme.regulatory.classification.functions;

import corp.acme.common.domain.Classification;
import corp.acme.regulatory.classification.ClassificationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BySubstanceNames implements Function<List<String>, List<Classification>> {
    @Autowired
    ClassificationService classificationService;

    @Override
    public List<Classification> apply(List<String> names) {
        return names.stream().map(n ->
                this.classificationService.bySubstanceName(n)).collect(Collectors.toList());
    }
}
