package com.cognizant.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.cognizant.model.MemberPolicy;
import com.cognizant.repo.MemberRepo;
import com.cognizant.repo.MembersPolicyRepo;
import com.cognizant.service.MembersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private MembersService membersService;

	@MockBean
	private MemberRepo memberRepo;

	@MockBean
	private MembersPolicyRepo membersPolicyRepo;

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

//	@BeforeEach
//	void setUp() throws Exception {
//	}
//
//	@Test
//	final void testGetMemberdetails() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testViewBill() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testSubmitClaim() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testGetClaimStatus() {
//		fail("Not yet implemented"); // TODO
//	}

}
