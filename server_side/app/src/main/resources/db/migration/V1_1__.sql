CREATE TABLE IF NOT EXISTS words (id BIGSERIAL PRIMARY KEY, text_value TEXT NOT NULL, part_of_speech TEXT, length INT);

CREATE TABLE IF NOT EXISTS semantic_similar (id BIGSERIAL PRIMARY KEY, word1_id INT, word2_id INT);