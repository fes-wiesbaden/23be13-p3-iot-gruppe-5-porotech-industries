CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TYPE event_type AS ENUM (
  'CAR_INITIALIZED',
  'CONNECTION_ESTABLISHED',
  'CONNECTION_LOST',
  'CAMERA_STREAM_STARTED',
  'CAMERA_STREAM_STOPPED',
  'BATTERY_LOW',
  'ERROR_OCCURRED',
  'DRIVE_TO_POINT',
  'ARRIVAL_AT_DESTINATION'
);

CREATE TABLE vehicle (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  name VARCHAR(255) NOT NULL,
  model VARCHAR(255) NOT NULL,
  firmware_version VARCHAR(100),
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now()
);

CREATE TABLE event (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  vehicle_id UUID NOT NULL REFERENCES vehicle(id) ON DELETE CASCADE,
  event_type event_type NOT NULL,
  occurred_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  payload JSONB NOT NULL,
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now()
);

-- Indexes for performance
CREATE INDEX idx_event_vehicle_occurred_at ON event (vehicle_id, occurred_at DESC);
CREATE INDEX idx_event_event_type ON event (event_type);
CREATE INDEX idx_event_payload_gin ON event USING GIN (payload);