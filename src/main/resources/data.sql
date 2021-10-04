INSERT INTO users (username, password, enabled) VALUES ('user', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', TRUE);
INSERT INTO users (username, password, enabled) VALUES ('admin', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', TRUE);
INSERT INTO users (username, password, enabled) VALUES ('maria', '$2a$10$wPHxwfsfTnOJAdgYcerBt.utdAvC24B/DWfuXfzKBSDHO0etB1ica', TRUE);

INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority) VALUES ('maria', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('maria', 'ROLE_ADMIN');



INSERT INTO plants (id, description, difficulty, file_name, food, latin_name, light, location, name, uploaded_by_username, uploaded_date, watering) VALUES ('1', 'Qua verzorging kan het een beetje een diva zijn, maar met de juiste aanpak kom je een heel eind. Dit alles lees je hier. Ook vertellen we graag meer over de afkomst, groeiwijze, luchtzuiverende eigenschappen en de verschillende soorten', 'MODERATE', 'Calathea.jpeg', 'MONTH', 'Calathea', 'HALFSUNNY', 'uploads/Calathea.jpeg', 'Pauwenplant', 'maria', '2021-10-01 11:21:44.084', 'WEEK');
INSERT INTO Plants (id, description, difficulty, file_name, food, latin_name, light, location, name, uploaded_by_username, uploaded_date, watering) VALUES ('2', 'De Dracaena is een makkelijke kamerplant om te verzorgen. De Dracaena kan goed op een plek staan in de (half)schaduw. Ook verbruikt de Drakenbloedboom weinig water.', 'EASY', 'DrakenBloedboom.jpeg', 'MONTH', 'Dracacena', 'SHADOW', 'uploads/DrakenBloedboom.jpeg', 'Drakenbloedboom', 'maria', '2021-10-01 11:30:44.084', 'WEEK');
INSERT INTO Plants (id, description, difficulty, file_name, food, latin_name, light, location, name, uploaded_by_username, uploaded_date, watering) VALUES ('3', 'De grond mag net niet uitdrogen, maar zeker ook niet te nat zijn, want daar kan hij slecht tegen. Vanaf het najaar minderen met water geven, tot zo’n 1 keer per week. In de lente en zomer heeft de plant voeding nodig.', 'EASY', 'hangficus.jpeg', 'MONTH', 'Tradescentia', 'HALFSUNNY', 'uploads/hangficus.jpeg', 'Vaderplant', 'user', '2021-10-01 11:31:44.084', 'WEEK');
INSERT INTO Plants (id, description, difficulty, file_name, food, latin_name, light, location, name, uploaded_by_username, uploaded_date, watering) VALUES ('4', 'De grond mag net niet uitdrogen, maar zeker ook niet te nat zijn, want daar kan hij slecht tegen. Vanaf het najaar minderen met water geven, tot zo’n 1 keer per week. In de lente en zomer heeft de plant voeding nodig.', 'EASY', 'Pannenkoekplant.jpeg', 'MONTH', 'Pilea Peperomioides', 'HALFSUNNY', 'uploads/Pannenkoekplant.jpeg', 'Pannenkoekplant', 'user', '2021-10-01 11:31:44.084', 'WEEK');