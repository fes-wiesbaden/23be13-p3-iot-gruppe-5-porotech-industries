CREATE TABLE IF NOT EXISTS sensor_data (
                                           id SERIAL PRIMARY KEY,
                                           topic TEXT NOT NULL,
                                           payload JSONB NOT NULL,
                                           received_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);