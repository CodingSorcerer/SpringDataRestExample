package net.lonewolfcode.examples.datarest.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import net.lonewolfcode.examples.datarest.entities.Employee;

import java.util.ArrayList;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class returnData {

    private Map<String,ArrayList<Employee>> _embedded;

    public Map<String,ArrayList<Employee>> get_embedded() {
        return _embedded;
    }
}
