package com.solo.bulletinboard2.posting.repository;

import com.solo.bulletinboard2.posting.entity.Posting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostingRepository extends JpaRepository<Posting, Long> {
}
