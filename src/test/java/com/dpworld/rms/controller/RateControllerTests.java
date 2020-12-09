package com.dpworld.rms.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class RateControllerTests {

	@Autowired
	private MockMvc mockMvc;

	private final static String TEST_USER_ID = "user-id-123";

	@Test
	void testFetchAllRates() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/v1/rms/rates").with(user(TEST_USER_ID)).with(csrf()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.length()", greaterThan(1)));
	}

	@Test
	void testAddRate() throws Exception {

		String rateJson = "{\r\n" + "    \"rateDescription\": \"Premium Rates\",\r\n" + "    \"rateEffectiveDate\": \"2020-12-19T14:00:00\",\r\n" + "    \"amount\": 100\r\n" + "}";

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/rms/rate").with(user(TEST_USER_ID)).with(csrf()).content(rateJson).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andExpect(jsonPath("$.amount", is(100)));
	}

	@Test
	void testInvalid500AddRate() throws Exception {

		String rateJson = "{\r\n" + "    \"rateDescription\": \"Premium Rates\",\r\n" + "    \"rateEffectiveDate\": \"20201-12-19T14:00:00\",\r\n" + "    \"amount\": 0\r\n" + "}";

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/rms/rate").with(user(TEST_USER_ID)).with(csrf()).content(rateJson).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().is5xxServerError()).andExpect(jsonPath("$", is("Internal server error. Please contact admin")));
	}

	@Test
	void testInvalidAmountAddRate() throws Exception {

		String rateJson = "{\r\n" + "    \"rateDescription\": \"Premium Rates\",\r\n" + "    \"rateEffectiveDate\": \"2020-12-19T14:00:00\",\r\n" + "    \"amount\": 0\r\n" + "}";

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/rms/rate").with(user(TEST_USER_ID)).with(csrf()).content(rateJson).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andExpect(jsonPath("$", is("Amount cannot be less than 1")));
	}

	@Test
	void testAmountUpdateRate() throws Exception {

		String rateJson = "{\r\n" + "    \"rateId\": 1,\r\n" + "    \"rateDescription\": \"Threshold Rates\",\r\n" + "    \"rateEffectiveDate\": \"2020-12-19T14:00:00\",\r\n"
				+ "    \"amount\": 500\r\n" + "}";

		mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/rms/rate").with(user(TEST_USER_ID)).with(csrf()).content(rateJson).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andExpect(jsonPath("$.amount", is(500)));
	}

	@Test
	void testInvalid500UpdateRate() throws Exception {

		String rateJson = "{\r\n" + "    \"rateId\": 1,\r\n" + "    \"rateDescription\": \"Threshold Rates\",\r\n" + "    \"rateEffectiveDate\": \"20201-12-19T14:00:00\",\r\n"
				+ "    \"amount\": 500\r\n" + "}";

		mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/rms/rate").with(user(TEST_USER_ID)).with(csrf()).content(rateJson).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().is5xxServerError()).andExpect(jsonPath("$", is("Internal server error. Please contact admin")));
	}

	@Test
	void testFetchRate() throws Exception {

		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/v1/rms/rate/1").with(user(TEST_USER_ID)).with(csrf()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.rate.amount", is(500))).andExpect(jsonPath("$.rate.rateId", is(1)));
	}

	@Test
	void testInvalid400FetchRate() throws Exception {

		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/v1/rms/rate/15551").with(user(TEST_USER_ID)).with(csrf()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andExpect(jsonPath("$", is("RateId not found in RMS")));
	}
	
	@Test
	void testDeleteRate() throws Exception {

		mockMvc.perform(
				MockMvcRequestBuilders.delete("/api/v1/rms/rate/2").with(user(TEST_USER_ID)).with(csrf()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", is("Deleted")));
	}
	
	@Test
	void testInvalid400DeleteRate() throws Exception {

		mockMvc.perform(
				MockMvcRequestBuilders.delete("/api/v1/rms/rate/15551").with(user(TEST_USER_ID)).with(csrf()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andExpect(jsonPath("$", is("RateId not found in RMS")));
	}
}
