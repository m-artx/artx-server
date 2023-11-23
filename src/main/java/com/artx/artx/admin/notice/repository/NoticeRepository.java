package com.artx.artx.admin.notice.repository;

import com.artx.artx.admin.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
