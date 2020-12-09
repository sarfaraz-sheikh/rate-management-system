package com.dpworld.rms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.dpworld.rms.entity.Rate;
import com.dpworld.rms.model.RateBean;
import com.dpworld.rms.model.RateSearchBean;
import com.dpworld.rms.model.SurchargeBean;
import com.dpworld.rms.repository.RateRepository;
import com.dpworld.rms.service.RateService;


@ExtendWith(MockitoExtension.class)
public class RateServiceTests {

	@Mock
	private RateRepository rateRepository;

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private RateService rateService;

	@Test
	void testFetchRateServbice() throws Exception {

		ReflectionTestUtils.setField(rateService, "surchargeUrl", "https://surcharge.free.beeceptor.com/surcharge");

		Rate rate = new Rate();
		rate.setRateId(1L);
		rate.setAmount(1000);
		rate.setRateEffectiveDate(LocalDateTime.now());
		rate.setRateDescription("Premium Rates");

		when(rateRepository.findByRateId(1L)).thenReturn(rate);

		SurchargeBean surcharge = new SurchargeBean();
		surcharge.setSurchargeRate(1000);
		surcharge.setSurchargeDescription("Charge");
		ResponseEntity<SurchargeBean> surchargeBeanEntity = new ResponseEntity<SurchargeBean>(surcharge, HttpStatus.OK);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<SurchargeBean> surchargeEntity = new HttpEntity<SurchargeBean>(headers);
		Mockito.when(restTemplate.exchange("https://surcharge.free.beeceptor.com/surcharge", HttpMethod.GET, surchargeEntity, SurchargeBean.class)).thenReturn(surchargeBeanEntity);

		RateSearchBean rateSearchBean = rateService.fetchRate(1L);
		assertEquals(1000, rateSearchBean.getRate().getAmount());

	}

	@Test
	void testAddRate() throws Exception {

		ReflectionTestUtils.setField(rateService, "surchargeUrl", "https://surcharge.free.beeceptor.com/surcharge");

		Rate rate = new Rate();
		rate.setRateId(555L);
		rate.setAmount(5000);
		rate.setRateEffectiveDate(LocalDateTime.now());
		rate.setRateDescription("Premium Rates");

		when(rateRepository.save(Mockito.any(Rate.class))).thenAnswer(i -> i.getArguments()[0]);

		RateBean rateBean = new RateBean();
		rateBean.setAmount(5000);
		rateBean.setRateEffectiveDate(LocalDateTime.now());
		rateBean.setRateDescription("Premium Rates");

		rateBean = rateService.addRate(rateBean);
		assertEquals(5000, rateBean.getAmount());
	}

	@Test
	void testUpdateRate() throws Exception {

		Rate rate = new Rate();
		rate.setRateId(1L);
		rate.setAmount(7000);
		rate.setRateEffectiveDate(LocalDateTime.now());
		rate.setRateDescription("Premium Rates");

		when(rateRepository.save(Mockito.any(Rate.class))).thenAnswer(i -> i.getArguments()[0]);

		RateBean rateBean = new RateBean();
		rateBean.setAmount(7000);
		rateBean.setRateEffectiveDate(LocalDateTime.now());
		rateBean.setRateDescription("Premium Rates");

		rateBean = rateService.addRate(rateBean);
		assertEquals(7000, rateBean.getAmount());
	}

	@Test
	void testDeleteRate() throws Exception {

		Rate rate = new Rate();
		rate.setRateId(1L);

		when(rateRepository.findByRateId(1L)).thenReturn(rate);
		rateService.deleteRate(1L);
		Mockito.verify(rateRepository, times(1)).delete(rate);
	}
}
