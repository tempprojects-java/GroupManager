package com.gmail.gak.artem.backend.repository;

import com.gmail.gak.artem.backend.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Page<Group> findBy(Pageable pageable);
}
