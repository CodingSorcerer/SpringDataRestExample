package net.lonewolfcode.examples.datarest;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.lonewolfcode.examples.datarest.data.returnData;
import net.lonewolfcode.examples.datarest.entities.Employee;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class TestApplication {
    @Autowired
    private MockMvc requester;
    private final static String API_URL = "/employee";
    private final static String NOT_FOUND_ID = "55";
    private final static Employee KAREN = new Employee("1","Karen");
    private final static Employee BOB = new Employee("2","Bob");
    private final static Employee AZULA = new Employee("3","Azula");
    private final static Employee[] EXPECTED_EMPLOYEES = {KAREN,BOB,AZULA};
    private final static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void postOneItemAndGetById() throws Exception {
        requester.perform(post(API_URL).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(KAREN))).andExpect(status().isCreated());

        MvcResult result = requester.perform(get(API_URL+"/"+KAREN.getId()))
                .andExpect(status().isOk()).andReturn();

        System.out.println(result.getResponse().getContentAsString());

        Assert.assertEquals(KAREN,mapper.readValue(result.getResponse().getContentAsString(),Employee.class));
    }

    @Test
    public void getNotFound() throws Exception {
        Assert.assertTrue(requester.perform(get(API_URL+"/"+NOT_FOUND_ID))
                .andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void getAll() throws Exception {
        requester.perform(post(API_URL).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(KAREN))).andExpect(status().isCreated());
        requester.perform(post(API_URL).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(BOB))).andExpect(status().isCreated());
        requester.perform(post(API_URL).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(AZULA))).andExpect(status().isCreated());

        MvcResult result = requester.perform(get(API_URL)).andExpect(status().isOk()).andReturn();

        Assert.assertArrayEquals(EXPECTED_EMPLOYEES,mapper.readValue(result.getResponse().getContentAsString(),
                returnData.class).get_embedded().get("employees").toArray());
    }

    @Test
    public void deleteSuccess() throws Exception {
        requester.perform(post(API_URL).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(KAREN))).andExpect(status().isCreated());
        requester.perform(get(API_URL+"/"+KAREN.getId()))
                .andExpect(status().isOk());
        requester.perform(delete(API_URL+"/"+KAREN.getId())).andExpect(status().isNoContent());
        requester.perform(get(API_URL+"/"+KAREN.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteNotFound() throws Exception {
        requester.perform(delete(API_URL+"/"+NOT_FOUND_ID)).andExpect(status().isNotFound());
    }
}
