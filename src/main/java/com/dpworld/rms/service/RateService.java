package com.dpworld.rms.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.dpworld.rms.entity.Rate;
import com.dpworld.rms.exception.InvalidDataException;
import com.dpworld.rms.exception.ResourceNotFoundException;
import com.dpworld.rms.model.RateBean;
import com.dpworld.rms.model.RateSearchBean;
import com.dpworld.rms.model.SurchargeBean;
import com.dpworld.rms.repository.RateRepository;

@Service
public class RateService {

	@Autowired
	private RateRepository rateRepository;

	@Autowired
	RestTemplate restTemplate;

	@Value("${params.surcharge.fee.url}")
	private String surchargeUrl;

	Logger logger = LoggerFactory.getLogger(RateService.class);

	/**
	 * This API fetch/searches the Rate by its rateId
	 * 
	 * @param rateId
	 * @return
	 */
	public RateSearchBean fetchRate(Long rateId) {

		Rate rate = this.getRate(rateId);
		RateBean rateBean = this.populateRateBean(rate);
		SurchargeBean surcharge = this.fetchSurcharge();

		RateSearchBean rateSearchBean = new RateSearchBean();
		rateSearchBean.setRate(rateBean);
		rateSearchBean.setSurcharge(surcharge);
		return rateSearchBean;
	}

	/**
	 * This API fetches all the rates stored in the rms
	 * 
	 * @return
	 */
	public List<RateBean> findAll() {

		List<Rate> rates = this.rateRepository.findAll();
		if (CollectionUtils.isEmpty(rates)) {
			logger.error("No rates found");
			throw new ResourceNotFoundException();
		}

		return rates.stream().map(x -> this.populateRateBean(x)).collect(Collectors.toList());
	}

	/**
	 * This API adds rate
	 * 
	 * @param rateBean
	 * @return
	 */
	public RateBean addRate(RateBean rateBean) {

		this.validateRateBean(rateBean);

		Rate rate = new Rate();
		BeanUtils.copyProperties(rateBean, rate);
		rate = this.rateRepository.save(rate);
		BeanUtils.copyProperties(rate, rateBean);
		return rateBean;
	}

	/**
	 * This API updates rate
	 * 
	 * @param rateBean
	 * @return
	 */
	public RateBean updateRate(RateBean rateBean) {

		if (rateBean.getRateId() == null) {
			throw new InvalidDataException("rate Id is mandatory");
		}

		this.validateRateBean(rateBean);

		Rate rate = this.getRate(rateBean.getRateId());
		BeanUtils.copyProperties(rateBean, rate);
		rate = this.rateRepository.save(rate);
		BeanUtils.copyProperties(rate, rateBean);
		return rateBean;
	}

	/**
	 * This API deletes a rate by rateId
	 * 
	 * @param rateId
	 */
	public void deleteRate(Long rateId) {
		Rate rate = this.getRate(rateId);
		this.rateRepository.delete(rate);
	}

	private RateBean populateRateBean(Rate rate) {

		RateBean rateBean = new RateBean();
		BeanUtils.copyProperties(rate, rateBean);
		return rateBean;
	}

	private Rate getRate(Long rateId) {

		Rate rate = this.rateRepository.findByRateId(rateId);
		if (rate == null) {
			logger.error("No rate found with id: " + rateId);
			throw new ResourceNotFoundException();
		}
		return rate;
	}

	/**
	 * This API fetches the Surcharge/VAT using RestTemplate
	 * 
	 * @return SurchargeBean
	 */
	private SurchargeBean fetchSurcharge() {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<SurchargeBean> surchargeEntity = new HttpEntity<SurchargeBean>(headers);
		SurchargeBean surcharge = this.restTemplate.exchange(surchargeUrl, HttpMethod.GET, surchargeEntity, SurchargeBean.class).getBody();
		return surcharge;
	}

	private void validateRateBean(RateBean rateBean) {

		if (rateBean.getAmount() < 1) {
			throw new InvalidDataException("Amount cannot be less than 1");
		}
	}
}
