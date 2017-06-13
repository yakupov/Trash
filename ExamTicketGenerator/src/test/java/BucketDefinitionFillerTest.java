import com.google.gson.reflect.TypeToken;
import org.junit.Ignore;
import org.junit.Test;
import ru.itmo.iyakupov.BucketDef;
import ru.itmo.iyakupov.ListStorage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BucketDefinitionFillerTest {
    @Test @Ignore
    public void generate() throws IOException {
        final ListStorage<BucketDef> storage = new ListStorage<>(new TypeToken<List<BucketDef>>(){});
        final List<BucketDef> buckets = new ArrayList<>();
        final int[] sizes = new int[] {6, 10, 7, 7, 6, 10, 9, 7};
        for (int i = 0; i < sizes.length; ++i) {
            buckets.add(new BucketDef(i, sizes[i]));
        }

        storage.setElements(buckets);
        storage.store(new File("bucketDef.json"));
    }
}
