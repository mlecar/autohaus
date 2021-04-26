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
create UNIQUE INDEX IX_dealer_id_code ON VEHICLES (dealer_id, code);

ALTER TABLE VEHICLES ADD CONSTRAINT UK_dealer_id_code UNIQUE USING INDEX IX_dealer_id_code;