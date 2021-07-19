package com.cognizant.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.model.MemberSubmitClaim;

@Repository
public interface MemberSubmitClaimRepo extends JpaRepository<MemberSubmitClaim, Long>{

}
