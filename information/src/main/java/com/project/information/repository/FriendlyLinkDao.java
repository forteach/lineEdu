package com.project.information.repository;


import com.project.information.domain.FriendlyLink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FriendlyLinkDao extends JpaRepository<FriendlyLink, String>, JpaSpecificationExecutor<FriendlyLink> {


}
