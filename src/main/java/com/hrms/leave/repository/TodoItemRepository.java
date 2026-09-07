package com.hrms.leave.repository;

import com.hrms.leave.entity.*;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TodoItemRepository extends JpaRepository<TodoItem, UUID> {
}
