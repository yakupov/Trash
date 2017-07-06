import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pk.First;
import pk.Second;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Config.class})
public class Test {
    @Autowired
    First first;

    @Autowired
    Second second;

    @org.junit.Test
    public void test() throws Exception {
    }
}
