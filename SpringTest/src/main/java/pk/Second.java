package pk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Second {
    private String value;

    @Autowired private First first;

    @PostConstruct
    public void init() {
        this.value = "second";
        System.out.println(first + " : " + first.getValue());
    }

    public String getValue() {
        return value;
    }
}
