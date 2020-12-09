DROP TABLE IF EXISTS rate;

CREATE TABLE rate (
  rate_id BIGINT AUTO_INCREMENT  PRIMARY KEY,
  rate_description VARCHAR(250) DEFAULT NULL,
  rate_effective_date datetime NOT NULL,
  amount INT NOT NULL
);

INSERT INTO rate (rate_description, rate_effective_date, amount) VALUES
  ('Base Rate', '2020-12-08 01:15:00', 5000),
  ('Premium Rate', '2020-12-09 01:15:00', 8000);