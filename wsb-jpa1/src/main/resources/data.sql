INSERT INTO address (city, address_line1, address_line2, postal_code)
VALUES
    ('Warszawa', 'ul. Pięciomorgowa 3', 'Mieszkanie 5', '00-001'),
    ('Kraków', 'ul. Długa 15', NULL, '30-002'),
    ('Gdańsk', 'ul. Morska 22', 'Apartament 8', '80-003');

INSERT INTO doctor (firstname, lastname, telephone_number, email, doctor_number, specialization)
VALUES
    ('Jan', 'Kowalski', '123-456-789', 'jan.kowalski@example.com', 'L12345', 'KARDIOLOGIA'),
    ('Anna', 'Nowak', '987-654-321', 'anna.nowak@example.com', 'L67890', 'DERMATOLOGIA');

INSERT INTO medical_treatment (description, type)
VALUES
    ('Przeszczepienie serca', 'OPERACJA'),
    ('Biopsja skóry', 'PROCEDURA');

INSERT INTO patient (firstname, lastname, telephone_number, email, patient_number, date_of_birth, address_id)
VALUES
    ('Alicja', 'Jankowska', '555-1234', 'alicja.jankowska@example.com', 'P001', '1985-07-23', 1),
    ('Bartosz', 'Wójcik', '555-5678', 'bartosz.wojcik@example.com', 'P002', '1990-03-15', 2),
    ('Igor', 'Karlik', '555-8765', 'igor.karlik@example.com', 'P003', '1995-11-30', 3);

INSERT INTO visit (description, time, patient_id, doctor_id)
VALUES
    ('Kontrola zdrowia', '2024-12-08T10:00:00', 1, 1),
    ('Konsultacja dermatologiczna', '2024-12-09T11:30:00', 2, 2),
    ('Wizyta kontrolna', '2024-12-10T09:00:00', 3, 1);