package com.personal.jobhive.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.personal.jobhive.entities.Job;
import com.personal.jobhive.entities.User;

@Repository
public interface JobRepo extends JpaRepository<Job, String>{
    Page<Job> findByUser(User user, Pageable pageable);

    @Query("SELECT c FROM Job c WHERE c.user.id = :userId")
    List<Job> findByUserId(@Param("userId") String userId);

    Page<Job> findByUserAndCurrentStatusContaining(User user, String currentStatus, Pageable pageable);
    
    Page<Job> findByUserAndCompanyContaining(User user, String companykeyword, Pageable pageable);

    Page<Job> findByUserAndJobRoleContaining(User user, String rolekeyword, Pageable pageable);

    Page<Job> findByUserAndLocationContaining(User user, String locationkeyword, Pageable pageable);
}
