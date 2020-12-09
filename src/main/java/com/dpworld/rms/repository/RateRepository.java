package com.dpworld.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dpworld.rms.entity.Rate;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

	Rate findByRateId(Long rateId);

	List<Rate> findByRateDescriptionContaining(String description);
}
