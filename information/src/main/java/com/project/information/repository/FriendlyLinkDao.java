package com.project.information.repository;


import com.project.information.domain.FriendlyLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FriendlyLinkDao extends JpaRepository<FriendlyLink, String>, JpaSpecificationExecutor<FriendlyLink> {


}
