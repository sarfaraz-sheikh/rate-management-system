package com.dpworld.rms.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

public class RateBean {

	private Long rateId;

	private String rateDescription;

	@NonNull
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime rateEffectiveDate;

	@NonNull
	private Integer amount;

	public Long getRateId() {
		return rateId;
	}

	public void setRateId(Long rateId) {
		this.rateId = rateId;
	}

	public String getRateDescription() {
		return rateDescription;
	}

	public void setRateDescription(String rateDescription) {
		this.rateDescription = rateDescription;
	}

	public LocalDateTime getRateEffectiveDate() {
		return rateEffectiveDate;
	}

	public void setRateEffectiveDate(LocalDateTime rateEffectiveDate) {
		this.rateEffectiveDate = rateEffectiveDate;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

}
