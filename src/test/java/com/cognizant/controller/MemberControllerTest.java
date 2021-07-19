package com.cognizant.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.cognizant.model.MemberPolicy;
import com.cognizant.repo.MemberRepo;
//import com.cognizant.repo.MemberRepo;
import com.cognizant.repo.MembersPolicyRepo;
import com.cognizant.service.MembersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@WebMvcTest(controllers = MemberController.class)
class ClaimControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private MembersService membersService;

	@MockBean
	private MemberRepo MemberRepo;

	@MockBean
	private MembersPolicyRepo membersPolicyRepo;
	@MockBean
	private RestTemplate restTemp;

	@Test
	final void testSubmit() throws Exception {
		Date d = new Date(2000 - 12 - 12);
		Date d1 = new Date(2000 - 11 - 12);
		MemberPolicy memberPolicy = new MemberPolicy(1L, "1", "1", d, 2L, "done", d1);
		when(membersService.saveMemberPolicy(memberPolicy)).thenReturn(memberPolicy);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		String jsonString = mapper.writeValueAsString(memberPolicy);
		this.mockMvc.perform(post("/savememb").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andExpect(status().isOk());
	}

	private List<MemberPolicy> employees;
	@MockBean
	private MemberController mc;

	@Test
	public void testViewBill() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "yes");

		// HttpEntity<Boolean> requestEntity = new HttpEntity<>(null, headers);
		// ResponseEntity<String> result =
		// restTemp.exchange("http://cms-authorization-service/api/auth/validate",
		// HttpMethod.GET, requestEntity, String.class);
		// System.out.println(result.getBody());
		// System.out.println(mc);
		when(mc.viewBill("1", "1", "yes")).thenReturn(getTestData());
		mockMvc.perform(get("/viewbill/{membId}/{policyId}", "1", "1").header("Authorization", "yes")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	private List<MemberPolicy> getTestData() {
		MemberPolicy mp1 = new MemberPolicy();
		mp1.setMemberPolicyId(2);
		mp1.setMemberid("1");
		mp1.setPolicyId("1");
		mp1.setPremiumAmtDue(25000);
		mp1.setLastPaymentInfo("Paid");
		List<MemberPolicy> mp = new ArrayList<MemberPolicy>();
		mp.add(mp1);
		return mp;
	}

}

//package com.cognizant.controller;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.sql.Date;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.context.WebApplicationContext;
//
//import com.cognizant.model.MemberPolicy;
//import com.cognizant.repo.MemberRepo;
//import com.cognizant.repo.MembersPolicyRepo;
//import com.cognizant.service.MembersService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//
//@WebMvcTest(controllers = MemberController.class)
//class MemberControllerTest {
//
//	@Autowired
//	private MockMvc mockMvc;
//
//	@Autowired
//	private WebApplicationContext webApplicationContext;
//
//	@MockBean
//	private MembersService membersService;
//
//	@MockBean
//	private MemberRepo memberRepo;
//
//	@MockBean
//	private MembersPolicyRepo membersPolicyRepo;
//
//	@Test
//	final void testSubmit() throws Exception {
//		Date d = new Date(2000 - 12 - 12);
//		Date d1 = new Date(2000 - 11 - 12);
//		MemberPolicy memberPolicy = new MemberPolicy(1L, "1", "1", d, 2L, "done", d1);
//		when(membersService.saveMemberPolicy(memberPolicy)).thenReturn(memberPolicy);
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.registerModule(new JavaTimeModule());
//		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//		String jsonString = mapper.writeValueAsString(memberPolicy);
//		this.mockMvc.perform(post("/savememb").contentType(MediaType.APPLICATION_JSON).content(jsonString))
//				.andExpect(status().isOk());
//	}
//
////	@BeforeEach
////	void setUp() throws Exception {
////	}
////
////	@Test
////	final void testGetMemberdetails() {
////		fail("Not yet implemented"); // TODO
////	}
////
////	@Test
////	final void testViewBill() {
////		fail("Not yet implemented"); // TODO
////	}
////
////	@Test
////	final void testSubmitClaim() {
////		fail("Not yet implemented"); // TODO
////	}
////
////	@Test
////	final void testGetClaimStatus() {
////		fail("Not yet implemented"); // TODO
////	}
//
//}
