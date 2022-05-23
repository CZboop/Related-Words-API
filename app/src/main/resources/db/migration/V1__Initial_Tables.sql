CREATE TABLE words (id BIGSERIAL PRIMARY KEY, value TEXT NOT NULL, part_of_speech TEXT, length INT);

CREATE TABLE semantic_similar (id BIGSERIAL PRIMARY KEY, word1_id INT, word2_id INT);