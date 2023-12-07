package com.sinor.main.gathering;


import org.springframework.data.jpa.repository.JpaRepository;

import com.sinor.main.gathering.model.Gathering;

public interface GatheringRepository extends JpaRepository<Gathering,Long> {
}
