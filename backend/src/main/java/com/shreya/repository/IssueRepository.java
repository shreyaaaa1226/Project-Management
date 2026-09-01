package com.shreya.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shreya.model.Issue;

public interface IssueRepository extends JpaRepository<Issue, Long> {
	List<Issue> findByAssigneeId(Long assigneeId);
	
    @Query("SELECT i FROM Issue i " +
            "LEFT JOIN i.assignee a " +
            "WHERE (:title IS NULL OR LOWER(i.title) LIKE %:title%) " +
            "AND (:status IS NULL OR i.status = :status) " +
            "AND (:priority IS NULL OR i.priority = :priority) " +
            "AND (:assigneeId IS NULL OR a.id = :assigneeId)")
    List<Issue> searchIssues(
            @Param("title") String title,
            @Param("status") String status,
            @Param("priority") String priority,
            @Param("assigneeId") Long assigneeId
    );
    


    List<Issue> findByProjectId(Long projectId);


}
