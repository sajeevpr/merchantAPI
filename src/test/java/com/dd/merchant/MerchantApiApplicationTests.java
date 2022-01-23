package com.dd.merchant;

import com.dd.merchant.model.ui.Response;
import com.dd.merchant.model.ui.UIRequest;
import com.dd.merchant.model.ui.UIResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = MerchantApiApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
		locations = "classpath:application.properties")
class MerchantApiApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	void updateCustomerIT() throws Exception {
		final UIRequest request = UIRequest.builder().rawPhoneNumbers("(Home)415-415-4155 (Cell) 415-123-4567").build();
		ObjectMapper mapper = new ObjectMapper();
		final String requestBody = mapper.writeValueAsString(request);
		final List<Response> phoneNumberList = Arrays.asList(
				Response.builder().id("4154154155-home").phoneNumber("4154154155").phoneType("Home").occurrences(BigInteger.ONE).build(),
				Response.builder().id("4151234567-cell").phoneNumber("4151234567").phoneType("Cell").occurrences(BigInteger.ONE).build()
		);
		final UIResponse response = UIResponse.builder().results(phoneNumberList).build();

		ResultActions result = mvc.perform(MockMvcRequestBuilders.post("/phone-numbers")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestBody))
				.andExpect(status().isOk());

		final String actualOutput = result.andReturn().getResponse().getContentAsString().trim();
		UIResponse actualRes = mapper.readValue(actualOutput, UIResponse.class);

		Assert.assertEquals(response.getResults().get(0).getOccurrences(), actualRes.getResults().get(0).getOccurrences());
	}

}
