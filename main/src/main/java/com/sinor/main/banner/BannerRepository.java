package com.sinor.main.banner;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinor.main.banner.model.Banner;

public interface BannerRepository extends JpaRepository<Banner, Long> {
}
