package com.dpworld.rms.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.dpworld.rms.entity.Rate;

@DataJpaTest
public class RateRepositoryTests {

	@Autowired
	private RateRepository rateRepository;

	@Test
	void testFindByRateId() {

		Rate rate = new Rate();
		rate.setAmount(7000);
		rate.setRateEffectiveDate(LocalDateTime.now());
		rate.setRateDescription("Premium Rates");

		rateRepository.save(rate);
		assertNotNull(rate.getRateId());

		Rate savedRate = rateRepository.findByRateId(rate.getRateId());
		assertEquals(savedRate.getAmount(), 7000);
	}

	@Test
	void testNullFindByRateId() {

		Rate savedRate = rateRepository.findByRateId(555L);
		assertNull(savedRate);
	}

	@Test
	void testFindAllRates() {

		Rate rate = new Rate();
		rate.setAmount(7000);
		rate.setRateEffectiveDate(LocalDateTime.now());
		rate.setRateDescription("Premium Rates");

		rateRepository.save(rate);
		assertNotNull(rate.getRateId());

		List<Rate> rates = rateRepository.findAll();
		assertTrue(rates.size() > 0);
	}

	@Test
	void testSaveRate() {

		Rate rate = new Rate();
		rate.setAmount(1000);
		rate.setRateEffectiveDate(LocalDateTime.now());
		rate.setRateDescription("Premium Rates");

		rateRepository.save(rate);
		assertNotNull(rate.getRateId());
	}

	@Test
	void testUpdateRate() {

		Rate rate = new Rate();
		rate.setAmount(7000);
		rate.setRateEffectiveDate(LocalDateTime.now());
		rate.setRateDescription("Premium Rates");

		rateRepository.save(rate);
		assertNotNull(rate.getRateId());

		Rate savedRate = rateRepository.findByRateId(rate.getRateId());
		assertEquals(savedRate.getAmount(), 7000);

		savedRate.setAmount(5000);
		savedRate = rateRepository.save(savedRate);
		assertEquals(savedRate.getAmount(), 5000);
	}

	@Test
	void testDeleteRate() {

		Rate rate = new Rate();
		rate.setAmount(1000);
		rate.setRateEffectiveDate(LocalDateTime.now());
		rate.setRateDescription("Premium Rates");

		rateRepository.save(rate);
		assertNotNull(rate.getRateId());

		rateRepository.delete(rate);
		Rate savedRate = rateRepository.findByRateId(rate.getRateId());
		assertNull(savedRate);
	}
}
