package com.sinor.main.gatheringcontent;


import com.sinor.main.gatheringcontent.model.GatheringContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatheringContentRepository extends JpaRepository<GatheringContent,Long> {
}
