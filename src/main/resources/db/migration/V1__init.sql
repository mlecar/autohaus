CREATE TABLE VEHICLES (
    vehicle_id BIGSERIAL PRIMARY KEY,
    dealer_id BIGINT NOT NULL,
    code VARCHAR NOT NULL,
    make VARCHAR NOT NULL,
    model VARCHAR NOT NULL,
    kw INT NOT NULL,
    year INT NOT NULL,
    color VARCHAR NOT NULL,
    price INT NOT NULL, -- initially using integer
    created_at TIMESTAMPTZ NOT NULL DEFAULT (now() at time zone 'utc'),
    deleted_at TIMESTAMPTZ
);
create UNIQUE INDEX UK_dealer_id_code ON VEHICLES (dealer_id, code);