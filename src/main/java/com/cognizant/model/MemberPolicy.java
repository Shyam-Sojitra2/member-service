package com.cognizant.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "member_policy")
public class MemberPolicy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long memberPolicyId;

	@Column(name = "member_id")
	private String memberid;

	@Column(name = "policy_Id")
	private String policyId;

	@Column(name = "lastPremium_Date")
	private Date lastPremiumDate;

	@Column(name = "premium_AmtDue")
	private long premiumAmtDue;

	@Column(name = "lastPayment_Info")
	private String lastPaymentInfo;

	@Column(name = "due_Date")
	private Date dueDate;

}
