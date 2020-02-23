package corp.acme.regulatory.classification;

import corp.acme.common.domain.Category;
import corp.acme.common.domain.Classification;
import corp.acme.common.domain.Util;
import corp.acme.regulatory.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ClassificationService {

    // Did not really help with microservice stuff
    public static final long WAIT_BEFORE_INITIALIZING = 300;

    Logger logger = Logger.getLogger("ClassifiactionService");

    @Value("classpath:static/classifications.csv")
    Resource resourceFile;

    //Sadly failed
//    @Autowired
//    private DiscoveryClient discoveryClient;

    @Autowired
    private CategoryService categoryService;


    /* THIS SHOULD BE IN SOME KIND OF DATABASE, THIS ONE MIGHT BE A RELATIONAL ONE */
    Map<String, Classification> byId = new HashMap<>();
    Map<String, Classification> byName = new HashMap<>();
    List<Classification> alphabetical = new ArrayList<>();

    public Classification bySubstanceName(String name) {
        return this.byName.get(name);
    }

    public List<Classification> bySubstanceNames(List<String> names) {
        return names.stream().map(n -> bySubstanceName(n)).collect(Collectors.toList());
    }

    public Classification byId(String id) {
        return this.byId.get(id);
    }

    public List<Classification> all() {
        return this.alphabetical;
    }


    @PostConstruct
    private void init() throws IOException {
        // I know.. :(, but need to wait for discovery and catgoeries, these would be separate micro services
        try {
            Thread.sleep(WAIT_BEFORE_INITIALIZING );
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        this.importFromFile();
    }


    /* From here stateful oldskool import */
    private void importFromFile() throws IOException {
        try {
            final Integer counter = new Integer(0);
            Arrays.asList(Util.asString(resourceFile).split("\r\n")).stream().map(l ->
                l.split(";")
            ).collect(Collectors.toList()).forEach(pair -> {
                Classification classification = new Classification();
                classification.setId(Util.createUUID());
                classification.setName(pair[0]);
                classification.setCategory(fetchCategory(pair[1]));
                this.byId.put(classification.getId(), classification);
                this.byName.put(classification.getName(), classification);
                this.alphabetical.add(classification);
            });

            this.alphabetical.sort((a, b)-> a.getName().compareTo(b.getName()));
            this.logger.info(String.format("Imported classifications"));
        } catch (Exception e) {
            logger.severe("Could not load or parse the classifiactions csv!");
            logger.throwing("ClassificationService", "importFromFile", e);
            e.printStackTrace();
            throw e;
        }
    }

    private Category fetchCategory(String name) {
        return this.categoryService.getByName(name);

        /** This was planned to work as a microservice "self" call, but it failed, sometimes it found the service
         *  sometimes not, and connection to service call was always refused
         *
            this.discoveryClient.getServices().forEach(s -> logger.info(String.format("Found Service %s", s)));
            URI uri = this.discoveryClient.getInstances("REGULATORY").get(0).getUri();
            // Hm, do I really have to do this manually?
            WebClient.RequestHeadersSpec call = ServiceCall.buildDefaultCall(uri, "byName", name);
            //omg, blocking, but this is just at startup
            logger.info(call.toString());
            return call.retrieve().toEntity(Category.class).block().getBody();

         */
    }


}
