package com.example.tracingissue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureObservability
@AutoConfigureWireMock(port = 0)
class TracingIssueApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	void testTracing() throws Exception {
		stubFor(WireMock.get(urlPathEqualTo("/say-hello"))
				.withHeader("X-B3-TraceId", equalTo("80f198ee56343ba864fe8b2a57d3eff7"))
				.willReturn(ok("Good morning!")));

		mvc.perform(get("/greetings")
						.header("b3", "80f198ee56343ba864fe8b2a57d3eff7-e457b5a2e4d86bd1-1-05e3ac9a4f6e3b90")
						.header("X-B3-TraceId", "80f198ee56343ba864fe8b2a57d3eff7")
						.header("X-B3-ParentSpanId", "05e3ac9a4f6e3b90")
						.header("X-B3-SpanId", "e457b5a2e4d86bd1")
						.header("X-B3-Sampled", "1")
				)
				.andExpect(status().isOk());
	}

}
