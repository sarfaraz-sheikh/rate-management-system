package com.dpworld.rms.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dpworld.rms.exception.InvalidDataException;
import com.dpworld.rms.exception.ResourceNotFoundException;
import com.dpworld.rms.model.RateBean;
import com.dpworld.rms.model.RateSearchBean;
import com.dpworld.rms.service.RateService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController
@RequestMapping(path = "/api/v1/rms")
public class RateController {

	@Autowired
	private RateService rateService;

	Logger logger = LoggerFactory.getLogger(RateController.class);

	@HystrixCommand(fallbackMethod = "fallback_getRate", ignoreExceptions = { ResourceNotFoundException.class }, commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000") })
	@GetMapping(value = "/rate/{rateId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> getRate(@PathVariable Long rateId) {

		logger.info("fetching the Rate with id: " + rateId);

		RateSearchBean rate = this.rateService.fetchRate(rateId);
		return new ResponseEntity<>(rate, HttpStatus.OK);
	}

	@HystrixCommand(fallbackMethod = "fallback_getRates", ignoreExceptions = { ResourceNotFoundException.class }, commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000") })
	@GetMapping(value = "/rates", produces = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> getRates() {

		logger.info("Fetching all the Rates from the RMS");

		List<RateBean> rates = this.rateService.findAll();
		logger.info("Total Rate entries found: " + rates.size());

		return new ResponseEntity<>(rates, HttpStatus.OK);
	}

	@HystrixCommand(fallbackMethod = "fallback_addUpdateRate", ignoreExceptions = { ResourceNotFoundException.class, InvalidDataException.class }, commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000") })
	@PostMapping(path = "/rate", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> addRate(@RequestBody RateBean rateBean) {

		logger.info("Adding the Rate in the RMS");

		rateBean = this.rateService.addRate(rateBean);
		return new ResponseEntity<>(rateBean, HttpStatus.CREATED);
	}

	@HystrixCommand(fallbackMethod = "fallback_addUpdateRate", ignoreExceptions = { ResourceNotFoundException.class, InvalidDataException.class }, commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000") })
	@PutMapping(path = "/rate", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> updateRate(@RequestBody RateBean rateBean) {

		logger.info("Updating the Rate with Id: " + rateBean.getRateId());

		rateBean = this.rateService.updateRate(rateBean);
		return new ResponseEntity<>(rateBean, HttpStatus.CREATED);
	}

	@HystrixCommand(fallbackMethod = "fallback_deleteRate", ignoreExceptions = { ResourceNotFoundException.class }, commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000") })
	@DeleteMapping(path = "/rate/{rateId}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<?> deleteRate(@PathVariable Long rateId) {

		logger.info("Deleting the Rate with Id: " + rateId);

		this.rateService.deleteRate(rateId);
		return new ResponseEntity<>("Deleted", HttpStatus.OK);
	}

	@SuppressWarnings("unused")
	private ResponseEntity<?> fallback_getRate(Long rateId) {

		logger.error("Hystrix -> fallback_getRate with Rate Id: " + rateId);

		RateBean rateBean = new RateBean();
		rateBean.setAmount(0);
		rateBean.setRateDescription("Hystrix Fallback");
		rateBean.setRateEffectiveDate(LocalDateTime.now());
		rateBean.setRateId(rateId);
		return ResponseEntity.ok(rateBean);
	}

	@SuppressWarnings("unused")
	private ResponseEntity<?> fallback_getRates() {

		logger.error("Hystrix -> fallback_getRates");

		List<RateBean> rates = new ArrayList<RateBean>();
		RateBean rateBean = new RateBean();
		rateBean.setAmount(0);
		rateBean.setRateDescription("Hystrix Fallback");
		rateBean.setRateEffectiveDate(LocalDateTime.now());
		rateBean.setRateId(0L);
		rates.add(rateBean);
		return ResponseEntity.ok(rates);
	}

	@SuppressWarnings("unused")
	private ResponseEntity<?> fallback_addUpdateRate(RateBean rateBean) {

		logger.error("Hystrix -> fallback_addUpdateRate with Rate Id: " + rateBean.getRateId());

		rateBean.setAmount(0);
		rateBean.setRateDescription("Hystrix Fallback");
		rateBean.setRateEffectiveDate(LocalDateTime.now());
		rateBean.setRateId(0L);
		return ResponseEntity.ok(rateBean);
	}

	@SuppressWarnings("unused")
	private ResponseEntity<?> fallback_deleteRate(Long rateId) {

		logger.error("Hystrix -> fallback_deleteRate with Rate Id: " + rateId);
		return ResponseEntity.ok("Not deleted - Hystrix Fallback");
	}
}
