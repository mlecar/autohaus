CREATE TABLE VEHICLES (
    vehicle_id BIGSERIAL PRIMARY KEY,
    dealer_id BIGINT NOT NULL,
    code VARCHAR NOT NULL,
    make VARCHAR NOT NULL,
    model VARCHAR NOT NULL,
    kw INT NOT NULL,
    year INT NOT NULL,
    color VARCHAR,
    price INT NOT NULL, -- initially using integer
    created_at TIMESTAMPTZ NOT NULL DEFAULT (now() at time zone 'utc'),
    updated_at TIMESTAMPTZ
);
create UNIQUE INDEX IDX_dealer_id_code ON VEHICLES (dealer_id, code);

CREATE INDEX IDX_dealer_id ON VEHICLES(dealer_id);
CREATE INDEX IDX_make ON VEHICLES(make);
CREATE INDEX IDX_model ON VEHICLES(model);
CREATE INDEX IDX_year ON VEHICLES(year);
CREATE INDEX IDX_color ON VEHICLES(color);

ALTER TABLE VEHICLES ADD CONSTRAINT UK_dealer_id_code UNIQUE USING INDEX IDX_dealer_id_code;