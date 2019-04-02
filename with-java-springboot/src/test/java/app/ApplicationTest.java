package app;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import net.minidev.json.JSONObject;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetByIDShouldReturnTransaction() throws Exception {
        this.mockMvc.perform(get("/transactionservice/transaction/10"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.amount").value(0))
            .andExpect(jsonPath("$.type").value("cars"))
            .andExpect(jsonPath("$.parentID").isEmpty()).andDo(print());
    }

    @Test
    public void testPutByIDShouldReturnNoContent() throws Exception {
        Map<String, Object> payload = new HashMap<>();
        payload.put("amount", 5000);
        payload.put("type", "cars");

        MockHttpServletRequestBuilder request = put("/transactionservice/transaction/10")
            .contentType(APPLICATION_JSON_UTF8)
            .accept(APPLICATION_JSON_UTF8)
            .content(new JSONObject(payload).toJSONString());

        this.mockMvc.perform(request)
            .andExpect(status().isNoContent())
            .andDo(print());
    }

    @Test
    public void testPutByIDWithoutParentIDShouldUpdateTransaction() throws Exception {
        Map<String, Object> payload = new HashMap<>();
        payload.put("amount", 5000);
        payload.put("type", "cars");

        MockHttpServletRequestBuilder request = put("/transactionservice/transaction/10")
            .contentType(APPLICATION_JSON_UTF8)
            .accept(APPLICATION_JSON_UTF8)
            .content(new JSONObject(payload).toJSONString());

        this.mockMvc.perform(request)
            .andExpect(status().isNoContent())
            .andDo(print());

        this.mockMvc.perform(get("/transactionservice/transaction/10"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.amount").value(5000))
            .andExpect(jsonPath("$.type").value("cars"))
            .andExpect(jsonPath("$.parentID").isEmpty())
            .andDo(print());
    }

    @Test
    public void testPutByIDWithParentIDShouldUpdateTransaction() throws Exception {
        Map<String, Object> payload = new HashMap<>();
        payload.put("amount", 10000);
        payload.put("type", "cars");
        payload.put("parentID", 10);

        MockHttpServletRequestBuilder request = put("/transactionservice/transaction/11")
            .contentType(APPLICATION_JSON_UTF8)
            .accept(APPLICATION_JSON_UTF8)
            .content(new JSONObject(payload).toJSONString());

        this.mockMvc.perform(request)
            .andExpect(status().isNoContent())
            .andDo(print());

        this.mockMvc.perform(get("/transactionservice/transaction/11"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.amount").value(10000))
            .andExpect(jsonPath("$.type").value("cars"))
            .andExpect(jsonPath("$.parentID").value(10))
            .andDo(print());
    }

    @Test
    public void testGetByIDReturns404ForInvalidTransactionID() throws Exception {
        Map<String, Object> payload = new HashMap<>();
        payload.put("amount", 5000);
        payload.put("type", "cars");

        MockHttpServletRequestBuilder request = put("/transactionservice/transaction/12")
            .contentType(APPLICATION_JSON_UTF8)
            .accept(APPLICATION_JSON_UTF8)
            .content(new JSONObject(payload).toJSONString());

        this.mockMvc.perform(request)
            .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("$.error").value("Transaction with id 12 not found."))
            .andDo(print());
    }

    @Test
    public void testGetSumByIDShouldReturnSumAsZeroForInvalidTransactionID() throws Exception {
        this.mockMvc.perform(get("/transactionservice/sum/12"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.sum").value(0))
            .andDo(print());
    }

    @Test
    public void testGetSumByIDShouldReturnSumForSingleTransaction() throws Exception {
        Map<String, Object> payload = new HashMap<>();
        payload.put("amount", 5000);
        payload.put("type", "cars");

        MockHttpServletRequestBuilder request = put("/transactionservice/transaction/10")
            .contentType(APPLICATION_JSON_UTF8)
            .accept(APPLICATION_JSON_UTF8)
            .content(new JSONObject(payload).toJSONString());

        this.mockMvc.perform(request)
            .andExpect(status().isNoContent())
            .andDo(print());

        this.mockMvc.perform(get("/transactionservice/sum/10"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.sum").value(5000))
            .andDo(print());
    }

    @Test
    public void testGetTypesByValidTypeShouldReturnTransactionIDs() throws Exception {
        this.mockMvc.perform(get("/transactionservice/types/cars"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(content().string("[9,10]"))
            .andDo(print());
    }

    @Test
    public void testGetTypesByInvalidTypeShouldNotReturnTransactionIDs() throws Exception {
        this.mockMvc.perform(get("/transactionservice/types/foo"))
            .andExpect(status().is4xxClientError())
            .andDo(print());
    }

    @Test
    public void testGetSumByIDShouldReturnSumForMultipleTransaction() throws Exception {
        Map<String, Object> payload = new HashMap<>();
        payload.put("amount", 5000);
        payload.put("type", "cars");

        MockHttpServletRequestBuilder request = put("/transactionservice/transaction/10")
            .contentType(APPLICATION_JSON_UTF8)
            .accept(APPLICATION_JSON_UTF8)
            .content(new JSONObject(payload).toJSONString());
        
        this.mockMvc.perform(request)
            .andExpect(status().isNoContent())
            .andDo(print());

        payload.clear();
        payload.put("amount", 10000);
        payload.put("type", "shopping");
        payload.put("parentID", 10);

        request = put("/transactionservice/transaction/11")
            .contentType(APPLICATION_JSON_UTF8)
            .accept(APPLICATION_JSON_UTF8)
            .content(new JSONObject(payload).toJSONString());

        this.mockMvc.perform(request)
            .andExpect(status().isNoContent())
            .andDo(print());

        this.mockMvc.perform(get("/transactionservice/sum/10"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.sum").value(15000))
            .andDo(print());
    }

    // TODO: This test passes as the route for POST is not implemented
    @Test
    public void testPostRequestShouldReturnContent() throws Exception {
        Map<String, Object> payload = new HashMap<>();
        payload.put("amount", 5000);
        payload.put("type", "cars");

        MockHttpServletRequestBuilder request = post("/transactionservice/transaction")
            .contentType(APPLICATION_JSON_UTF8)
            .accept(APPLICATION_JSON_UTF8)
            .content(new JSONObject(payload).toJSONString());

        this.mockMvc.perform(request)
            .andExpect(status().is4xxClientError())
            .andDo(print());
    }

    @After
    public void tearDown() throws Exception {
        Map<String, Object> payload = new HashMap<>();
        payload.put("amount", 0);
        payload.put("type", "cars");

        MockHttpServletRequestBuilder request = put("/transactionservice/transaction/10")
            .contentType(APPLICATION_JSON_UTF8)
            .accept(APPLICATION_JSON_UTF8)
            .content(new JSONObject(payload).toJSONString());

        this.mockMvc.perform(request)
            .andDo(print());
    }


}