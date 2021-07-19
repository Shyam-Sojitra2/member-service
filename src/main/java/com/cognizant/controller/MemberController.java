package com.cognizant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.RestTemplate;

import com.cognizant.model.ClaimStatusResult;
import com.cognizant.model.MemberPolicy;
import com.cognizant.model.MemberSubmitClaim;
import com.cognizant.model.Members;
import com.cognizant.repo.MembersPolicyRepo;
import com.cognizant.service.MembersService;

@RestController
public class MemberController {

	@Autowired
	private MembersService membersService;

	@Autowired
	private MembersPolicyRepo membersPolicyRepo;

	@Autowired
	private RestTemplate restTemp;

	// Member Registration
	@PostMapping("/savememb")
	public Members getMemberdetails(@RequestBody Members members) {
		return this.membersService.savaMembers(members);

	}

//	@PutMapping("/getmember/{id}")
//	public MemberPolicy updateMemberPolicy(@RequestBody MemberPolicy memberPolicy,@PathVariable("id") long id ) throws Exception {
//		Members members = membersService.getMemberByid(id);
//		memberPolicy.setMemberid(members.getMemberId());
//		memberPolicy.setMemberPolicyId(members.getPolicyId());
//		return this.membersService.saveMemberPolicy(memberPolicy);	
//	}

	// viewBills (Input: Member_ID, Policy_ID | Output: Last Premium Paid Date,
	// Premium_Amount_Due, Details of Late Payment Charges if applicable, Due Date
	// etc.)(file)

//	@GetMapping("/viewbill/{membId}/{policyId}")
//	public List<MemberPolicy> viewBill(@PathVariable("membId") String membid,@PathVariable("policyId") String policyId ){
//		return this.membersPolicyRepo.viewbill(membid, policyId);
//		
//	}

	@GetMapping("/viewbill/{membId}/{policyId}")
	public List<MemberPolicy> viewBill(@PathVariable("membId") String membid, @PathVariable("policyId") String policyId,
			@RequestHeader("Authorization") String token) throws BadRequest {
		System.out.println(token);
		List<MemberPolicy> res = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", token);

			HttpEntity<Boolean> requestEntity = new HttpEntity<>(null, headers);
			ResponseEntity<String> result = restTemp.exchange("http://cms-authorization-service/api/auth/validate",
					HttpMethod.GET, requestEntity, String.class);
			System.out.println(result.getBody());
			if (result.getBody().equals("True")) {
				res = this.membersPolicyRepo.viewbill(membid, policyId);
			} else {
				res = null;
			}

		} catch (BadRequest e) {
			MemberPolicy invalid = new MemberPolicy();
			invalid.setLastPaymentInfo("Invalid Token");
			res.add(invalid);
			return res;
		}
		return res;

	}

	// Submit Claim (file)
	@PostMapping("/submitclaim")
	public MemberSubmitClaim submitClaim(@RequestBody MemberSubmitClaim memberSubmitClaim) {
		return this.membersService.saveClaim(memberSubmitClaim);

	}

	// get Claim Status (file)
	@GetMapping("/getClaimStatus/{claimId}/{policyId}/{memberId}")
	public ClaimStatusResult getClaimStatus(@PathVariable("claimId") long claimId,
			@PathVariable("policyId") long policyId, @PathVariable("memberId") long memberId) {
		ClaimStatusResult result = restTemp.getForObject(
				"http://cms-claim-service/getClaimStatus/" + claimId + "/" + policyId + "/" + memberId,
				ClaimStatusResult.class);
		return result;

	}

}
