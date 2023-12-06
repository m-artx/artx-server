package com.artx.artx.domain.admin.notice.repository;

import com.artx.artx.domain.admin.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
