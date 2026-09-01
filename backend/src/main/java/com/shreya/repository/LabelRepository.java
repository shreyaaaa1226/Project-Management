package com.shreya.repository;

import com.shreya.model.Label;
import com.shreya.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LabelRepository extends JpaRepository<Label, Long> {
    Optional<Label> findByName(String name);
    List<Label> findByCreator(User creator);
}
