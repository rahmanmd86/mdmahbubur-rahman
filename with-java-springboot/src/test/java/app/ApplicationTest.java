package app;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import net.minidev.json.JSONObject;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetByIDShouldReturnTransaction() throws Exception {
        this.mockMvc.perform(get("/transactionservice/transaction/10")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("cars"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.parentID").isEmpty()).andDo(print());
    }

    @Test
    public void testPutByIDShouldReturnNoContent() throws Exception {
        Map<String, Object> payload = new HashMap<>();
        payload.put("amount", 5000);
        payload.put("type", "cars");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/transactionservice/transaction/10")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .content(new JSONObject(payload).toJSONString());

        this.mockMvc.perform(request)
        .andExpect(status().isNoContent())
        .andDo(print());
    }

    @Test
    public void testPutByIDShouldUpdateTransaction() throws Exception {
        Map<String, Object> payload = new HashMap<>();
        payload.put("amount", 5000);
        payload.put("type", "cars");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/transactionservice/transaction/10")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .content(new JSONObject(payload).toJSONString());

        this.mockMvc.perform(request)
            .andExpect(status().isNoContent())
            .andDo(print());

        this.mockMvc.perform(get("/transactionservice/transaction/10")).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(5000))
            .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("cars"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.parentID").isEmpty())
            .andDo(print());
    }

    @Test
    public void testGetSumByIDShouldReturnSum() throws Exception {
        Map<String, Object> payload = new HashMap<>();
        payload.put("amount", 5000);
        payload.put("type", "cars");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/transactionservice/transaction/10")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .content(new JSONObject(payload).toJSONString());
        
        this.mockMvc.perform(request)
            .andExpect(status().isNoContent())
            .andDo(print());

        payload.clear();
        payload.put("amount", 10000);
        payload.put("type", "shopping");
        payload.put("parentID", 10);

        request = MockMvcRequestBuilders.put("/transactionservice/transaction/11")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .content(new JSONObject(payload).toJSONString());

        this.mockMvc.perform(request)
            .andExpect(status().isNoContent())
            .andDo(print());

        this.mockMvc.perform(get("/transactionservice/sum/10"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.jsonPath("$.sum").value(15000))
            .andDo(print());
    }


}