package com.dpworld.rms.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rate")
public class Rate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rate_id")
	private Long rateId;

	@Column(name = "rate_description")
	private String rateDescription;

	@Column(name = "rate_effective_date", nullable = false)
	private LocalDateTime rateEffectiveDate;

	@Column(name = "amount", nullable = false)
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
