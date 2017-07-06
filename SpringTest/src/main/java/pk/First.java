package pk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class First {
    private String value;

    @Autowired private Second second;

    @PostConstruct
    public void init() {
        this.value = "first";
        System.out.println(second + " : " + second.getValue());
    }

    public String getValue() {
        return value;
    }
}
